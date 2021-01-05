package project.insa.idchatsystem.Observers.Conversations;

import project.insa.idchatsystem.Message;

import java.util.ArrayList;

public interface ConversationHandlerObservable {
    public void notifyObserversRetrievedMessages(ArrayList<Message> retrievedMessages);

}
