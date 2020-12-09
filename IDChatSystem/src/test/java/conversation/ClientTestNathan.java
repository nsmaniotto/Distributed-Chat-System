/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.User.distanciel.User;

/**
 *
 * @author smani
 */
public class ClientTestNathan {    
    public ClientTestNathan() {
        int id = 10;
        ClientController controller = new ClientController(id);
        
        //ConversationHandler conversationHandler = ConversationHandler.getInstance();
        ConversationHandler conversationHandler = controller.getConversationHandler();
        new Thread(conversationHandler).start();
        
        
        /* TESTING TCP COMMUNICATIONS */
          
        
        // Initialize our current user
        User.init_current_user(id);
        try {
            User.set_current_username("Nathan");
        } catch (Exception e) {
            
        }
        
        
        User user1 = new User("Robin", 20, "/127.0.0.1");
        
        controller.onlineUser(user1);
        //conversationHandler.addKnownUser(user1);
    }
}
