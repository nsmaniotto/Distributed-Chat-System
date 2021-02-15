package project.insa.idchatsystem.Observers.Server.Observables;

import project.insa.idchatsystem.Observers.Server.Observers.ServerConvControllerObserver;
import project.insa.idchatsystem.Observers.Server.Observers.ServerLoginControllerObserver;

public interface ServerControllerObservable {
    public void addLoginListener(ServerLoginControllerObserver obs);
    public void addConvListener(ServerConvControllerObserver obs);
}
