package database;

import java.util.ArrayList;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.database.MessageDatabase;

public class DatabaseTest {
    public static void main(String [] argv) {
        MessageDatabase db = MessageDatabase.getInstance();
        
        db.init();
        
        User fakeCurrentUser = new User("User1", 10, "127.0.0.1");
        User fakeCorrespondent = new User("User2", 20, "127.0.0.1");
        
        Message fakeMessage1 = new Message("hello");
        fakeMessage1.setSource(fakeCurrentUser);
        fakeMessage1.setDestination(fakeCorrespondent);
        
        db.storeMessage(fakeMessage1);
        
        ArrayList<Message> retrievedMessages = db.retrieveOrderedMessagesByConversationBetween(fakeCurrentUser, fakeCorrespondent);
        
        System.out.print("\n\n\n\n(DatabaseTest) - Retrieved messages test result : (must contain 'hello' message(s))\n");
        retrievedMessages.forEach(message -> System.out.println(message));
    }
}
