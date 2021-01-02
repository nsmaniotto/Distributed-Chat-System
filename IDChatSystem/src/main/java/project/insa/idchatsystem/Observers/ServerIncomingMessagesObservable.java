package project.insa.idchatsystem.Observers;

public interface ServerIncomingMessagesObservable {
    public void addOserver(ServerIncomingMessagesObserver obs);
    public void notifyNewMessage(String message);
}
