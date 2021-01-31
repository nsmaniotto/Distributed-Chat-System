package project.insa.idchatsystem.Conversations.ConversationHandler;

import project.insa.idchatsystem.Conversations.Conversation.Conversation;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.Observables.ConversationHandlerObservable;
import project.insa.idchatsystem.Observers.Conversations.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.Conversations.Observers.ConversationObserver;
import project.insa.idchatsystem.Observers.Conversations.Observers.LocalConversationHandlerObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractConversationHandler implements ConversationObserver, ConversationHandlerObservable {
    protected ArrayList<Conversation> conversations;
    protected Conversation currentConversation = null;
    protected HashMap<String, User> users; // Copy of UserModel's hashmap to identify every user
    protected ArrayList<ConversationHandlerObserver> observers;
    protected final ExecutorService conversationThreadPool;
    public AbstractConversationHandler(){
        this.observers = new ArrayList<>();
        this.conversations = new ArrayList<>();
        this.users = new HashMap<>(); // Empty for now
        // Create an open ended thread-pool for our conversations which are threads
        this.conversationThreadPool = Executors.newCachedThreadPool();
    }
    /**
     * Find the corresponding user in users array list
     *
     * @param correspondentAddress : String - address of the correspondent we want to find
     * @return User - null if not found
     */
    protected User findUserByAddress(String correspondentAddress) {
        AtomicReference<User> userToFind = new AtomicReference<>();//Suggested by the IDE with the forEach...
        this.users.forEach((k,user) -> {
            if (user.get_ipAddress().equals(correspondentAddress)) {
                userToFind.set(user);
            }
        });

        return userToFind.get();
    }
    /**
     * Find the corresponding conversation in conversations array list
     *
     * @param correspondent : User - address of the correspondent we want to find
     * @return Conversation - null if not found
     */
    protected Conversation findConversationByCorrespondent(User correspondent) {
        for(Conversation conversation : this.conversations) {
            if (conversation.getCorrespondent().equals(correspondent))
                return conversation;
        }
        return null;
    }
    /**
     * Add a new conversation to our array and thread pool
     *
     * @param newConversation : Conversation - conversation to add
     */
    protected void addConversation(Conversation newConversation) {
        System.out.printf("ADD CONVERSATION\n");
        // Add this new conversation to our array list, used for conversation search
        this.conversations.add(newConversation);

        // Add 'this' reference as an observer in order to process future events
        newConversation.addConversationObserver(this);

        // Add this new conversation thread to our thread pool
        this.conversationThreadPool.submit(newConversation);
    }
    public ArrayList<Message> setCurrentConversation(User user) {
        Conversation conv = this.findConversationByCorrespondent(user);
        this.conversations.forEach(conversation -> {
            if(!conversation.equals(conv))
                conversation.close();
            else
                conversation.open();
        });
        if(conv != null) {
            return conv.getHistory();
        }
        return null;
    }
    public void noCurrentConversation() {
        this.conversations.forEach(Conversation::close);
    }
    public abstract void open(User correspondent);
    /**
     * Closing the current conversation
     * No need to notify the controller because the order comes from the view (then the controller)
     */
    public void closeCurrentConversation() {
        if(this.currentConversation != null) {
            this.currentConversation.close();
            this.currentConversation = null;
        }
    }
    /**
     * Method called to keep track of all the user, will be replaced by listeners later
     *
     * @param newUser
     */
    public void addKnownUser(User newUser) {
        this.users.put(newUser.get_id(),newUser);
        //System.out.printf("Adding user %s\n",newUser);
    }
    public void removeKnownUser(User user){
        this.users.remove(user.get_id(),user);
        System.out.printf("CONVERSATIONHANDLER : Removing user %s\n",user);
    }
    public void offlineUser(User user) {
        this.removeKnownUser(user);
    }
    //OBSERVERS

    @Override
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation) {
        for(ConversationHandlerObserver observer : this.observers) {
            observer.newMessageReceived(receivedMessage, isCurrentConversation);
        }
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        System.out.printf(".(AbstractConversationHandler.java:129) - newMessageSent\n");
        this.observers.forEach( observer -> observer.newMessageSent(sentMessage) );
    }
    //GETTERS

    public HashMap<String,User> getUsers() {
        return users;
    }
    /* GETTERS/SETTERS */
    public Conversation getCurrentConversation() {
//        System.out.printf(".(AbstractConversationHandler.java:138) - getCurrentConversation : %s\n",this.currentConversation);
        return this.currentConversation;
    }
    public void notifyObserversRetrievedMessages(ArrayList<Message> retrievedMessages) {
        this.observers.forEach(observer -> observer.messagesRetrieved(retrievedMessages));
    }

    public void messagesRetrieved(ArrayList<Message> retrievedMessages) {
        this.notifyObserversRetrievedMessages(retrievedMessages);
    }
    @Override
    public void addObserver(ConversationHandlerObserver observer) {
        System.out.printf(".(AbstractConversationHandler.java:151) - addObserver\n");
        this.observers.add(observer);
    }

    @Override
    public void deleteObserver(ConversationHandlerObserver obs) {
        this.observers.remove(obs);
    }

}
