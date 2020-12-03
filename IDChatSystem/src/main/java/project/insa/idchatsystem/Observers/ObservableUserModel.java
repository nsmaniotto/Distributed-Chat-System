
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.User.distanciel.User;

public interface ObservableUserModel {
    
    public void addUserModelObserver(UsersStatusObserver obs);

    public void deleteUserModelObserver(UsersStatusObserver obs);

    public void notifyNewUserObservers(User user);
    public void notifyDisconnectedObservers(User user);
}
