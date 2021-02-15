package project.insa.idchatsystem.Observers.gui.Observers;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.gui.UserView;

public interface ViewObserver {
    abstract boolean newLogin(String login);
    abstract void newMessageSending(Message sendingMessage);
    abstract void userSelected(UserView userview);
    void closing();
}
