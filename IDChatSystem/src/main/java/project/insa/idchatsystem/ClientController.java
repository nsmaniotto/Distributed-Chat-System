
package project.insa.idchatsystem;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.Observers.ViewObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.UserView;
import project.insa.idchatsystem.gui.View;
import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;

import java.util.ArrayList;

public class ClientController implements ConversationHandlerObserver, UsersStatusObserver, ViewObserver {
    private View view;
    private final ConversationHandler conversationHandler;
    private final LocalUserModel localUserModel;
    /*
    private DistantUserModel centralizedUserModel;*/
    
    public ClientController(int id,
                            int loginReceiverPort, int loginEmiterPort, ArrayList<Integer> loginBroadcast) throws NoPortAvailable {
        // Conversation handler init
        this.conversationHandler = ConversationHandler.getInstance();
        this.conversationHandler.addObserver(this);
        new Thread(conversationHandler).start();

        // View init
        this.view = new View();
        this.view.addObserver(this);
        new Thread(view).start();

        // At this stage, the login controller is running in the same thread as the ClientController but the reception and emission operates in two others
        this.localUserModel = new LocalUserModel(id,loginReceiverPort,loginEmiterPort,loginBroadcast);
        this.localUserModel.addUserModelObserver(this);
    }

    public LocalUserModel getLocalUserModel() {
        return localUserModel;
    }
    /* USERS STATUS OBSERVER METHODS */
    
    @Override
    public void offlineUser(User user) {
        this.view.offlineUser(user);
        this.conversationHandler.removeKnownUser(user);
    }

    @Override
    public void onlineUser(User user) {
        //System.out.printf("CONTROLLER : Online user : %s\n",user);
        this.conversationHandler.addKnownUser(user);
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
        this.view.displayMessage(sentMessage);
    }

    @Override
    public void listenerPortChosen(int port) {
        User.setCurrentConversationHandlerListenerPort(port);
    }

    /* GETTERS/SETTERS */
    public ConversationHandler getConversationHandler() {
        return this.conversationHandler;
    }

    /* VIEW OBSERVER METHODS */
    @Override
    public boolean newLogin(String login) {
        return this.localUserModel.setUsername(login);
    }


    @Override
    public void newMessageSending(Message sendingMessage) {
        System.out.printf("CONTROLLEUR newMessageSending\n");
        if(this.conversationHandler.getCurrentConversation() != null) {
            this.conversationHandler.getCurrentConversation().send(sendingMessage);
        }
    }

    @Override
    public void userSelected(UserView userview) {
        System.out.printf("CONTROLLEUR : userSelected\n");
        this.conversationHandler.open(userview.getUser());
    }

    @Override
    public void askForMessages(User user) {
        ArrayList<Message> messages = this.conversationHandler.setCurrentConversation(user);
        if(messages != null) {
            this.view.messagesToShow(messages);
        }
        else {
            System.out.printf("Conversation not found\n");
        }
    }
}
