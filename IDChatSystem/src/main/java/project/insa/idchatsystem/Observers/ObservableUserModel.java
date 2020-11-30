
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.User;

public interface ObservableUserModel {
    
    public void addObserver(UsersStatusObserver obs);

    public void deleteObserver(UsersStatusObserver obs);

    public void notifyNewUserObservers(User user);
    public void notifyDisconnectedObservers(User user);
}
