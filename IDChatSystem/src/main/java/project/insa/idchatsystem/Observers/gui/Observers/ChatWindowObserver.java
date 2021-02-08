
package project.insa.idchatsystem.Observers.gui.Observers;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.UserView;

public interface ChatWindowObserver {
    public void newMessageSending(Message sendingMessage);
    public void userSelected(UserView userview);
    abstract boolean setUsername(String login);
    abstract void loginModify(String login);
}
