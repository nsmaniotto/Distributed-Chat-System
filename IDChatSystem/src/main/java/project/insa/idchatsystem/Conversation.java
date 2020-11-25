
package project.insa.idchatsystem;

import java.net.Socket;
import java.util.ArrayList;

class Conversation implements ConversationObservable, Runnable {
    private Socket socket;
    private boolean isOpen;
    private User correspondent;
    private ArrayList<Message> history;

   /**
     * Initialize a passive conversation with a given correspondent
     *
     * @param correspondent : User - reference of the correspondent
     * @param socket : Integer - Socket on which the conversation is
     */
    public Conversation(User correspondent, Socket socket) {
        this.socket = socket;
        this.isOpen = false;
        
        this.correspondent = correspondent;
        
        // Empty for now, will be loaded later
        this.history = new ArrayList<>();
    }
    
    @Override
    public void run() {
        this.loadConversation();
        
        //TODO listen on the current socket
    }
    
    /**
     * Method making the conversation to open itself
     *
     */
    public void open() {
        this.isOpen = true;
        
        // Load pasts messages
        this.loadConversation();
        
        //TODO notify the client view to show, passing 'this' as an argument
    }    
    
    /**
     * Method making the conversation to close itself
     *
     */
    public void close() {
        this.isOpen = false;
        
        //TODO close socket unilaterally
        
        //TODO notify the client view to close this conversation
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
    
    /* GETTERS/SETTERS */
    public User getCorrespondent() {
        return this.correspondent;
    }
    
    public void setSocket(Socket newSocket) {
        this.socket = newSocket;
    }
}