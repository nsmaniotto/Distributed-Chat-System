package project.insa.idchatsystem.Conversations.Conversation;

import project.insa.idchatsystem.Data;
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
    public void send(Data data,User corresp) {
//        System.out.printf(".(DistantConversation.java:20) - send : %s\n",String.format("%s,%s",corresp.get_id(),data.toStream()));
        this.server.sendMessage(String.format("%s,%s",corresp.get_id(),data.toStream()),corresp);
    }

    @Override
    public void run() {

    }
}
