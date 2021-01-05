package project.insa.idchatsystem.Observers.Server.Observers;

public interface ServerIncomingMessagesObserver {
    public void notifyNewMessage(String message);
}
