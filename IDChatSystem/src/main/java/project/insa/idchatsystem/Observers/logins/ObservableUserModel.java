
package project.insa.idchatsystem.Observers.logins;

import project.insa.idchatsystem.User.distanciel.User;

public interface ObservableUserModel {
    
    void addUserModelObserver(UsersStatusObserver obs);

    void deleteUserModelObserver(UsersStatusObserver obs);

    void notifyNewUserObservers(User user);
    void notifyDisconnectedObservers(User user);
}
