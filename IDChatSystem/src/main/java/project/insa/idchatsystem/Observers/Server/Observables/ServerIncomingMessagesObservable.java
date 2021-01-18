package project.insa.idchatsystem.Observers.Server.Observables;

import project.insa.idchatsystem.Observers.Server.Observers.ServerIncomingMessagesObserver;

public interface ServerIncomingMessagesObservable {
    public void addOserver(ServerIncomingMessagesObserver obs);
    public void askForUpdate();
}
