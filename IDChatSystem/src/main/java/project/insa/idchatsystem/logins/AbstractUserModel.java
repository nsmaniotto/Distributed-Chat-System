package project.insa.idchatsystem.logins;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.logins.Observables.ObservableUserModel;
import project.insa.idchatsystem.Observers.logins.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUserModel implements ObservableUserModel {
    abstract public boolean checkavailable(String username);
    public abstract void addUserModelObserver(UsersStatusObserver obs);

    public abstract void deleteUserModelObserver(UsersStatusObserver obs);

    public abstract void notifyNewUserObservers(User user);
    public abstract void notifyDisconnectedObservers(User user);

    protected abstract void diffuseNewUsername();
    public abstract void stopperEmission();
}
