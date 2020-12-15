package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ViewObserver {
    abstract boolean newLogin(String login);
    abstract void newMessageSending(Message sendingMessage);
}
