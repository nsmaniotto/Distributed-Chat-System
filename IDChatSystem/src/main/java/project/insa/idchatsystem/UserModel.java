package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UserModel implements ObservableUserModel{
    private HashMap<Integer,User> users;
    private ArrayList<UsersStatusObserver> connected_user_observers;
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
    public void removeOnlineUser(User user) {
        if(this.users.remove(user.get_id()) == null) {
            System.out.printf("%s is not connected%n",user.toString());
        }
        else {
            this.notifyDisconnectedObservers(user);
        }
    }
    public HashMap<Integer,User> getOnlineUsers() {
        return this.users;
    }
    public boolean checkAvailable(String username) {
        for (Map.Entry<Integer, User> integerUserEntry : this.users.entrySet()) {
            User user = (User) ((Map.Entry) integerUserEntry).getValue();
            if (user.get_username().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public void addObserver(UsersStatusObserver obs) {
        this.connected_user_observers.add(obs);
    }

    public void deleteObserver(UsersStatusObserver obs) {
        this.connected_user_observers.remove(obs);
    }

    public void notifyNewUserObservers(User user) {
        for (UsersStatusObserver obs :
                this.connected_user_observers) {
            obs.onlineUser(this,user);
        }
    }
    public void notifyDisconnectedObservers(User user) {
        for (UsersStatusObserver obs :
                this.connected_user_observers) {
            obs.offlineUser(this,user);
        }
    }

    abstract void diffuseNewUsername();
}
