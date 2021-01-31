package project.insa.idchatsystem.logins;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.logins.Observables.ObservableUserModel;
import project.insa.idchatsystem.Observers.logins.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUserModel implements ObservableUserModel {
    private HashMap<String, User> users;
    private final long limite_tolerance_ss_nouvelles = 10000;
    public AbstractUserModel(String id,boolean local) {
        User.init_current_user(id,local);
        this.users = new HashMap<>();
    }
    public boolean setUsername(String newUserName) {
        System.out.print("Changing username\n");
        try {
            User.set_current_username(newUserName);
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
        this.diffuseNewUsername();
        return true;
    }
    public void addOnlineUser(User user) {
//        System.out.printf("AbstractUserModel : ONLINE USER %s\n",user);
        boolean new_user = this.users.containsKey(user.get_id());
        this.users.put(user.get_id(),user);//Replace automatically the previous version if already in the HashMap
        this.notifyNewUserObservers(user);
    }
    public void removeOnlineUser(String id) {
//        System.out.printf("AbstractUserModel : DISCONNECTED USER %d\n",id);
        User removed_user = this.users.remove(id);
        if(removed_user == null) {
            System.out.printf("%s was not connected%n",id);
        }
        else {
            this.notifyDisconnectedObservers(removed_user);
        }
    }
    protected void checkUserStillActive() {
        long time = System.currentTimeMillis();
        ArrayList<String> toRemove = new ArrayList<>();
        this.users.forEach((k,v) -> {
            if(time-v.get_lastSeen().getTime() > limite_tolerance_ss_nouvelles) {
                toRemove.add(k);
            }
        });
        for (String index :toRemove) {
            this.removeOnlineUser(index);
        }
    }
    abstract public void disconnect();
    public HashMap<String,User> getOnlineUsers() {
        return this.users;
    }
    public boolean checkLocallyAvailable(String username) {
        for (Map.Entry<String, User> integerUserEntry : this.users.entrySet()) {
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
