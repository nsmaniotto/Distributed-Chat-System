
package project.insa.idchatsystem;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.gui.ChatWindow;
import project.insa.idchatsystem.gui.View;
import project.insa.idchatsystem.logins.server_mode.DistantUserModel;
import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;

public class ClientController implements ConversationHandlerObserver, UsersStatusObserver {
    /*private final ConversationHandler conversationHandler;
    private LocalUserModel localUserModel;
    private View view;*/
    //private DistantUserModel centralizedUserModel;
    
    public ClientController(int id) {
        /*this.localUserModel = new LocalUserModel();
        this.view = new View();
        this.conversationHandler = new ConversationHandler();
        this.conversationHandler.addObserver(this);*/
    }

    @Override
    public void offlineUser(User user) {

    }

    @Override
    public void onlineUser(User user) {

    }

    @Override
    public void newMessageReceived(Message receivedMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
