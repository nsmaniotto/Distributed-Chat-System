
package project.insa.idchatsystem.Observers.logins.Observers;

import project.insa.idchatsystem.User.distanciel.User;

import java.util.HashMap;

public interface UsersStatusObserver {
    public void offlineUser(User user) ;

    public void onlineUser(User user) ;
}
