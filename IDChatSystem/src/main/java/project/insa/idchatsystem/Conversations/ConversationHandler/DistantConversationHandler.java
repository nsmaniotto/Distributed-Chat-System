package project.insa.idchatsystem.Conversations.ConversationHandler;

import project.insa.idchatsystem.Conversations.Conversation.DistantConversation;
import project.insa.idchatsystem.Observers.Server.Observers.ServerConvControllerObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.servlet.ServerController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistantConversationHandler extends AbstractConversationHandler implements ServerConvControllerObserver {
    private static DistantConversationHandler INSTANCE;
    private ServerController server;
    private DistantConversationHandler(){
        this.server = new ServerController("conv");
        this.server.addConvListener(this);
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

    @Override
    public void notifyNewMessage(String message) {
        System.out.printf(".(DistantConversationHandler.java:44) - notifyNewMessage : %s\n",message);

        Pattern pattern_getMessage = Pattern.compile("(?<id>[a-z0-9-]+),(?<msg>.*)");
        Matcher m = pattern_getMessage.matcher(message);
        String message_extracted = "";
        String idCorresp = "";
        while (m.find()){
            message_extracted = m.group("msg");
            idCorresp = m.group("id");
            DistantConversation conv = (DistantConversation) this.findConversationByCorrespondent(new User("",idCorresp,""));
            System.out.printf(".(DistantConversationHandler.java:57) - notifyNewMessage : step1\n");
            if(conv != null) {
                System.out.printf(".(DistantConversationHandler.java:59) - notifyNewMessage : step2\n");
                conv.onReceive(message_extracted);
            }
            //System.out.printf("ASKFORUPDATE %s\n", other);
        }
    }
}
