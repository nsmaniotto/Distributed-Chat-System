
package project.insa.idchatsystem;

import javax.swing.JList;

class UserModel implements ObservableUserModel {
    private String networkMode;
    private ClientController clientController;
    private JList<User> users;

    public UserModel(String networkMode, int id) {
        
    }

    public void setUsername(String newUserName) {
        
    }

    public void addOnlineUser(User user) {
        
    }

    public void removeOnlineUser(User user) {
        
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
