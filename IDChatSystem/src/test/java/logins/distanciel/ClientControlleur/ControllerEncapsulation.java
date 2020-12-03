package logins.distanciel.ClientControlleur;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;

import java.util.ArrayList;

public class ControllerEncapsulation implements ConversationHandlerObserver, UsersStatusObserver {
    private final ConversationHandler conversationHandler;
    private LocalUserModel localUserModel;
    //private DistantUserModel centralizedUserModel;

    public ControllerEncapsulation(int id, int usermodel_receiver_port, int usermodel_emitter_port, ArrayList<Integer> others,int handler_port) {
        this.localUserModel = new LocalUserModel(id,usermodel_receiver_port,usermodel_emitter_port,others);
        this.conversationHandler = new ConversationHandler(handler_port);
        this.conversationHandler.addObserver(this);
        this.localUserModel.addUserModelObserver(this);
    }

    @Override
    public void offlineUser(User user) {
        System.out.printf("OFFLINE USER : %s\n",user.toString());
    }

    @Override
    public void onlineUser(User user) {
        System.out.printf("ONLINE USER : %s\n",user.toString());
    }

    @Override
    public void newMessageReceived(Message receivedMessage) {
        System.out.printf("RECEIVED MESSAGE : %s\n",receivedMessage.toString());
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        System.out.printf("SENT MESSAGE : %s\n",sentMessage.toString());
    }
}
