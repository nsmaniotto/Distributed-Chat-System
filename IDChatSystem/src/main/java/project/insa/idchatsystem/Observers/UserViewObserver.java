package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.UserView;

public interface UserViewObserver {
    void userSelected(UserView userview);
    void askForMessages(User user);
}
