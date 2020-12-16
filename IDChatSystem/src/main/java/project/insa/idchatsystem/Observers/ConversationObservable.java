
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ConversationObservable {
    public void addConversationObserver(ConversationObserver observer);

    public void deleteConversationObserver(ConversationObserver observer);

    public void notifyObserversSentMessage(Message sentMessage);
    public void notifyObserversReceivedMessage(Message receivedMessage);
}
