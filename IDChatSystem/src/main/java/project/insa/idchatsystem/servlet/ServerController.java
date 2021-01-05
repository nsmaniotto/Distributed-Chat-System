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
    private static ServerController INSTANCE;
    private ServerIncomingMessages incomingMessages;
    private ServerSendMessage sendMessages;
    private ServerLoginControllerObserver loginObs;
    private ServerConvControllerObserver convObs;
    private ServerController() {
        incomingMessages = new ServerIncomingMessages();
        incomingMessages.addOserver(this);
        sendMessages = new ServerSendMessage();
        this.subscribe();
        this.publish("ready");
    }
    public static ServerController getInstance() {
        if(INSTANCE == null) {
            ServerController.INSTANCE = new ServerController();
        }
        return ServerController.INSTANCE;
    }
    public void subscribe() {
        sendMessages.sendGet(String.format("subscribe"),null);
    }
    public void publish(String state) {
        sendMessages.sendGet(String.format("state,%s",state),null);
    }
    public void sendMessage(String message,User corresp) {
        this.sendMessages.sendGet(message,corresp);
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
