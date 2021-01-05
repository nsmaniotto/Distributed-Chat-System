package project.insa.idchatsystem.Conversations.Conversation;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.servlet.ServerController;

public class DistantConversation extends Conversation {
    private ServerController server;
    public DistantConversation(User correspondent, ServerController server) {
        super(correspondent);
        this.server = server;
        this.loadConversation();
    }
    @Override
    public void loadConversation() {

    }
    @Override
    public void send(Message message,User corresp) {
        this.server.sendMessage(String.format("conversation,%s",message.toStream()),corresp);
    }

    @Override
    public void run() {

    }
}
