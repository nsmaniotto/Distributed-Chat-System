
package project.insa.idchatsystem;

import project.insa.idchatsystem.Conversations.FacadeConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.Conversations.Observers.FacadeConversationHandlerObserver;
import project.insa.idchatsystem.Observers.logins.Observers.UsersStatusObserver;
import project.insa.idchatsystem.Observers.gui.Observers.ViewObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.database.LoginsBroadcastDatabase;
import project.insa.idchatsystem.gui.UserView;
import project.insa.idchatsystem.gui.View;
import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import project.insa.idchatsystem.database.MessageDatabase;
import project.insa.idchatsystem.tools.TestPort;

public class ClientController implements FacadeConversationHandlerObserver, UsersStatusObserver, ViewObserver {
    private final View view;
    private final MessageDatabase database;
    private final FacadeConversationHandler conversationHandler;
    private final UserModel userModel;
    /*
    private DistantUserModel centralizedUserModel;*/
    
    public ClientController(String id, boolean local, boolean cleanReceiversPorts) throws NoPortAvailable {
//        System.out.printf(".(ClientController.java:27) - ClientController : id : %s\n",id);
        User.init_current_user(id,local);

        // Initialize local database
        this.database = MessageDatabase.getInstance();
        this.database.init();

        // View init
        this.view = new View();
        this.view.addObserver(this);
        new Thread(view).start();

        // At this stage, the login controller is running in the same thread as the ClientController but the reception and emission operates in two others
        boolean twoPortsFound = false;
        int port = 1025;
        int portEmission = -1;
        int portReception = -1;
        while (!twoPortsFound && port < 65535) {
            boolean portAvailable= TestPort.portIsavailable(port);
            if(portAvailable) {
                if(portEmission == -1)
                    portEmission = port;
                else {
                    portReception = port;
                    twoPortsFound = true;
                }
            }
            port++;
        }
        ArrayList<Integer> portsBroadcast = null;
        if (local) {
            LoginsBroadcastDatabase logins = new LoginsBroadcastDatabase(cleanReceiversPorts);
            portsBroadcast = logins.getPortReceivers();
            logins.writePortReceiver(portReception);
        }
        this.userModel = new UserModel(id,portReception,portEmission,portsBroadcast);
        this.userModel.addUserModelObserver(this);
        HashMap<String,User> knownUsers = new HashMap<>();
        HashMap<String,User> allKnownUsersthis = this.userModel.getOnlineUsers();
        allKnownUsersthis.forEach(
                (k,v) -> {
                    try {
                        if ((User.getCurrentUser().isLocal_user() ^ v.isLocal_user()))
                            knownUsers.put(k, v);

                    } catch (Uninitialized uninitialized) {
                        uninitialized.printStackTrace();
                    }
                }
        );
        // Conversation handler init
        this.conversationHandler = FacadeConversationHandler.getInstance(local,this,knownUsers);



        this.view.enableLoginTextField();
    }

    public UserModel getUserModel() {
        return userModel;
    }
    /* USERS STATUS OBSERVER METHODS */
    
    @Override
    public void offlineUser(User user) {
        this.view.offlineUser(user);
        this.conversationHandler.removeKnownUser(user);
    }

    @Override
    public void onlineUser(User user) {
        if(this.conversationHandler != null)
            this.conversationHandler.addKnownUser(user);
        if(this.view != null)
            this.view.onlineUser(user);
    }
    
    /* CONVERSATION HANDLER OBSERVER METHODS */

    @Override
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation) {
        if(isCurrentConversation) {
            this.view.displayMessage(receivedMessage);
        } else {
            this.view.displayNotification(receivedMessage);
        }
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        /* No need to check if this message is associated with the current conversation.
            Because the message has just been sent, it means the opened conversation's 
            correspondent is the same as the message's destination
        */
//        System.out.printf(".(ClientController.java:82) - newMessageSent : \n");
        this.view.displayMessage(sentMessage);
    }

    @Override
    public void listenerPortChosen(int port) {
        User.setCurrentConversationHandlerListenerPort(port);
    }

    @Override
    public void messagesRetrieved(ArrayList<Message> retrievedMessages) {
        this.view.clearMessages();
        retrievedMessages.forEach(this.view::displayMessage);
    }
    /* GETTERS/SETTERS */
    public FacadeConversationHandler getConversationHandler() {
        return this.conversationHandler;
    }

    /* VIEW OBSERVER METHODS */
    @Override
    public boolean newLogin(String login) {
        return this.userModel.setUsername(login);
    }


    @Override
    public void newMessageSending(Message sendingMessage) {
        sendingMessage.setDestination(this.conversationHandler.getCurrentConversation().getCorrespondent());
        try {
            sendingMessage.setSource(User.getCurrentUser());
            System.out.printf(".(ClientController.java:138) - newMessageSending : currentConv : %s\n",this.conversationHandler.getCurrentConversation().getCorrespondent().get_id());
            if(this.conversationHandler.getCurrentConversation() != null) {
                this.conversationHandler.getCurrentConversation().send(sendingMessage,this.conversationHandler.getCurrentConversation().getCorrespondent());
            }
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }

    @Override
    public void userSelected(UserView userview) {
        this.conversationHandler.open(userview.getUser());
    }
}
