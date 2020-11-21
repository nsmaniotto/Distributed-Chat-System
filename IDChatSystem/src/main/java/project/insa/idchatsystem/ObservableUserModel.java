
package project.insa.idchatsystem;

interface ObservableUserModel {
    //public UsersStatusObserver usersStatusObserver; // To be declared later
    
    public void addObserver();

    public void deleteObserver();

    public void notifyObservers();
}
