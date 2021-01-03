package project.insa.idchatsystem.Conversations;

import project.insa.idchatsystem.Conversations.Conversation.Conversation;
import project.insa.idchatsystem.Conversations.ConversationHandler.DistantConversationHandler;
import project.insa.idchatsystem.Conversations.ConversationHandler.LocalConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.Conversations.LocalConversationHandlerObservable;
import project.insa.idchatsystem.Observers.Conversations.LocalConversationHandlerObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;

public class FacadeConversationHandler implements LocalConversationHandlerObserver, ConversationHandlerObserver {
    private static FacadeConversationHandler INSTANCE;
    private final LocalConversationHandler localHandler;
    private final DistantConversationHandler distantHandler;
    private FacadeConversationHandler() throws NoPortAvailable {
        this.localHandler = LocalConversationHandler.getInstance();
        this.distantHandler = DistantConversationHandler.getInstance();
        new Thread(this.localHandler).start();
        new Thread(this.distantHandler).start();
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

    }

    @Override
    public void newMessageSent(Message sentMessage) {

    }

    @Override
    public void listenerPortChosen(int port) {

    }
}
