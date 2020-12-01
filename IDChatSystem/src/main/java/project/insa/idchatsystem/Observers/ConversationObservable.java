
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Message;

public interface ConversationObservable {
    public void addConversationObserver(ConversationHandlerObserver obs);

    public void deleteConversationObserver(ConversationHandlerObserver obs);

    public void notifyObserversSent(Message message);
    public void notifyObserversRcv(Message message);
}
