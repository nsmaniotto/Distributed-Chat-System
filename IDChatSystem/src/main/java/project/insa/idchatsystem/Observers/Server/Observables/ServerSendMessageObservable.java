package project.insa.idchatsystem.Observers.Server.Observables;

import project.insa.idchatsystem.Observers.Server.Observers.ServerSendMessageObserver;

public interface ServerSendMessageObservable {
    public void addObserver(ServerSendMessageObserver obs);
}
