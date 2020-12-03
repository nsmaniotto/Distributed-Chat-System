/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.User.distanciel.User;

/**
 *
 * @author smani
 */
public class ClientTestNathan {    
    public ClientTestNathan() {
        ConversationHandler conversationHandler = ConversationHandler.getInstance();
        new Thread(conversationHandler).start();
        //ClientView clientView = new ClientView();
        
        
        /* TESTING TCP COMMUNICATIONS */
          
        
        // Initialize our current user
        User.init_current_user(12345);
        try {
            User.set_current_username("Nathan");
        } catch (Exception e) {
            
        }
        
        
        User user1 = new User("Robin", 456, "/127.0.0.1");
        
        conversationHandler.addKnownUser(user1);
       
        
        //conversationHandler.open(user1); // Sending a message to Robin
        //conversationHandler.getCurrentConversation().send(new Message("Hi Robin !"));
    }
}
