
package project.insa.idchatsystem;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.View;

public class ClientController implements ConversationHandlerObserver, UsersStatusObserver {
    private View view;
    private final ConversationHandler conversationHandler;
    /*private LocalUserModel localUserModel;
    private DistantUserModel centralizedUserModel;*/
    
    public ClientController(int id) {
        // View init
        this.view = new View();
        new Thread(view).start();
        
        // Conversation handler init
        this.conversationHandler = ConversationHandler.getInstance();
        this.conversationHandler.addObserver(this);
        //new Thread(conversationHandler).start();
        //this.localUserModel = new LocalUserModel();
    }

    /* USERS STATUS OBSERVER METHODS */
    
    @Override
    public void offlineUser(User user) {

    }

    @Override
    public void onlineUser(User user) {
        this.conversationHandler.addKnownUser(user);
    }
    
    /* CONVERSATION HANDLER OBSERVER METHODS */

    @Override
    public void newMessageReceived(Message receivedMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
