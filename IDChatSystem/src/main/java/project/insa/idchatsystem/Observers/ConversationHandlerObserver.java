
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ConversationHandlerObserver {
    public void newMessageReceived(Message receivedMessage);
    public void newMessageSent(Message sentMessage);
}
