package project.insa.idchatsystem.logins;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.ObservableUserModel;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UserModel implements ObservableUserModel {
    private HashMap<Integer, User> users;
    private ArrayList<UsersStatusObserver> connected_user_observers;
    private long limite_tolerance_ss_nouvelles = 10000;
    public UserModel(int id) {
        User.init_current_user(id);
        this.users = new HashMap<Integer,User>();
        this.connected_user_observers = new ArrayList<UsersStatusObserver>();
    }
    public void setUsername(String newUserName) {
        try {
            User.set_current_username(newUserName);
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
        this.diffuseNewUsername();
    }
    public void addOnlineUser(User user) {
        boolean new_user = this.users.containsKey(user.get_id());
        this.users.put(user.get_id(),user);//Replace automatically the previous version if already in the HashMap
        if(new_user) {
            //notify observers
            this.notifyNewUserObservers(user);
        }
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
    public void addObserver(UsersStatusObserver obs) {
        this.connected_user_observers.add(obs);
    }

    public void deleteObserver(UsersStatusObserver obs) {
        this.connected_user_observers.remove(obs);
    }

    public void notifyNewUserObservers(User user) {
        for (UsersStatusObserver obs :
                this.connected_user_observers) {
            obs.onlineUser(user);
        }
    }
    public void notifyDisconnectedObservers(User user) {
        for (UsersStatusObserver obs :
                this.connected_user_observers) {
            obs.offlineUser(user);
        }
    }

    protected abstract void diffuseNewUsername();
    public abstract void stopperEmission();
}
