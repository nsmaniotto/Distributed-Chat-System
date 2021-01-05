package project.insa.idchatsystem.Observers.Server;

public interface ServerIncomingMessagesObservable {
    public void addOserver(ServerIncomingMessagesObserver obs);
    public void notifyNewMessage(String message);
}
