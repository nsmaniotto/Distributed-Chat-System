
package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import javax.swing.JList;
import java.net.UnknownHostException;
import java.util.ArrayList;

class UserModel implements ObservableUserModel {
    private String networkMode;
    private ArrayList<User> users;

    public UserModel(String networkMode, int id) throws UnknownHostException {//TODO : revoir si exception à gérer plus haut ou pas
        this.networkMode = networkMode;
        this.users = new ArrayList<User>();
        User.init_current_user(id);
    }

    public void setUsername(String newUserName) {
        try {
            User.set_current_username(newUserName);
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }

    public void addOnlineUser(User user) {
        this.users.add(user);
    }

    public void removeOnlineUser(User user) {
        int index_user = users.indexOf(user);
        if(index_user==-1) {
            System.out.printf("%s is not connected%n",user.toString());
        }
        else {
            this.users.remove(index_user);
        }
    }

    public User getOnlineUsers() {
        return null;
    }

    public void difuseNewUsername(String username, int id) {
        
    }

    public boolean checkAvailable(String username, int id) {
        return false;   
    }

    @Override
    public void addObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObservers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
