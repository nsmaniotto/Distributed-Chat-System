
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.gui.UserView;

public interface ChatWindowObserver {
    public void newMessageSending(Message sendingMessage);
    public void userSelected(UserView userview);
}
