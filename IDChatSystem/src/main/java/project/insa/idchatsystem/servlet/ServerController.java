package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.ServerControllerObservable;
import project.insa.idchatsystem.Observers.ServerControllerObserver;
import project.insa.idchatsystem.Observers.ServerIncomingMessagesObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerController implements ServerIncomingMessagesObserver, ServerControllerObservable {
    private ServerIncomingMessages incomingMessages;
    private ServerSendMessage sendMessages;
    private ServerControllerObserver obs;
    public ServerController() {
        incomingMessages = new ServerIncomingMessages();
        incomingMessages.addOserver(this);
        sendMessages = new ServerSendMessage();
    }
    public void subscribe() {
        sendMessages.sendGet(String.format("subscribeLocal,%s", User.current_user_transfer_string()));
    }
    public void publish(String state) {
        try {
            sendMessages.sendGet(String.format("state,%d,%s",User.get_current_id(),state));
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }
    public void sendMessage(String message) {
        this.sendMessages.sendGet(message);
    }
    @Override
    public void notifyNewMessage(String message) {
        //Analyse message
        //check if login message
        Pattern pattern = Pattern.compile("login.*");
        Matcher m = pattern.matcher(message);
        while (m.find()){
            this.obs.notifyNewLoginMessage(message);
        }
        //check if cnversation message
        pattern = Pattern.compile("conversation.*");
        m = pattern.matcher(message);
        while (m.find()){
            this.obs.notifyNewConversationMessage(message);
        }
    }

    @Override
    public void addListener(ServerControllerObserver obs) {
        this.obs = obs;
    }
}
