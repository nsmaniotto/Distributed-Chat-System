
package project.insa.idchatsystem;

import java.util.ArrayList;

/**
 * Based on Singleton pattern, a conversation handler is here to collect
 * TCP requests and delegate the accept() call to on generated thread by
 * affecting it a dedicated port.
 *
 * @author nsmaniotto
 */
public class ConversationHandler implements ConversationObservable, Runnable {
    private static ConversationHandler INSTANCE = new ConversationHandler(1234); //TODO change port
    private ArrayList<Conversation> conversations;
    private Conversation currentConversation;
    private int socketPort;
    
    private ConversationHandler(int socketPort) {
        this.socketPort = socketPort;
        this.conversations = new ArrayList<>();
    }
     
    /**
     * Access point of the (unique) instance
     * 
     * @return INSTANCE : ConversationHandler - single instance of this class
     */
    public ConversationHandler getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void run() {
        //TODO initialize socket
        
        //TODO loop listening on this socket and accept then adding to array list or updating (redirect())
    }
    
    /**
     * Method making the handler opening a specific conversation
     *
     * @param correspondent : User - reference of the correspondent
     */
    public void open(User correspondent) {
        //TODO check if it is different from the current conversation
        //TODO check if this conversation exists (-> create or update)
        //TODO close the current conversation
        //TODO affect the conversation to currentConversation
        //TODO accept and delegate redirecting to the conversation thread
        //TODO call open() on the correspondent instance
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
