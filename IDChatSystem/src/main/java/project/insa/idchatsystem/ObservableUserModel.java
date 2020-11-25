
package project.insa.idchatsystem;

interface ObservableUserModel {
    
    public void addObserver(UsersStatusObserver obs);

    public void deleteObserver(UsersStatusObserver obs);

    public void notifyNewUserObservers(User user);
    public void notifyDisconnectedObservers(User user);
}
