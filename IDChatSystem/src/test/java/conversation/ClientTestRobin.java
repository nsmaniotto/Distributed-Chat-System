/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import java.util.Scanner;
import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

/**
 *
 * @author smani
 */
public class ClientTestRobin {
    public ClientTestRobin() {
        ConversationHandler conversationHandler = ConversationHandler.getInstance();
        //ClientView clientView = new ClientView();
        
        
        /* TESTING TCP COMMUNICATIONS */
          
        
        // Initialize our current user
        User.init_current_user(456);
        try {
            User.set_current_username("Robin");
        } catch (Exception e) {
            
        }
        
        
        User user1 = new User("Nathan", 123, "127.0.0.1");
        
        conversationHandler.addKnownUser(user1);
        
        conversationHandler.open(user1); // Sending a message to Nathan
        conversationHandler.getCurrentConversation().send(new Message("Hi Nathan !"));
        
        String message;
        
        try ( Scanner scanner = new Scanner( System.in ) ) {
            
            message = scanner.nextLine();
            conversationHandler.getCurrentConversation().send(new Message(message));
                
            message = scanner.nextLine();
            conversationHandler.getCurrentConversation().send(new Message(message));
        }
    }
}