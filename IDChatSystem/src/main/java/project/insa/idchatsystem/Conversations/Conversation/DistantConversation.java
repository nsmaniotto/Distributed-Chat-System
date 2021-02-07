package project.insa.idchatsystem.Conversations.Conversation;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Data;
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
    public void send(Data data,User corresp) {
        System.out.printf(".(DistantConversation.java:20) - send : %s\n",String.format("%s,%s",corresp.get_id(),data.toStream()));
        
        if(data.isMessage()) {
            Message message = (Message)data;
            
            this.server.sendMessage(String.format("%s,%s",corresp.get_id(),message.toStream()),corresp);
            
            try {
                this.notifyObserversSentMessage(new Message(User.getCurrentUser(),corresp,message.getText(),new Timestamp(System.currentTimeMillis())));
            } catch (Uninitialized uninitialized) {
                uninitialized.printStackTrace();
            }
        } else if (data.isInformation()) {
            this.server.sendMessage(data.toStream(),corresp);
        }
    }
    
    public void loadConversation() {
        
    }

    @Override
    public void run() {

    }
}
