
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ConversationObservable {
    public void addObserver(ConversationHandlerObserver observer);

    public void deleteObserver(ConversationHandlerObserver observer);

    public void notifyObserversSentMessage(Message sentMessage);
    public void notifyObserversReceivedMessage(Message receivedMessage);
}
