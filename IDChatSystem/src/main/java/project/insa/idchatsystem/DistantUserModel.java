package project.insa.idchatsystem;

import java.util.HashMap;

public class DistantUserModel  implements ObservableUserModel,UserModel {
    @Override
    public void addObserver(UsersStatusObserver obs) {

    }

    @Override
    public void deleteObserver(UsersStatusObserver obs) {

    }

    @Override
    public void notifyNewUserObservers(User user) {

    }

    @Override
    public void notifyDisconnectedObservers(User user) {

    }

    @Override
    public void setUsername(String newUserName) {

    }

    @Override
    public void addOnlineUser(User user) {

    }

    @Override
    public void removeOnlineUser(User user) {

    }

    @Override
    public HashMap<Integer, User> getOnlineUsers() {
        return null;
    }

    @Override
    public void diffuseNewUsername() {

    }

    @Override
    public boolean checkAvailable(String username) {
        return false;
    }
}
