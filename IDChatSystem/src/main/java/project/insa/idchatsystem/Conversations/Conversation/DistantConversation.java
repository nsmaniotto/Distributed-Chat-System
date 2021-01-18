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
        System.out.printf(".(DistantConversation.java:20) - send : %s\n",String.format("%d,%s",corresp.get_id(),message.toStream()));
        this.server.sendMessage(String.format("%d,%s",corresp.get_id(),message.toStream()),corresp);
    }

    @Override
    public void run() {

    }
}
