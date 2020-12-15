
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.User.distanciel.User;

import java.util.HashMap;

public interface ObservableUserModel {
    
    void addUserModelObserver(UsersStatusObserver obs);

    void deleteUserModelObserver(UsersStatusObserver obs);

    void notifyNewUserObservers(User user);
    void notifyDisconnectedObservers(User user);
}
