
package project.insa.idchatsystem;

import java.util.ArrayList;

class Conversation implements ConversationObservable, Runnable {
    private int socket;
    private int port;
    private boolean isOpen;
    private User correspondent;
    private ArrayList<Message> history;

   /**
     * Initialize a conversation with a given correspondent
     *
     * @param correspondent : User - reference of the correspondent
     * @param socket : int - Socket on which the conversation is
     * @param redirectingPort : int - port that the socket WILL use
     */
    public Conversation(User correspondent, int socket, int redirectingPort) {
        this.socket = socket;
        this.port = redirectingPort;
        this.isOpen = false;
        
        this.correspondent = correspondent;
        
        // Empty for now, will be loaded later
        this.history = new ArrayList<>();
    }
    
    @Override
    public void run() {
        // Redirect the connexion
        this.redirect(this.socket, this.port);
        
        ////TODO if this is the handler  then listen on the socket for incoming datas
        //TODO initialize the socket to be able to receive
        //TODO listen on this socket
        
        //TODO time to times : send a message saying we are still online, recurring scheduled call
    }
    
    public void redirect(int socket, int redirectingPort) {
        
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