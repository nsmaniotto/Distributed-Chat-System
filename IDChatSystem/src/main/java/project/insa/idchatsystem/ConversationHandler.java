
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
    private int socketPort;
    
    private ConversationHandler(int socketPort) {
        this.socketPort = socketPort;
        this.conversations = new ArrayList<>();
    }
     
    /**
     * Access point of the (unique) instance
     * 
     */
    public ConversationHandler getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void run() {
        //TODO initialize socket
        
        //TODO loop listening on this socket
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
