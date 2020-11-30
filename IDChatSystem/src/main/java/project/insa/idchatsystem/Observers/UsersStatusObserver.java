
package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.User;

public interface UsersStatusObserver {
    public void offlineUser(ObservableUserModel observable, User user) ;

    public void onlineUser(ObservableUserModel observable, User user) ;
}
