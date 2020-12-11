
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ChatWindowObservable {
    public void addChatWindowObserver(ChatWindowObserver observer);

    public void deleteChatWindowObserver(ChatWindowObserver observer);

    public void notifyObserverSendingMessage(Message sendingMessage);
}
