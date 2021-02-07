package logins.distanciel.ClientControlleur;

import project.insa.idchatsystem.Conversations.FacadeConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.Observers.FacadeConversationHandlerObserver;
import project.insa.idchatsystem.Observers.logins.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerEncapsulation implements FacadeConversationHandlerObserver,UsersStatusObserver {
    private final FacadeConversationHandler conversationHandler;
    private UserModel userModel;
    //private DistantUserModel centralizedUserModel;

    public ControllerEncapsulation(String id,
                                   int usermodel_receiver_port, int usermodel_emitter_port, ArrayList<Integer> others) throws NoPortAvailable {
        this.userModel = new UserModel(id,usermodel_receiver_port,usermodel_emitter_port,others);
        //TODO : Remplacer par la facade
        HashMap<String,User> knownUsers = new HashMap<>();
        HashMap<String,User> allKnownUsersthis = this.userModel.getOnlineUsers();
        allKnownUsersthis.forEach(
                (k,v) -> {
                    if (!v.isLocal_user()) {
                        knownUsers.put(k, v);
                    }
                }
        );
        this.conversationHandler = FacadeConversationHandler.getInstance(true,this,knownUsers);
        this.conversationHandler.addObserver(this);
        this.conversationHandler.addObserver(this);
        this.userModel.addUserModelObserver(this);
    }
    public void startConversation(int id){
        User user = this.userModel.getOnlineUsers().get(id);
        System.out.println(user);
        this.conversationHandler.open(user);
        this.conversationHandler.getCurrentConversation().send(new Message("raclette et tartiflette seront au menu"),this.conversationHandler.getCurrentConversation().getCorrespondent());
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

    @Override
    public void listenerPortChosen(int port) {
        User.setCurrentConversationHandlerListenerPort(port);

    }

    @Override
    public void messagesRetrieved(ArrayList<Message> retrievedMessages) {
        System.out.printf("RETRIEVED STORED MESSAGES\n");
    }
}
