
package project.insa.idchatsystem.Observers;

import java.util.ArrayList;
import project.insa.idchatsystem.Message;

public interface ConversationHandlerObserver {
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation);
    public void newMessageSent(Message sentMessage);
    
    public void listenerPortChosen(int port);
    
    public void messagesRetrieved(ArrayList<Message> retrievedMessages);
}
