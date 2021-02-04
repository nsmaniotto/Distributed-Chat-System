
package project.insa.idchatsystem.Conversations.ConversationHandler;

import project.insa.idchatsystem.Conversations.Conversation.LocalConversation;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Observers.Conversations.Observables.LocalConversationHandlerObservable;
import project.insa.idchatsystem.Observers.Conversations.Observers.LocalConversationHandlerObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.tools.TestPort;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Based on Singleton pattern, a conversation handler is here to collect
 * TCP requests and delegate the accept() call to on generated thread by
 * affecting it a dedicated port.
 *
 * @author nsmaniotto
 */
public class LocalConversationHandler extends AbstractConversationHandler implements LocalConversationHandlerObservable,Runnable {
    private static LocalConversationHandler INSTANCE;
    private ArrayList<LocalConversation> conversations;
    
    private HashMap<Integer,User> users; // Copy of UserModel's hashmap to identify every user

    private ServerSocket handlerSocket; // Acts as a server listening for incoming connection requests
    private final int listenerPort;
    private final int destinationPort;
    private int MINLISTENERPORT = 1500;
    private int MAXCONVERSATIONSPORTS = 500;
    
    public LocalConversationHandler() throws NoPortAvailable {
        super();
        //Choose a port to listen
        int port = this.MINLISTENERPORT;
        while (!TestPort.portIsavailable(port) && port < this.MINLISTENERPORT +this.MAXCONVERSATIONSPORTS) {
            port++;
        }
        if(port == this.MINLISTENERPORT +this.MAXCONVERSATIONSPORTS) {
            throw new NoPortAvailable("ConversationHandler");
        }

        this.listenerPort = port;

        this.destinationPort = -1;
    }
     
    /**
     * Access point of the (unique) instance
     * 
     * @return INSTANCE : ConversationHandler - single instance of this class
     */
    public static LocalConversationHandler getInstance() throws NoPortAvailable {
        if(LocalConversationHandler.INSTANCE == null){
            LocalConversationHandler.INSTANCE = new LocalConversationHandler();
        }
        return LocalConversationHandler.INSTANCE;
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

                //TODO : pb vient de là
                if(correspondent != null) {
                    // Check if we arleady have a conversation instance with this correspondent
                    LocalConversation newConversation = (LocalConversation)this.findConversationByCorrespondent(correspondent);

                    // If we do not have a current conversation instance for this correspondent
                    if(newConversation == null) {
                        // Instantiate a new conversation with the given socket
                        //TODO : transmettre en paramètres les observeurs à notifier
                        newConversation = new LocalConversation(correspondent, conversationSocket);
                        newConversation.addConversationObserver(this);
                        
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
     * Method making the handler opening a specific conversation
     *
     * @param correspondent : User - reference of the correspondent
     */
    public void open(User correspondent) {
        // Check if we aleady have a conversation instance with this correspondent
        LocalConversation conversation = (LocalConversation)this.findConversationByCorrespondent(correspondent);
        
        if(conversation == null) {
            // --> We want to initiate the communication with our correspondent
            // Instantiate a socket that will send a request to the correspondent ConversationHandler
            Socket conversationSocket = null;

            try {
                conversationSocket = new Socket(InetAddress.getByName(correspondent.get_ipAddress()), correspondent.getConversationHandlerListenerPort());
            } catch(IOException e) {
//                System.out.println("EXCEPTION: CANNOT CREATE CONVERSATION SOCKET TOWARDS " + correspondent.get_ipAddress() + ":" + correspondent.getConversationHandlerListenerPort() + " (" + e + ")");
                System.exit(0);
            }

            // Instantiate a new conversation with the given socket
            conversation = new LocalConversation(correspondent, conversationSocket);

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
    //OBSERVERS
    @Override
    public void notifyListenerPortNegociated() {
        this.observers.forEach(obs -> {
            if(obs instanceof LocalConversationHandlerObserver)
                obs.listenerPortChosen(this.listenerPort);
        });
    }
    @Override
    public void addObserver(LocalConversationHandlerObserver observer) {
        super.addObserver(observer);
        this.notifyListenerPortNegociated();
    }

    @Override
    public void deleteObserver(LocalConversationHandlerObserver obs) {
        super.deleteObserver(obs);
    }

}
