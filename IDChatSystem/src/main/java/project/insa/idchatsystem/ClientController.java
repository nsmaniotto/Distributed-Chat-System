
package project.insa.idchatsystem;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Observers.ChatWindowObserver;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.Observers.ViewObserver;
import project.insa.idchatsystem.User.distanciel.User;
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
                            int loginReceiverPort, int loginEmiterPort, ArrayList<Integer> loginBroadcast,
                            int conversationSocketPortEcoute, int conversationSocketPortDest)
    {
        // View init
        this.view = new View();
        this.view.addObserver(this);
        new Thread(view).start();
        
        // Conversation handler init
        this.conversationHandler = ConversationHandler.getInstance(conversationSocketPortEcoute,conversationSocketPortDest);
        this.conversationHandler.addObserver(this);
        new Thread(conversationHandler).start();

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
        this.localUserModel.removeOnlineUser(user.get_id());
    }

    @Override
    public void onlineUser(User user) {
        this.conversationHandler.addKnownUser(user);
    }
    
    /* CONVERSATION HANDLER OBSERVER METHODS */

    @Override
    public void newMessageReceived(Message receivedMessage) {        
        this.view.displayMessage(receivedMessage);
        //TODO manage notifications when current conversation is not the same conversation as the message
        //this.view.displayNotification(receivedMessage);
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        /* No need to check if this message is associated with the current conversation.
            Because the message has just been sent, it means the opened conversation's 
            correspondent is the same as the message's destination
        */
        this.view.displayMessage(sentMessage);
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
        if(this.conversationHandler.getCurrentConversation() != null) {
            this.conversationHandler.getCurrentConversation().send(sendingMessage);
        }
    }
}
