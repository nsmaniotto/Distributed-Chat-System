package conversation.reopen;

import project.insa.idchatsystem.Conversations.ConversationHandler.LocalConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

public class ClientTest1 {    
    public static void main(String [] argv) throws NoPortAvailable {
        int id = 1;
        
        // Creating the conversation handler
        LocalConversationHandler conversationHandler = new LocalConversationHandler( );
        new Thread(conversationHandler).start();
        
        // Create fake users
        User user2 = new User("User_2", 2, "127.0.0.1");
        
        conversationHandler.addKnownUser(user2);
        
        // Giving some some time to launch the other test client
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Open a conversation with user 2
        conversationHandler.open(user2);
        
        // Sending messages
        conversationHandler.getCurrentConversation().send(new Message("Now waiting for you to close the conversation"),conversationHandler.getCurrentConversation().getCorrespondent());
        
        // Giving some some time for user2 to close the conversation
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Try sending another message
        conversationHandler.getCurrentConversation().send(new Message("Are you still here ?"),conversationHandler.getCurrentConversation().getCorrespondent());
    }
}
