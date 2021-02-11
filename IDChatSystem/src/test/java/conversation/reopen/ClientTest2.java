package conversation.reopen;

import project.insa.idchatsystem.Conversations.ConversationHandler.LocalConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

public class ClientTest2 {    
    public static void main(String [] argv) throws NoPortAvailable {
        int id = 2;
        
        // Creating the conversation handler
        LocalConversationHandler conversationHandler = new LocalConversationHandler();
        new Thread(conversationHandler).start();
        
        // Simulate this user
        User user2 = new User("User_2", "2", "127.0.0.1");
        
        // Create fake users
        User user1 = new User("User_1", "1", "127.0.0.1");
        
        conversationHandler.addKnownUser(user1);
        //conversationHandler.addKnownUser(user3);
        
        // Open then closing a conversation
        conversationHandler.open(user1);
        
        // Sending messages to user1
        Message message1 = new Message("Waiting 10s for your messages");
        message1.setSource(user2);
        message1.setDestination(user1);
        conversationHandler.getCurrentConversation().send(message1);
        
        // Receiving some messages from user1 (waiting)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Simulate closing of the ocnversation
        Message message2 = new Message("Closing the conversation");
        message2.setSource(user2);
        message2.setDestination(user1);
        conversationHandler.getCurrentConversation().send(message2);
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
        Message message3 = new Message("I am back !");
        message3.setSource(user2);
        message3.setDestination(user1);
        conversationHandler.getCurrentConversation().send(message3);
    }
}
