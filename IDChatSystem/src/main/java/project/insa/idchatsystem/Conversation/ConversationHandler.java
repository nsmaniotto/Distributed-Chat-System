
package project.insa.idchatsystem.Conversation;

import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.ConversationHandlerObservable;
import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.ConversationObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.tools.TestPort;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Based on Singleton pattern, a conversation handler is here to collect
 * TCP requests and delegate the accept() call to on generated thread by
 * affecting it a dedicated port.
 *
 * @author nsmaniotto
 */
public class ConversationHandler implements ConversationObserver, ConversationHandlerObservable, Runnable {
    private static ConversationHandler INSTANCE;
    private ArrayList<Conversation> conversations;
    private Conversation currentConversation = null;
    
    private HashMap<Integer,User> users; // Copy of UserModel's hashmap to identify every user

    private final ExecutorService conversationThreadPool;
    private ServerSocket handlerSocket; // Acts as a server listening for incoming connection requests
    private final int listenerPort;
    private final int destinationPort;
    private ArrayList<ConversationHandlerObserver> observers;
    private int MINLISTENERPORT = 1500;
    private int MAXCONVERSATIONSPORTS = 100;
    
    public ConversationHandler() throws NoPortAvailable {
        // Empty for now
        this.observers = new ArrayList<>();
        //Choose a port to listen
        int port = this.MINLISTENERPORT;
        while (!TestPort.portIsavailable(port) && port < this.MINLISTENERPORT +this.MAXCONVERSATIONSPORTS) {
            port++;
        }
        if(port == this.MINLISTENERPORT +this.MAXCONVERSATIONSPORTS) {
            throw new NoPortAvailable("ConversationHandler");
        }

        this.listenerPort = port;
        this.notifyListenerPortNegociated();
        this.destinationPort = -1;
        
        this.conversations = new ArrayList<>();
        this.users = new HashMap<>(); // Empty for now

        // Create an open ended thread-pool for our conversations which are threads
        this.conversationThreadPool = Executors.newCachedThreadPool();

    }
     
    /**
     * Access point of the (unique) instance
     * 
     * @return INSTANCE : ConversationHandler - single instance of this class
     */
    public static ConversationHandler getInstance() throws NoPortAvailable {
        if(ConversationHandler.INSTANCE == null){
            ConversationHandler.INSTANCE = new ConversationHandler();
        }
        return ConversationHandler.INSTANCE;
    }
    
    @Override
    public void run() {
        // Initializing the handler socket instance
        try {
            this.handlerSocket = new ServerSocket(this.listenerPort);
        }
        catch(IOException e) {
            System.out.println("EXCEPTION: CANNOT CREATE SOCKET ON PORT " + this.listenerPort + " (" + e + ")");
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
                User correspondent = this.findUserByAddress(correspondentAddress.toString().substring(1)); // Find with removed '/'
                
                if(correspondent != null) {
                    // Check if we arleady have a conversation instance with this correspondent
                    Conversation newConversation = this.findConversationByCorrespondent(correspondent);

                    // If we do not have a current conversation instance for this correspondent
                    if(newConversation == null) {
                        // Instantiate a new conversation with the given socket
                        //TODO : transmettre en paramètres les observeurs à notifier
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
    private Conversation findConversationByCorrespondent(User correspondent) {
        for(Conversation conversation : this.conversations) {
            if (conversation.getCorrespondent().equals(correspondent)) {
                return conversation;
            }
        }
        
        return null;
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
    /**
     * Add a new conversation to our array and thread pool
     *
     * @param newConversation : Conversation - conversation to add
     */
    private void addConversation(Conversation newConversation) {
        System.out.printf("ADD CONVERSATION\n");
        // Add this new conversation to our array list, used for conversation search 
        this.conversations.add(newConversation);
        
        // Add 'this' reference as an observer in order to process future events
        newConversation.addConversationObserver(this);

        // Add this new conversation thread to our thread pool
        this.conversationThreadPool.submit(newConversation);
        if(this.currentConversation==null)
            this.currentConversation = newConversation;
        System.out.printf("CURRENT CONV : %s\n",this.currentConversation);
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
                conversationSocket = new Socket(InetAddress.getByName(correspondent.get_ipAddress()), correspondent.getConversationHandlerListenerPort());
            } catch(IOException e) {
                System.out.println("EXCEPTION: CANNOT CREATE CONVERSATION SOCKET TOWARDS " + correspondent.get_ipAddress() + ":" + correspondent.getConversationHandlerListenerPort() + " (" + e + ")");
                System.exit(0);
            }

            // Instantiate a new conversation with the given socket
            conversation = new Conversation(correspondent, conversationSocket);

            this.addConversation(conversation);
        }
        
        if(conversation != this.currentConversation) {
            // Close the previous conversation
            this.closeCurrentConversation();

            // Set the opening conversation as our current conversation
            this.currentConversation = conversation;
            
            // Open the new current conversation
            this.currentConversation.open();
        }
    }
    
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

    public HashMap<Integer,User> getUsers() {
        return users;
    }

    @Override
    public void addObserver(ConversationHandlerObserver observer) {
        this.observers.add(observer);
        this.notifyListenerPortNegociated();
    }

    @Override
    public void deleteObserver(ConversationHandlerObserver obs) {
        this.observers.remove(obs);
    }

    public void offlineUser(User user) {

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
    
    /* CONVERSATION HANDLER OBSERVER METHODS */
    
    @Override
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation) {  
        for(ConversationHandlerObserver observer : this.observers) {
            observer.newMessageReceived(receivedMessage, isCurrentConversation);
        }
    }

    @Override
    public void newMessageSent(Message sentMessage) {
        this.observers.forEach( observer -> observer.newMessageSent(sentMessage) );
    }
    
    /* GETTERS/SETTERS */
    public Conversation getCurrentConversation() {
        System.out.printf("-------------------getCurrentConversation %s\n",this.currentConversation);

        return this.currentConversation;
    }

    @Override
    public void notifyListenerPortNegociated() {
        this.observers.forEach(obs -> obs.listenerPortChosen(this.listenerPort));
    }
}
