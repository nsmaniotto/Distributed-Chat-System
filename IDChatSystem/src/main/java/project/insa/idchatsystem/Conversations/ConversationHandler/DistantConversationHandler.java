package project.insa.idchatsystem.Conversations.ConversationHandler;

import project.insa.idchatsystem.Conversations.Conversation.DistantConversation;
import project.insa.idchatsystem.Conversations.Conversation.LocalConversation;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.servlet.ServerController;

public class DistantConversationHandler extends AbstractLocalConversationHandler {
    private static DistantConversationHandler INSTANCE;
    private ServerController server;
    private DistantConversationHandler(){
        this.server = new ServerController();
    }
    public static DistantConversationHandler getInstance() {
        if(DistantConversationHandler.INSTANCE == null){
            DistantConversationHandler.INSTANCE = new DistantConversationHandler();
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
}
