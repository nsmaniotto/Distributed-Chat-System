package project.insa.idchatsystem.Conversations.ConversationHandler;

import project.insa.idchatsystem.Conversations.Conversation.DistantConversation;
import project.insa.idchatsystem.Conversations.Conversation.LocalConversation;
import project.insa.idchatsystem.Observers.Server.Observers.ServerConvControllerObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.servlet.ServerController;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistantConversationHandler extends AbstractConversationHandler implements ServerConvControllerObserver {
    private static DistantConversationHandler INSTANCE;
    private ServerController server;
    private DistantConversationHandler(HashMap<String, User> knownUsers){
        System.out.printf(".(DistantConversationHandler.java:15) - DistantConversationHandler : \n");
        knownUsers.forEach((k,v) -> {
            super.addKnownUser(v);
            System.out.printf(".(DistantConversationHandler.java:20) - DistantConversationHandler : %s\n",v);
        });
        this.server = new ServerController("conv");
        this.server.addConvListener(this);
    }
    public static DistantConversationHandler getInstance(HashMap<String, User> knownUsers) {
        if(DistantConversationHandler.INSTANCE == null){
            DistantConversationHandler.INSTANCE = new DistantConversationHandler(knownUsers);
        }
        return DistantConversationHandler.INSTANCE;
    }
    @Override
    public void open(User correspondent) {
        // Check if we aleady have a conversation instance with this correspondent
        DistantConversation conversation = (DistantConversation)this.findConversationByCorrespondent(correspondent);
        if(conversation == null) {
            // --> We want to initiate the communication with our correspondent
            // Instantiate a socket that will send a request to the correspondent ConversationHandler
            conversation = new DistantConversation(correspondent, this.server);
            this.addConversation(conversation);
        }
        if(conversation != this.currentConversation) {// Close the previous conversation
            this.closeCurrentConversation();

            // Set the opening conversation as our current conversation
            this.currentConversation = conversation;

            // Open the new current conversation
            this.currentConversation.open();
        }
    }
    @Override
    public void addKnownUser(User user) {
        System.out.printf(".(DistantConversationHandler.java:50) - addKnownUser : %s\n",user);
        super.addKnownUser(user);
    }
    @Override
    public void notifyNewMessage(String message) {
        Pattern pattern_getMessage = Pattern.compile("^(?<id>[a-z0-9-]*),User (?<pseudo>[a-z0-9]+) ; id (?<idSrc>[0-9a-zA-Z-]+) ; (?<resteMsg>.*)$");
        Matcher m = pattern_getMessage.matcher(message);
        String message_extracted;
        String idCorresp;
        String pseudo;
        while (m.find()){
            message_extracted = m.group("resteMsg");
            pseudo = m.group("pseudo");
            idCorresp = m.group("idSrc");
            User correspondent = this.findUserById(idCorresp);
            DistantConversation conv = (DistantConversation) this.findConversationByCorrespondent(correspondent);
            if(conv == null) {
                conv = new DistantConversation(correspondent, this.server);
                conv.addConversationObserver(this);

                this.addConversation(conv);

            }
            if(conv != null) {
                conv.onReceive(String.format("User %s ; id %s ; ",pseudo,idCorresp)+message_extracted);
            }
        }
    }
}
