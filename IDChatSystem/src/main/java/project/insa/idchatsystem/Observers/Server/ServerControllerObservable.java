package project.insa.idchatsystem.Observers.Server;

public interface ServerControllerObservable {
    public void addLoginListener(ServerLoginControllerObserver obs);
    public void addConvListener(ServerConvControllerObserver obs);
}
