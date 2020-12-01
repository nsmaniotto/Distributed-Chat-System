
package project.insa.idchatsystem;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.gui.ChatWindow;
import project.insa.idchatsystem.logins.server_mode.DistantUserModel;
import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;

public class ClientController implements ConversationHandlerObserver, UsersStatusObserver {
    private ConversationHandler conversationHandler;
    private LocalUserModel localUserModel;
    private ClientView clientView;
    private DistantUserModel centralizedUserModel;
    
    public ClientController(int id) {
        this.localUserModel = new LocalUserModel();
        this.view = new ChatWindow();
        this.conversationHandler = new ConversationHandler();
        this.conversationHandler.addObserver(this);
    }

    @Override
    public void newMessageRcv(Message message) {
        this.view. //....Récupération et on fait les modifs ici
    }

    @Override
    public void newMessageSent(Message message) {

    }
    @Override
    public void offlineUser(User user) {

    }

    @Override
    public void onlineUser(User user) {

    }
}
