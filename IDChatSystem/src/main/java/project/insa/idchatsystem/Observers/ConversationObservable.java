
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Conversation.ConversationHandler;

public interface ConversationObservable {
    public void addObserver(ConversationHandlerObserver obs);

    public void deleteObserver(ConversationHandlerObserver obs);

    public void notifyObserversSent();
    public void notifyObserversRcv();
}
