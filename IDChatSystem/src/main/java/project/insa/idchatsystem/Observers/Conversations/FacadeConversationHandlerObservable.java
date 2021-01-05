package project.insa.idchatsystem.Observers.Conversations;

public interface FacadeConversationHandlerObservable {
    public void notifyListenerPortNegociated();
    public void addObserver(FacadeConversationHandlerObserver observer);
    public void deleteObserver(FacadeConversationHandlerObserver observer);
}
