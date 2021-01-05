
package project.insa.idchatsystem.Observers.Conversations.Observables;

import java.util.ArrayList;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.Observers.ConversationObserver;

public interface ConversationObservable {
    public void addConversationObserver(ConversationObserver observer);

    public void deleteConversationObserver(ConversationObserver observer);

    public void notifyObserversSentMessage(Message sentMessage);
    public void notifyObserversReceivedMessage(Message receivedMessage);
    
    public void notifyObserversRetrievedMessages(ArrayList<Message> retrievedMessages);
}
