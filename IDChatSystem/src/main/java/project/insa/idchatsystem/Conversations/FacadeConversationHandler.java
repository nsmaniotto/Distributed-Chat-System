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
    private FacadeConversationHandler(boolean local,FacadeConversationHandlerObserver obs,HashMap<String, User> knowUsers) throws NoPortAvailable {
        this.local = local;
        this.observers = new ArrayList<>();
        this.addObserver(obs);
        this.distantHandler = DistantConversationHandler.getInstance(knowUsers);
        this.distantHandler.addObserver(this);
        if(local) {
            System.out.printf(".(FacadeConversationHandler.java:31) - FacadeConversationHandler : Adding local conversation support %b\n",local);
            this.localHandler = LocalConversationHandler.getInstance();
            this.localHandler.addObserver(this);
            new Thread(this.localHandler).start();
        }
    }
    /**
     * Access point of the (unique) instance
     *
     * @return INSTANCE : FacadeConversationHandler - single instance of this class
     */
    public static FacadeConversationHandler getInstance(boolean local,FacadeConversationHandlerObserver obs,HashMap<String, User> knownUsers) throws NoPortAvailable {
        if(FacadeConversationHandler.INSTANCE == null){
            FacadeConversationHandler.INSTANCE = new FacadeConversationHandler(local,obs,knownUsers);
        }
        return FacadeConversationHandler.INSTANCE;
    }
    /* GETTERS/SETTERS */
    public Conversation getCurrentConversation() {
        if(this.localHandler != null && this.localHandler.getCurrentConversation() != null) {
            return this.localHandler.getCurrentConversation();
        }
        else{
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
        if(this.local && (this.local == newUser.isLocal_user())) {
//            System.out.printf(".(FacadeConversationHandler.java:65) - addKnownUser : local %s\n",newUser);
            this.localHandler.addKnownUser(newUser);
        }
        else {
            this.distantHandler.addKnownUser(newUser);
        }
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
        if(this.observers.contains(observer))
            System.out.printf(".(FacadeConversationHandler.java:110) - addObserver : Duplicate observer\n");
        else {
            this.observers.add(observer);
            this.notifyListenerPortNegociated();
        }
    }

    @Override
    public void deleteObserver(FacadeConversationHandlerObserver observer) {
        this.observers.remove(observer);
    }
    public HashMap<String,User> getUsers() {
        HashMap<String,User> merged_hashmap_users = new HashMap<>();
        if(this.local)
            merged_hashmap_users.putAll(this.localHandler.getUsers());
        merged_hashmap_users.putAll(this.distantHandler.getUsers());// Not useful as all users are stored in both of the handlers
        return merged_hashmap_users;
    }
}
