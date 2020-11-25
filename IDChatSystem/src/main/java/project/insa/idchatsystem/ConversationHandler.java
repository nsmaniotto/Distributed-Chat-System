
package project.insa.idchatsystem;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    
    private ArrayList<User> users; // Copy of UserModel's hashmap to identify every user

    private ExecutorService conversationThreadPool;
    private ServerSocket handlerSocket; // Acts as a server listening for incoming connection requests
    private Integer port;
    
    private ConversationHandler(Integer socketPort) {
        this.port = socketPort;
        
        this.conversations = new ArrayList<>();
        this.users = new ArrayList<>(); // Empty for now
        
        // Create an open ended thread-pool for our conversations which are threads
        this.conversationThreadPool = Executors.newCachedThreadPool();
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
        // Initializing the handler socket instance
        try {
            this.handlerSocket = new ServerSocket(this.port);
        }
        catch(IOException e) {
            System.out.println("EXCEPTION: CANNOT CREATE SOCKET ON PORT " + this.port + " (" + e + ")");
            System.exit(0);
        }
        
        //TODO loop listening on this socket and accept then adding to array list or updating (redirect())
        // Waiting for prospective agent connections requests (not blocking thanks to threads use)
        try {
            while(!Thread.currentThread().isInterrupted()) {
                // Wait for an agent to send a connection request
                Socket conversationSocket = this.handlerSocket.accept();

                // Retrieve correspondent informations thanks to its address
                String correspondentAddress = conversationSocket.getRemoteSocketAddress().toString();
                User correspondent = this.findUserByAddress(correspondentAddress);

                if(correspondent != null) {
                    //TODO Check if we arleady have a conversation instance with this correspondent
                    Conversation newConversation = this.findConversationByCorrespondent(correspondent);

                    // If we do not have a current conversation instance for this correspondent
                    if(newConversation == null) {
                        // Generate a free port
                        Integer redirectingPort = this.generateFreePort();
                        
                        // Instanciate a new conversation with the given redirecting port and socket
                        newConversation = new Conversation(correspondent, conversationSocket, redirectingPort);
                        
                        // Add this new conversation to our array list, used later for port generation
                        this.conversations.add(newConversation);
                        
                        // Add this new conversation thread to our thread pool
                        this.conversationThreadPool.submit(newConversation);
                    } else {
                        // Redirect the communication on the conversation dedicated port
                        newConversation.redirect(conversationSocket, newConversation.getPort());
                    }
                } else {
                    // ERROR: no such user found
                }            
            }
        } catch(IOException e) {
            System.out.println("Exception '" + e + "' lors de l'attente d'un socket client");
            System.exit(0);
        }
    }
    
    /**
     * Find the corresponding user in users array list
     *
     * @param correspondentAddress : String - address of the correspondent we want to find
     * @return User - null if not found
     */
    private User findUserByAddress(String correspondentAddress) {
        for(User user : this.users) {
            if (user.get_ipAddress().equals(correspondentAddress)) {
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * Find the corresponding conversation in conversations array list
     *
     * @param correspondent : User - address of the correspondent we want to find
     * @return Conversation - null if not found
     */
    private Conversation findConversationByCorrespondent(User correspondent) {
        for(Conversation conversation : this.conversations) {
            if (conversation.getCorrespondent().equals(correspondent)) {
                return conversation;
            }
        }
        
        return null;
    }
    
    /**
     * Generate a port that is not already taken by checking taken port in conversations
     *
     * @return Integer - free port
     */
    private Integer generateFreePort() {
        Integer i = 0;
        Integer[] takenPorts = new Integer[this.conversations.size() + 1]; // +1 for the handler port
        
        // Fill this array with already taken ports
        takenPorts[0] = this.port;
        
        for(Conversation conversation : this.conversations) {
            i++;
            
            takenPorts[i] = conversation.getPort();
        }
        
        // Pick a random port and see if it is available
        boolean isTaken = true;
        Integer randomPort = null;
        
        do {
            randomPort = (new Random()).nextInt(65536 - 1025) + 1025; // 1025 inclusive, 65536 exclusive
            
            isTaken = (Arrays.asList(takenPorts).indexOf(randomPort) != -1);
        } while(isTaken);
        
        return randomPort;
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
