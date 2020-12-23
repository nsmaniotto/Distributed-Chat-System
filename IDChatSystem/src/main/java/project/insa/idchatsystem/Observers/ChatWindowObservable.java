
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

public interface ChatWindowObservable {
    public void addChatWindowObserver(ChatWindowObserver observer);

    public void deleteChatWindowObserver(ChatWindowObserver observer);

    public void notifyObserverSendingMessage(Message sendingMessage);
    public void notifyAskForMessages(User user);
}
