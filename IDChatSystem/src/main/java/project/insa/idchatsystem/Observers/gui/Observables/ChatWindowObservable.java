
package project.insa.idchatsystem.Observers.gui.Observables;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.gui.Observers.ChatWindowObserver;

public interface ChatWindowObservable {
    public void addChatWindowObserver(ChatWindowObserver observer);

    public void deleteChatWindowObserver(ChatWindowObserver observer);

    public void notifyObserverSendingMessage(Message sendingMessage);
    public void notifyObserverClosing();
}
