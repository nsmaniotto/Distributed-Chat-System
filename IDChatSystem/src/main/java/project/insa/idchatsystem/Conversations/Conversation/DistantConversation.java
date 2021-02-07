package project.insa.idchatsystem.Conversations.Conversation;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.servlet.ServerController;

import java.sql.Timestamp;

public class DistantConversation extends Conversation {
    private ServerController server;
    public DistantConversation(User correspondent, ServerController server) {
        super(correspondent);
        System.out.printf(".(DistantConversation.java:14) - DistantConversation : %s\n",correspondent);
        this.server = server;
        this.loadConversation();
    }
    @Override
    public void send(Message message,User corresp) {
        System.out.printf(".(DistantConversation.java:20) - send : %s\n",String.format("%s,%s",corresp.get_id(),message.toStream()));
        this.server.sendMessage(String.format("%s,%s",corresp.get_id(),message.toStream()),corresp);
        try {
            this.notifyObserversSentMessage(new Message(User.getCurrentUser(),corresp,message.getText(),new Timestamp(System.currentTimeMillis())));
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
