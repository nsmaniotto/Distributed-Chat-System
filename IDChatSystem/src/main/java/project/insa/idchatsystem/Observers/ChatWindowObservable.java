
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ChatWindowObservable {
    public void addViewObserver(ChatWindowObserver observer);

    public void deleteViewObserver(ChatWindowObserver observer);

    public void notifyObserverSendingMessage(Message sendingMessage);
}
