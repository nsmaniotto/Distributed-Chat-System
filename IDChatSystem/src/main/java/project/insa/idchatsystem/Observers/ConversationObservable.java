
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ConversationObservable {
    public void addConversationObserver(ConversationHandlerObserver observer);

    public void deleteConversationObserver(ConversationHandlerObserver observer);

    public void notifyObserversSentMessage(Message sentMessage);
    public void notifyObserversReceivedMessage(Message receivedMessage);
}
