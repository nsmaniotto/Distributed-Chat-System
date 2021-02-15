package project.insa.idchatsystem.Observers.Conversations.Observers;

import java.util.ArrayList;
import project.insa.idchatsystem.Conversations.Conversation.Conversation;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

public interface ConversationObserver {
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation);
    public void newMessageSent(Message sentMessage);
    
    public void messagesRetrieved(ArrayList<Message> retrievedMessages);
    
    public void wrongCorrespondentConversation(Conversation conversation, User rightUser);
}
