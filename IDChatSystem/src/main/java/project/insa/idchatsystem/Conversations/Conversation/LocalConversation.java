
package project.insa.idchatsystem.Conversations.Conversation;

import project.insa.idchatsystem.Data;
import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import project.insa.idchatsystem.database.MessageDatabase;

public class LocalConversation extends Conversation implements Runnable {
    private Socket socket;

    /**
     * Initialize a passive conversation with a given correspondent
     *
     * @param correspondent : User - reference of the correspondent
     * @param socket : Integer - Socket on which the conversation is
     */
    public LocalConversation(User correspondent, Socket socket) {
        super(correspondent);
        this.socket = socket;
        this.conversationObserver = null;
    }

    @Override
    public void run() {
//        this.loadConversation();
        
        // Listen on the current socket
        this.listen();
    }

    private Message generateMessage(Data data) {
        return null;
    }

    public void loadConversation() {
        ArrayList<Message> history = new ArrayList<>();

        // Retrieve past messages
        try {
            history = MessageDatabase.getInstance().retrieveOrderedMessagesByConversationBetween(User.getCurrentUser(), this.correspondent);
            history.forEach(message -> System.out.println(message));
        } catch (Uninitialized e) {
            // Current user (thereforce message source) is not initialized
            System.out.println("Conversation: EXCEPTION WHILE RETRIEVING PAST MESSAGES " + e);
        }

        // Notify ConversationHandler to display the previously retrieved messages
        if(!history.isEmpty()) {
            this.notifyObserversRetrievedMessages(history);
        }
    }


    /**
     * Listening on the current socket for incoming messages
     * 
     */
    public void listen() {
        BufferedReader inputStream = null;
        String inputBuffer = null;
        
        //Getting the input stream
        try {
            inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch(IOException e) {
            System.out.println("EXCEPTION WHILE RETRIEVING THE INPUT STREAM (" + e + ")");
            System.out.println("Continuing..");
        }
        
        // Continuously listen the input stream
        try {
            while((inputBuffer = inputStream.readLine()) != null) {
                // We received a new message
                this.onReceive(inputBuffer);
            }
        }
        catch(IOException e) {
            // Connection lost with the correspondent
        }
    }

    /**
     * Send a given message to the communication socket
     *
     * @param message : Message - message we want to send and display
     */
    @Override
    public void send(Message message, User corresp) {
//        System.out.printf(".(LocalConversation.java:98) - send : message %s\n",message);
        try {
            message.setSource(User.getCurrentUser());
            message.setDestination(this.correspondent);
        } catch (Uninitialized e) {
            // Current user (thereforce message source) is not initialized
            System.out.println("Conversation: EXCEPTION WHILE SETTING MESSAGE SOURCE " + e);
        }
        
        PrintWriter outputStreamLink = null;
        
        try {
            outputStreamLink = new PrintWriter(this.socket.getOutputStream(),true);
        }
        catch(IOException e) {
            System.out.println("EXCEPTION WHILE RETRIEVING THE SOCKET OUTPUT STREAM (" + e + ")");
            System.exit(0);
        }        
        
        // Send message through the dedicated socket
        outputStreamLink.println(message.toStream());
        
        // Store the message in the local database
        this.storeMessage(message);
        // Notify the handler that a new message has been sent
        this.notifyObserversSentMessage(message);
    }


    
    /* GETTERS/SETTERS */
    public void setSocket(Socket newSocket) {
        this.socket = newSocket;
    }

}