package project.insa.idchatsystem.Observers.Conversations;

public interface LocalConversationHandlerObservable {
    public void notifyListenerPortNegociated();
    public void addObserver(LocalConversationHandlerObserver obs);
    public void deleteObserver(LocalConversationHandlerObserver obs);
}
