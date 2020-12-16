package project.insa.idchatsystem.Observers;

public interface ConversationHandlerObservable {
    public void notifyListenerPortNegociated();
    public void addObserver(ConversationHandlerObserver obs);
    public void deleteObserver(ConversationHandlerObserver obs);
}
