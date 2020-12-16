package conversation.reopen;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

public class ClientTest2 {    
    public static void main(String [] argv){
        int id = 2;
        
        // Creating the conversation handler
        ConversationHandler conversationHandler = new ConversationHandler(2501, 2500);
        new Thread(conversationHandler).start();
        
        // Create fake users
        User user1 = new User("User_1", 1, "127.0.0.1");
        //User user3 = new User("User_3", 3, "127.0.0.1");
        
        conversationHandler.addKnownUser(user1);
        //conversationHandler.addKnownUser(user3);
        
        // Open then closing a conversation
        conversationHandler.open(user1);
        
        // Sending messages to user1
        conversationHandler.getCurrentConversation().send(new Message("Waiting 10s for your messages"));
        
        // Receiving some messages from user1 (waiting)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Simulate closing of the ocnversation
        conversationHandler.getCurrentConversation().send(new Message("Closing the conversation"));
        conversationHandler.closeCurrentConversation();
        
        // Waiting before going back on the conversation
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Try reopening the conversation
        conversationHandler.open(user1);
        
        // Sending messages to user1
        conversationHandler.getCurrentConversation().send(new Message("I am back !"));
    }
}
