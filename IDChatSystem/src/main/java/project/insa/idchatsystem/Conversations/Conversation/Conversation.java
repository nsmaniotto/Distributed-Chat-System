package project.insa.idchatsystem.Conversations.Conversation;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.Observables.ConversationObservable;
import project.insa.idchatsystem.Observers.Conversations.Observers.ConversationObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.database.MessageDatabase;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Conversation implements ConversationObservable, Runnable {
    protected final User correspondent;
    protected final ArrayList<Message> history;
    protected boolean isOpen;
    protected ConversationObserver conversationObserver;
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
        System.out.printf(".(Conversation.java:40) - onReceive : %s\n",input);
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
        
        // Wait before storing the message:
        // In local; give time to the sender to store the message
        // Because the receiver must check if the message is present on the local database
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Store the new message
        this.storeMessage(newMessage,"onReceive Conversation");

        // Notify the handler that a message has been received and must be treated
        this.notifyObserversReceivedMessage(newMessage);
    }
    protected void storeMessage(Message message,String indic) {
        MessageDatabase.getInstance().storeMessage(message,indic);
        System.out.printf(".(Conversation.java:70) - storeMessage : from %s\n",indic);
    }
    /**
     * Method making the conversation to close itself
     *
     */
    public void close() {
        this.isOpen = false;
    }
    public abstract void send(Message message,User corresp);

    public void loadConversation() {
        ArrayList<Message> history = new ArrayList<>();

        // Retrieve past messages
        try {
            history = MessageDatabase.getInstance().retrieveOrderedMessagesByConversationBetween(User.getCurrentUser(), this.correspondent);
        } catch (Uninitialized e) {
            // Current user (thereforce message source) is not initialized
            System.out.println("Conversation: EXCEPTION WHILE RETRIEVING PAST MESSAGES " + e);
        }

        // Notify ConversationHandler to display the previously retrieved messages
        if(!history.isEmpty()) {
            this.notifyObserversRetrievedMessages(history);
        }
    }
    /* CONVERSATION OBSERVER METHODS */
    @Override
    public void addConversationObserver(ConversationObserver observer) {
//        System.out.printf(".(Conversation.java:73) - addConversationObserver : %s\n",observer);
        this.conversationObserver = observer;
    }

    @Override
    public void deleteConversationObserver(ConversationObserver observer) {
        this.conversationObserver = null;
    }

    @Override
    public void notifyObserversSentMessage(Message sentMessage) {
//        System.out.printf(".(Conversation.java:84) - notifyObserversSentMessage : this.conversationHandlerObserver : %s\n",this.conversationHandlerObserver);
        if(this.conversationObserver != null) {
//            System.out.printf(".(Conversation.java:84) - notifyObserversSentMessage\n");
            this.conversationObserver.newMessageSent(sentMessage);
        }
    }
    @Override
    public void notifyObserversReceivedMessage(Message receivedMessage) {
        if(this.conversationObserver != null) {
            this.conversationObserver.newMessageReceived(receivedMessage, this.isOpen);
        }
    }
    @Override
    public void notifyObserversRetrievedMessages(ArrayList<Message> retrievedMessages) {
        if(this.conversationObserver != null) {
            this.conversationObserver.messagesRetrieved(retrievedMessages);
        }
    }
    //GETTERS
    public ArrayList<Message> getHistory() {
        return history;
    }
    public User getCorrespondent(){return this.correspondent;};
}
