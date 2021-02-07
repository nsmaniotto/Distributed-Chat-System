
package project.insa.idchatsystem.Observers.Conversations.Observables;

import java.util.ArrayList;
import project.insa.idchatsystem.Conversations.Conversation.Conversation;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.Observers.ConversationObserver;
import project.insa.idchatsystem.User.distanciel.User;

public interface ConversationObservable {
    public void addConversationObserver(ConversationObserver observer);

    public void deleteConversationObserver(ConversationObserver observer);

    public void notifyObserversSentMessage(Message sentMessage);
    public void notifyObserversReceivedMessage(Message receivedMessage);
    
    public void notifyObserversRetrievedMessages(ArrayList<Message> retrievedMessages);
    
    public void notifyWrongCorrespondentConversation(User rightUser);
}
