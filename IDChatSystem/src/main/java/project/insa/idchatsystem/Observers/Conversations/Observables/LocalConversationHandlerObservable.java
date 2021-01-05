package project.insa.idchatsystem.Observers.Conversations.Observables;

import project.insa.idchatsystem.Observers.Conversations.Observers.LocalConversationHandlerObserver;

public interface LocalConversationHandlerObservable {
    public void notifyListenerPortNegociated();
    public void addObserver(LocalConversationHandlerObserver obs);
    public void deleteObserver(LocalConversationHandlerObserver obs);
}
