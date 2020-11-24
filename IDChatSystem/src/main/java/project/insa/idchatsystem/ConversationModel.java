
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
                
        this.run();
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
        
        
        //Notify the view in order to draw the conversation shortcut
        
        this.run();
    }
    
    @Override
    public void run() {
        //TODO if this is the handler  then listen on the socket for incoming datas
        //TODO initialize the socket to be able to receive
        //TODO listen on this socket
        
        //TODO time to times : send a message saying we are still online, recurring scheduled call
    }
    
    /**
     * Method making the handler opening a specific conversation
     *
     * @param id2 : int - id of the correspondent
     */
    public void open(int id2) {
        //TODO retrieve the correspondent corresponding to id2
        
        //TODO check if it is different from the current conversation
        //TODO close the current conversation
        //TODO affect the conversation to currentConversation
        //TODO call open() on the correspondent instance
    }
    
    /**
     * Method making the conversation to open itself
     *
     */
    private void open() {
        this.isOpen = true;
        
        // Load pasts messages
        this.loadConversation();
        
        //TODO notify the client view to show, passing 'this' as an argument
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
