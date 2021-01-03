
package project.insa.idchatsystem.Observers.gui;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.UserView;

public interface ChatWindowObserver {
    public void newMessageSending(Message sendingMessage);
    public void userSelected(UserView userview);
    public void askForMessages(User user);
}
