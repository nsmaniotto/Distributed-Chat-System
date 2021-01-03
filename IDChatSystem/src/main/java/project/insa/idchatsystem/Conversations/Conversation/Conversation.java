package project.insa.idchatsystem.Conversations.Conversation;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.ConversationObservable;
import project.insa.idchatsystem.Observers.Conversations.ConversationObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;

public abstract class Conversation implements ConversationObservable {
    protected final User correspondent;
    protected final ArrayList<Message> history;
    protected boolean isOpen;
    protected ConversationObserver conversationHandlerObserver;
    public Conversation(User correspondent) {
        this.isOpen = false;
        this.correspondent = correspondent;
        // Empty for now, will be loaded later
        this.history = new ArrayList<>();
    }
    /**
     * Method making the conversation to open itself
     *
     */
    public void open() {
        this.isOpen = true;
        // Load pasts messages
        this.loadConversation();
    }
    /**
     * Building and treating a new message according to given input
     *
     * @param input : String - received stream
     */
    public void onReceive(String input) {
        System.out.printf("RECEIVED : %s\n",input);
        // Generate a Message instance from the given input
        Message newMessage = new Message(input);

        // Setting message source and destination
        try {
            newMessage.setSource(this.correspondent);
            newMessage.setDestination(User.getCurrentUser());
        } catch (Uninitialized e) {
            // Current user (thereforce message source) is not initialized
            System.out.println("Conversation: EXCEPTION WHILE SETTING MESSAGE DESTINATION " + e);
        }

        // Store the new message
        this.storeMessage(newMessage);

        // Notify the handler that a message has been received and must be treated
        this.notifyObserversReceivedMessage(newMessage);
    }
    public abstract void loadConversation();
    /**
     * Method making the conversation to close itself
     *
     */
    public void close() {
        this.isOpen = false;
    }
    protected void storeMessage(Message message) {this.history.add(message);}
    public abstract void send(Message message);

    /* CONVERSATION OBSERVER METHODS */
    @Override
    public void addConversationObserver(ConversationObserver observer) {
        this.conversationHandlerObserver = observer;
    }

    @Override
    public void deleteConversationObserver(ConversationObserver observer) {
        this.conversationHandlerObserver = null;
    }

    @Override
    public void notifyObserversSentMessage(Message sentMessage) {
        if(this.conversationHandlerObserver != null) {
            this.conversationHandlerObserver.newMessageSent(sentMessage);
        }
    }
    @Override
    public void notifyObserversReceivedMessage(Message receivedMessage) {
        if(this.conversationHandlerObserver != null) {
            this.conversationHandlerObserver.newMessageReceived(receivedMessage, this.isOpen);
        }
    }
    //GETTERS
    public ArrayList<Message> getHistory() {
        return history;
    }
    public User getCorrespondent(){return this.correspondent;};
}
