package project.insa.idchatsystem.Observers.gui;

import project.insa.idchatsystem.gui.UserView;

public interface UserViewObserver {
    void startCommunicationWith(UserView userview);
    void askForMessages(UserView userview);
}