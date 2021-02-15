package project.insa.idchatsystem.Observers.Conversations.Observables;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.Conversations.Observers.ConversationHandlerObserver;

import java.util.ArrayList;

public interface ConversationHandlerObservable {
    public void addObserver(ConversationHandlerObserver observer);
    public void deleteObserver(ConversationHandlerObserver obs);
    public void notifyObserversRetrievedMessages(ArrayList<Message> retrievedMessages);

}
