package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.Server.ServerControllerObservable;
import project.insa.idchatsystem.Observers.Server.ServerConvControllerObserver;
import project.insa.idchatsystem.Observers.Server.ServerLoginControllerObserver;
import project.insa.idchatsystem.Observers.Server.ServerIncomingMessagesObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerController implements ServerIncomingMessagesObserver, ServerControllerObservable {
    private ServerIncomingMessages incomingMessages;
    private ServerSendMessage sendMessages;
    private ServerLoginControllerObserver loginObs;
    private ServerConvControllerObserver convObs;
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
            this.loginObs.notifyNewMessage(message);
        }
        //check if coversation message
        pattern = Pattern.compile("conversation.*");
        m = pattern.matcher(message);
        while (m.find()){
            this.convObs.notifyNewMessage(message);
        }
    }

    @Override
    public void addLoginListener(ServerLoginControllerObserver obs) {
        this.loginObs = obs;
    }

    @Override
    public void addConvListener(ServerConvControllerObserver obs) {
        this.convObs = obs;
    }
}
