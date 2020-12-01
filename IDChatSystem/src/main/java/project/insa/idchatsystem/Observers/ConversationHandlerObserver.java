
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ConversationHandlerObserver {
    public void newMessageRcv(Message message);
    public void newMessageSent(Message message);
}
