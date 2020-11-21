
package project.insa.idchatsystem;

interface UsersStatusObserver {
    public void offlineUser(ObservableUserModel observable, User user) ;

    public void onlineUser(ObservableUserModel observable, User user) ;
}
