package project.insa.idchatsystem.Observers.Conversations.Observers;

import java.util.ArrayList;
import project.insa.idchatsystem.Message;

public interface ConversationObserver {
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation);
    public void newMessageSent(Message sentMessage);
    
    public void messagesRetrieved(ArrayList<Message> retrievedMessages);
}
