
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            this.announceCurrentUser(User.getCurrentUser());
        } catch (Uninitialized ex) {
            Logger.getLogger(LocalConversation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Listen on the current socket
        this.listen();
    }


    /**
     * Listening on the current socket for incoming messages
     * 
     */
    public synchronized void listen() {
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
                System.out.printf(".(LocalConversation.java:65) - listen : localReceive\n");
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
    public void send(Data data) {
        
        System.out.printf(".(LocalConversation.java:104) - send : data: %s\n", data);
        try {
            data.setSource(User.getCurrentUser());
            data.setDestination(this.correspondent);
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
        outputStreamLink.println(data.toStream());
        
        // Check if the data is a message
        if(data.isMessage()) {
            // Store the message in the local database
            this.storeMessage((Message)data);
            // Notify the handler that a new message has been sent
            this.notifyObserversSentMessage((Message)data);
        }
    }
    
    /* GETTERS/SETTERS */
    public void setSocket(Socket newSocket) {
        this.socket = newSocket;
    }

}