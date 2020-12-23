package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.User.distanciel.User;

public interface ViewObservable {
    public void notifyAskForMessage(User user);
}
