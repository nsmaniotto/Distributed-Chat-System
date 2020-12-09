/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

/**
 *
 * @author smani
 */
public class ClientTestRobin {
    public ClientTestRobin() {
        int id = 20;
        ClientController controller = new ClientController(id);
        
        //ConversationHandler conversationHandler = ConversationHandler.getInstance();
        ConversationHandler conversationHandler = controller.getConversationHandler();
        //new Thread(conversationHandler).start();
        
        /* TESTING TCP COMMUNICATIONS */
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            
        }
        
        // Initialize our current user
        User.init_current_user(id);
        try {
            User.set_current_username("Robin");
        } catch (Exception e) {
            
        }
        
        
        User user1 = new User("Nathan", 10, "127.0.0.1");
        
        controller.onlineUser(user1);
        //conversationHandler.addKnownUser(user1);
        
        conversationHandler.open(user1); // Sending a message to Nathan
        conversationHandler.getCurrentConversation().send(new Message("Hi Nathan !"));
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            
        }
        
        conversationHandler.getCurrentConversation().send(new Message("Hello again !"));
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            
        }
        
        conversationHandler.getCurrentConversation().send(new Message(":-)"));
    }
}