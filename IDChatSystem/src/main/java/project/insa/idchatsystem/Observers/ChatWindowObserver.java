
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ChatWindowObserver {
    public void newMessageSending(Message sendingMessage);
}
