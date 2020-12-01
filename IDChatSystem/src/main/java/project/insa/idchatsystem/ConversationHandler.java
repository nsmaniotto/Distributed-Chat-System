
package project.insa.idchatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private static final ConversationHandler INSTANCE = new ConversationHandler(1234); //TODO change port
    private final ArrayList<Conversation> conversations;
    private Conversation currentConversation;
    
    private final ArrayList<User> users; // Copy of UserModel's hashmap to identify every user

    private final ExecutorService conversationThreadPool;
    private ServerSocket handlerSocket; // Acts as a server listening for incoming connection requests
    private final Integer port;
    
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
    public static ConversationHandler getInstance() {
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
        
        // Waiting for prospective agent connections requests (not blocking thanks to threads use)
        try {
            while(!Thread.currentThread().isInterrupted()) {
                // Wait for an agent to send a connection request
                Socket conversationSocket = this.handlerSocket.accept();

                // Retrieve correspondent informations thanks to its address
                //String correspondentAddress = conversationSocket.getRemoteSocketAddress().toString(); // Address like '/127.0.0.1:53818'
                InetAddress correspondentAddress = conversationSocket.getInetAddress(); // Address like '/127.0.0.1'
                User correspondent = this.findUserByAddress(correspondentAddress.toString());
                
                if(correspondent != null) {
                    // Check if we arleady have a conversation instance with this correspondent
                    Conversation newConversation = this.findConversationByCorrespondent(correspondent);

                    // If we do not have a current conversation instance for this correspondent
                    if(newConversation == null) {
                        // Instantiate a new conversation with the given socket
                        newConversation = new Conversation(correspondent, conversationSocket);
                        
                        this.addConversation(newConversation);
                    } else {
                        // Update the conversation socket
                        newConversation.setSocket(conversationSocket);
                    }
                } else {
                    // ERROR: no such user found
                    System.out.println("ERROR: no such user found when creating a conversation");
                }            
            }
        } catch(IOException e) {
            System.out.println("EXCEPTION WHILE WAITING FOR INCOMING CONNECTION REQUESTS (" + e + ")");
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
     * Add a new conversation to our array and thread pool
     *
     * @param newConversation : Conversation - conversation to add
     */
    private void addConversation(Conversation newConversation) {
        // Add this new conversation to our array list, used for conversation search 
        this.conversations.add(newConversation);

        // Add this new conversation thread to our thread pool
        this.conversationThreadPool.submit(newConversation);
    }
    
    /**
     * Method making the handler opening a specific conversation
     *
     * @param correspondent : User - reference of the correspondent
     */
    public void open(User correspondent) {
        // Check if we aleady have a conversation instance with this correspondent
        Conversation conversation = this.findConversationByCorrespondent(correspondent);
        
        if(conversation == null) {
            // --> We want to initiate the communication with our correspondent
            // Instantiate a socket that will send a request to the correspondent ConversationHandler
            Socket conversationSocket = null;

            try {
                conversationSocket = new Socket(InetAddress.getByName(correspondent.get_ipAddress()), this.port);
            } catch(IOException e) {
                System.out.println("EXCEPTION: CANNOT CREATE CONVERSATION SOCKET TOWARDS " + correspondent.get_ipAddress() + ":" + this.port + " (" + e + ")");
                System.exit(0);
            }

            // Instantiate a new conversation with the given socket
            conversation = new Conversation(correspondent, conversationSocket);

            this.addConversation(conversation);

            // Set the opening conversation as our current conversation
            this.currentConversation = conversation;
        }
        
        if(conversation != this.currentConversation) {
            // Close the previous conversation
            this.currentConversation.close();

            // Set the opening conversation as our current conversation
            this.currentConversation = conversation;
            
            // Open the new current conversation
            this.currentConversation.open();
        }
    }
    
    /**
     * Method called to keep track of all the user, will be replaced by listeners later
     * 
     * @param newUser 
     */
    public void addKnownUser(User newUser) {
        this.users.add(newUser);
    }
    
    /* CONVERSATION OBSERVER METHODS */
    
    @Override
    public void addObserver(Object conversationObserver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObserver(Object conversationObserver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObserversNewMessageSent(Message sentMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObserversNewMessageReceived(Message receivedMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /* GETTERS/SETTERS */
    public Conversation getCurrentConversation() {
        return this.currentConversation;
    }
}
