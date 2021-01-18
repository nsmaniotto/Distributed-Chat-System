package project.insa.idchatsystem.Conversations;

import project.insa.idchatsystem.Conversations.Conversation.Conversation;
import project.insa.idchatsystem.Conversations.ConversationHandler.DistantConversationHandler;
import project.insa.idchatsystem.Conversations.ConversationHandler.LocalConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.Observables.FacadeConversationHandlerObservable;
import project.insa.idchatsystem.Observers.Conversations.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.Conversations.Observers.FacadeConversationHandlerObserver;
import project.insa.idchatsystem.Observers.Conversations.Observers.LocalConversationHandlerObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;

public class FacadeConversationHandler implements LocalConversationHandlerObserver, ConversationHandlerObserver, FacadeConversationHandlerObservable {
    private static FacadeConversationHandler INSTANCE;
    private LocalConversationHandler localHandler = null;
    private final DistantConversationHandler distantHandler;
    private int portHandlerLocal;
    private ArrayList<FacadeConversationHandlerObserver> observers;
    private boolean local;
    private FacadeConversationHandler(boolean local,FacadeConversationHandlerObserver obs) throws NoPortAvailable {
        this.local = local;
        this.observers = new ArrayList<>();
        this.addObserver(obs);
        this.distantHandler = DistantConversationHandler.getInstance();
        this.distantHandler.addObserver(this);
        if(local) {
            this.localHandler = LocalConversationHandler.getInstance();
            System.out.printf(".(FacadeConversationHandler.java:32) - FacadeConversationHandler : construction LocalConversationHandler\n");
            this.localHandler.addObserver(this);
            new Thread(this.localHandler).start();
        }
    }
    /**
     * Access point of the (unique) instance
     *
     * @return INSTANCE : FacadeConversationHandler - single instance of this class
     */
    public static FacadeConversationHandler getInstance(boolean local,FacadeConversationHandlerObserver obs) throws NoPortAvailable {
        if(FacadeConversationHandler.INSTANCE == null){
            FacadeConversationHandler.INSTANCE = new FacadeConversationHandler(local,obs);
        }
        return FacadeConversationHandler.INSTANCE;
    }
    /* GETTERS/SETTERS */
    public Conversation getCurrentConversation() {
        if(this.localHandler != null && this.localHandler.getCurrentConversation() != null) {
            System.out.printf(".(FacadeConversationHandler.java:51) - getCurrentConversation : local\n");
            return this.localHandler.getCurrentConversation();
        }
        else{
            System.out.printf(".(FacadeConversationHandler.java:51) - getCurrentConversation : distant\n");
            return this.distantHandler.getCurrentConversation();
        }
    }
    public void open(User correspondent) {
        if(this.local && correspondent.isLocal_user())
            this.localHandler.open(correspondent);
        else
            this.distantHandler.open(correspondent);
    }
    public void addKnownUser(User newUser) {
        if(this.local && newUser.isLocal_user())
            this.localHandler.addKnownUser(newUser);
        else
            this.distantHandler.addKnownUser(newUser);
    }
    public void removeKnownUser(User user) {
        if(this.local && user.isLocal_user())
            this.localHandler.removeKnownUser(user);
        else
            this.distantHandler.removeKnownUser(user);
    }

    @Override
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation) {
        for (FacadeConversationHandlerObserver obs : this.observers) {
            obs.newMessageReceived(receivedMessage,isCurrentConversation);
        }
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        System.out.printf(".(FacadeConversationHandler.java:81) - newMessageSent\n");
        this.observers.forEach( observer -> observer.newMessageSent(sentMessage) );
    }
    @Override
    public void listenerPortChosen(int port) {
        this.portHandlerLocal = port;
        this.notifyListenerPortNegociated();
    }

    @Override
    public void messagesRetrieved(ArrayList<Message> retrievedMessages) {
        this.observers.forEach(obs -> obs.messagesRetrieved(retrievedMessages));
    }

    @Override
    public void notifyListenerPortNegociated() {
        this.observers.forEach(obs -> obs.listenerPortChosen(this.portHandlerLocal));
    }

    @Override
    public void addObserver(FacadeConversationHandlerObserver observer) {
        System.out.printf("OBSERVER CALLED\n");
        this.observers.add(observer);
        this.notifyListenerPortNegociated();
    }

    @Override
    public void deleteObserver(FacadeConversationHandlerObserver observer) {
        this.observers.remove(observer);
    }
    public HashMap<Integer,User> getUsers() {
        HashMap<Integer,User> merged_hashmap_users = new HashMap<>();
        if(this.local)
            merged_hashmap_users.putAll(this.localHandler.getUsers());
        merged_hashmap_users.putAll(this.distantHandler.getUsers());// Not useful as all users are stored in both of the handlers
        return merged_hashmap_users;
    }
}
