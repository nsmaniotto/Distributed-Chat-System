package project.insa.idchatsystem.logins;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.ObservableUserModel;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UserModel implements ObservableUserModel {
    private HashMap<Integer, User> users;
    private long limite_tolerance_ss_nouvelles = 10000;
    public UserModel(int id) {
        User.init_current_user(id);
        this.users = new HashMap<Integer,User>();
    }
    public boolean setUsername(String newUserName) {
        System.out.printf("Changing username\n");
        try {
            User.set_current_username(newUserName);
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
        this.diffuseNewUsername();
        return true;
    }
    public void addOnlineUser(User user) {
        System.out.printf("Online user 2 %s\n",user);
        boolean new_user = this.users.containsKey(user.get_id());
        this.users.put(user.get_id(),user);//Replace automatically the previous version if already in the HashMap
        this.notifyNewUserObservers(user);
    }
    public void removeOnlineUser(int id) {
        User removed_user = this.users.remove(id);
        if(removed_user == null) {
            System.out.printf("%d was not connected%n",id);
        }
        else {
            this.notifyDisconnectedObservers(removed_user);
        }
    }
    protected void checkUserStillActive() {
        long time = System.currentTimeMillis();
        ArrayList<Integer> toRemove = new ArrayList<>();
        this.users.forEach((k,v) -> {
            if(time-v.get_lastSeen().getTime() > limite_tolerance_ss_nouvelles) {
                toRemove.add(k);
            }
        });
        for (int index :toRemove) {
            this.removeOnlineUser(index);
        }
    }
    abstract public void disconnect();
    public HashMap<Integer,User> getOnlineUsers() {
        return this.users;
    }
    public boolean checkLocallyAvailable(String username) {
        for (Map.Entry<Integer, User> integerUserEntry : this.users.entrySet()) {
            User user = (User) ((Map.Entry) integerUserEntry).getValue();
            if (user.get_username().equals(username)) {
                return false;
            }
        }
        return true;
    }
    abstract public boolean checkavailable(String username);
    public abstract void addUserModelObserver(UsersStatusObserver obs);

    public abstract void deleteUserModelObserver(UsersStatusObserver obs);

    public abstract void notifyNewUserObservers(User user);
    public abstract void notifyDisconnectedObservers(User user);

    protected abstract void diffuseNewUsername();
    public abstract void stopperEmission();
}
