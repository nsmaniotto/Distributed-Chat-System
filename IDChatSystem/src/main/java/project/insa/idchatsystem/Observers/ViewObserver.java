package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.gui.UserView;

public interface ViewObserver {
    abstract boolean newLogin(String login);
    abstract void newMessageSending(Message sendingMessage);
    void userSelected(UserView userview);
}
