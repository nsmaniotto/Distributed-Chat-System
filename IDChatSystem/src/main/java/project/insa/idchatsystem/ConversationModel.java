
package project.insa.idchatsystem;

import java.util.ArrayList;

class ConversationModel implements ConversationObservable, Runnable {
    private int id1;
    private boolean isOpen;
    private User correspondent;
    private ArrayList<Message> history;
    private ArrayList<ConversationModel> conversationChildren;
    private String networkType;

    /**
     * Constructor called to instantiate the conversationHandler
     *
     * @param id1 : int - id of the current user
     */
    public ConversationModel(int id1) {
        this.id1 = id1;
        this.isOpen = false;
        this.correspondent = null; // Because this constructor is for the handler
        this.history = null; // Because this constructor is for the handler
        this.conversationChildren = new ArrayList<ConversationModel>();
        this.networkType = "none"; // Not applicable
        
        // Do not start any thread because the handler doe not require one
    }

    /**
     * Constructor called to instantiate a conversation with a correspondent
     *
     * @param id1 : int - id of the current user
     * @param id2 : int - id of the correspondent
     * @param mode : String - type of the communication : local/distant
     */
    public ConversationModel(int id1, int id2, String mode) {
        this.id1 = id1;
        
        this.isOpen = false;
        
        //TODO retrieve User using id2
        this.correspondent = new User();
        
        //TODO retrieve past messages
        this.history = new ArrayList<>();
        
        this.conversationChildren = null; // Because a conversation has no children
        
        this.networkType = mode;
        
        //TODO create sockets tobe able to communicate
        
        
        this.run();
    }
    
    @Override
    public void run() {
        System.out.println("child created");
    }
    
    public void createConversation(int id2, String mode) {
        // Verify if 'this' is the conversation handler
        if(this.correspondent != null) {
            // Verify that there is no current conversation with this correspondent (id2)
            if(this.conversationChildren != null) {
                this.conversationChildren.add(new ConversationModel(this.id1, id2, mode));
            }
        }
    }

    private void storeMessage(Message message) {

    }

    private Message generateMessage(Data data) {
        return null;
    }

    private void loadConversation() {

    }

    public void onReceive(Data data) {

    }

    private void send(Data data) {

    }

    @Override
    public void addObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
