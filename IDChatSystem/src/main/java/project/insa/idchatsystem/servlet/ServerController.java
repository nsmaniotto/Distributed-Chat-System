package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Observers.Server.Observables.ServerControllerObservable;
import project.insa.idchatsystem.Observers.Server.Observers.ServerConvControllerObserver;
import project.insa.idchatsystem.Observers.Server.Observers.ServerLoginControllerObserver;
import project.insa.idchatsystem.Observers.Server.Observers.ServerIncomingMessagesObserver;
import project.insa.idchatsystem.Observers.Server.Observers.ServerSendMessageObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerController implements ServerIncomingMessagesObserver, ServerControllerObservable, ServerSendMessageObserver {
    private ServerIncomingMessages incomingMessages;
    private ServerSendMessage sendMessages;
    private ServerLoginControllerObserver loginObs;
    private ServerConvControllerObserver convObs;
    public ServerController(String protocole) {
        System.out.printf(".(ServerController.java:19) - ServerController : \n");
        incomingMessages = new ServerIncomingMessages();
        incomingMessages.addOserver(this);
        sendMessages = new ServerSendMessage(protocole);
        sendMessages.addObserver(this);
        this.subscribe();
        this.publish("ready");
        new Thread(incomingMessages).start();
    }
    public void subscribe() {
        System.out.printf(".(ServerController.java:36) - subscribe : \n");
        sendMessages.sendPost(String.format("subscribe"),null);
    }
    public void publish(String state) {
        sendMessages.sendPost(String.format("state,%s",state),null);
    }
    public void sendMessage(String message,User corresp) {
        this.sendMessages.sendPost(message,corresp);
    }
    @Override
    public void notifyNewMessage(String message) {
        //Analyse message
        //check if login message
        System.out.printf(".(ServerController.java:42) - notifyNewMessage : %s\n",message);
        Pattern pattern = Pattern.compile("(?<login>login),(?<msgRcvd>.*)");
        Matcher m = pattern.matcher(message);
        while (m.find()){
            this.loginObs.notifyNewMessage(m.group("msgRcvd"));
        }
        //check if coversation message
        pattern = Pattern.compile("(?<conv>conv),(?<msgRcvd>.*)");
        m = pattern.matcher(message);
        while (m.find()){
            this.convObs.notifyNewMessage(m.group("msgRcvd"));
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

    @Override
    public void askForUpdate() {
        this.sendMessage("getMessages",null);
    }
}
