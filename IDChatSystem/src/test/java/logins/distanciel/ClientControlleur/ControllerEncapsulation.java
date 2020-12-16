package logins.distanciel.ClientControlleur;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerEncapsulation implements ConversationHandlerObserver, UsersStatusObserver {
    private final ConversationHandler conversationHandler;
    private LocalUserModel localUserModel;
    //private DistantUserModel centralizedUserModel;

    public ControllerEncapsulation(int id,
                                   int usermodel_receiver_port, int usermodel_emitter_port, ArrayList<Integer> others,
                                   int conversationSocketPortDest) throws NoPortAvailable {
        this.localUserModel = new LocalUserModel(id,usermodel_receiver_port,usermodel_emitter_port,others);
        this.conversationHandler = ConversationHandler.getInstance(conversationSocketPortDest);
        this.conversationHandler.addObserver(this);
        this.localUserModel.addUserModelObserver(this);
    }
    public void startConversation(int id){
        User user = this.localUserModel.getOnlineUsers().get(id);
        System.out.println(user);
        this.conversationHandler.open(user);
        this.conversationHandler.getCurrentConversation().send(new Message("raclette et tartiflette seront au menu"));
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
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation) {
        System.out.printf("RECEIVED MESSAGE : %s\n",receivedMessage.toString());
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        System.out.printf("SENT MESSAGE : %s\n",sentMessage.toString());
    }
}
