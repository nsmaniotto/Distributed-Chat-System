
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Conversation.ConversationHandler;

public interface ConversationObservable {
    public void addObserver();

    public void deleteObserver();

    public void notifyObserversSent();
    public void notifyObserversRcv();
}
