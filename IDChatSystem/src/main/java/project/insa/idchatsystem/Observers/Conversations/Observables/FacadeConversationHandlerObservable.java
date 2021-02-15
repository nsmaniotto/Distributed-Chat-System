package project.insa.idchatsystem.Observers.Conversations.Observables;

import project.insa.idchatsystem.Observers.Conversations.Observers.FacadeConversationHandlerObserver;

public interface FacadeConversationHandlerObservable {
    public void notifyListenerPortNegociated();
    public void addObserver(FacadeConversationHandlerObserver observer);
    public void deleteObserver(FacadeConversationHandlerObserver observer);
}
