package project.insa.idchatsystem.Conversations;

import project.insa.idchatsystem.Conversations.Conversation.Conversation;
import project.insa.idchatsystem.Conversations.ConversationHandler.DistantConversationHandler;
import project.insa.idchatsystem.Conversations.ConversationHandler.LocalConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.*;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;

public class FacadeConversationHandler implements LocalConversationHandlerObserver, ConversationHandlerObserver, FacadeConversationHandlerObservable {
    private static FacadeConversationHandler INSTANCE;
    private final LocalConversationHandler localHandler;
    private final DistantConversationHandler distantHandler;
    private int portHandlerLocal;
    private ArrayList<FacadeConversationHandlerObserver> observers;
    private FacadeConversationHandler() throws NoPortAvailable {
        this.localHandler = LocalConversationHandler.getInstance();
        this.distantHandler = DistantConversationHandler.getInstance();
        new Thread(this.localHandler).start();
    }
    /**
     * Access point of the (unique) instance
     *
     * @return INSTANCE : FacadeConversationHandler - single instance of this class
     */
    public static FacadeConversationHandler getInstance() throws NoPortAvailable {
        if(FacadeConversationHandler.INSTANCE == null){
            FacadeConversationHandler.INSTANCE = new FacadeConversationHandler();
        }
        return FacadeConversationHandler.INSTANCE;
    }
    /* GETTERS/SETTERS */
    public Conversation getCurrentConversation() {
        if(this.localHandler.getCurrentConversation() != null)
            return this.localHandler.getCurrentConversation();
        else
            return this.distantHandler.getCurrentConversation();
    }
    public void open(User correspondent) {
        if(correspondent.isLocal_user())
            this.localHandler.open(correspondent);
        else
            this.distantHandler.open(correspondent);
    }
    public ArrayList<Message> setCurrentConversation(User user) {
        if(user.isLocal_user()){
            this.distantHandler.noCurrentConversation();
            return this.localHandler.setCurrentConversation(user);
        }
        else {
            this.localHandler.noCurrentConversation();
            return this.distantHandler.setCurrentConversation(user);
        }
    }
    public void addKnownUser(User newUser) {
        if(newUser.isLocal_user())
            this.localHandler.addKnownUser(newUser);
        else
            this.distantHandler.addKnownUser(newUser);
    }
    public void removeKnownUser(User user) {
        if(user.isLocal_user())
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
    public void notifyListenerPortNegociated() {
        this.observers.forEach(obs -> obs.listenerPortChosen(this.portHandlerLocal));
    }

    @Override
    public void addObserver(FacadeConversationHandlerObserver observer) {
        this.observers.add(observer);
        this.notifyListenerPortNegociated();
    }

    @Override
    public void deleteObserver(FacadeConversationHandlerObserver observer) {
        this.observers.remove(observer);
    }
    public HashMap<Integer,User> getUsers() {
        HashMap<Integer,User> merged_hashmap_users = new HashMap<>();
        merged_hashmap_users.putAll(this.localHandler.getUsers());
        merged_hashmap_users.putAll(this.distantHandler.getUsers());// Not useful as all users are stored in both of the handlers
        return merged_hashmap_users;
    }
}
