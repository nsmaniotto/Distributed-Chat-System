
package project.insa.idchatsystem;

import project.insa.idchatsystem.Conversations.FacadeConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Observers.Conversations.Observers.FacadeConversationHandlerObserver;
import project.insa.idchatsystem.Observers.logins.Observers.UsersStatusObserver;
import project.insa.idchatsystem.Observers.gui.Observers.ViewObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.UserView;
import project.insa.idchatsystem.gui.View;
import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.ArrayList;
import project.insa.idchatsystem.database.MessageDatabase;

public class ClientController implements FacadeConversationHandlerObserver, UsersStatusObserver, ViewObserver {
    private View view;
    private final MessageDatabase database;
    private final FacadeConversationHandler conversationHandler;
    private final UserModel userModel;
    /*
    private DistantUserModel centralizedUserModel;*/
    
    public ClientController(int id,
                            int loginReceiverPort, int loginEmiterPort, ArrayList<Integer> loginBroadcast) throws NoPortAvailable {
        System.out.printf(".(ClientController.java:27) - ClientController : id : %d\n",id);
        User.init_current_user(id,loginBroadcast != null);
        // Conversation handler init
        this.conversationHandler = FacadeConversationHandler.getInstance(loginBroadcast != null,this);
        this.conversationHandler.addObserver(this);

        // View init
        this.view = new View();
        this.view.addObserver(this);
        new Thread(view).start();

        // At this stage, the login controller is running in the same thread as the ClientController but the reception and emission operates in two others
        this.userModel = new UserModel(id,loginReceiverPort,loginEmiterPort,loginBroadcast);
        this.userModel.addUserModelObserver(this);

        // Initialize local database
        this.database = MessageDatabase.getInstance();
        this.database.init();
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

    @Override
    public void messagesRetrieved(ArrayList<Message> retrievedMessages) {
        retrievedMessages.forEach( message -> this.view.displayMessage(message) );
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
        System.out.printf(".(ClientController.java:106) - newMessageSending : currentConv : %s\n",this.conversationHandler.getCurrentConversation());
        if(this.conversationHandler.getCurrentConversation() != null) {
            this.conversationHandler.getCurrentConversation().send(sendingMessage,this.conversationHandler.getCurrentConversation().getCorrespondent());
        }
    }

    @Override
    public void userSelected(UserView userview) {
        System.out.printf(".(ClientController.java:114) - userSelected\n");
        this.conversationHandler.open(userview.getUser());
    }
}
