package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.User.distanciel.User;

public interface UserViewObservable {
    public void userSelected();
    public void notifyAskForMessagesUserSelected();
}
