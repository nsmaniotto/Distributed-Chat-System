package project.insa.idchatsystem.Observers.Conversations;

import project.insa.idchatsystem.Message;

import java.util.ArrayList;

public interface FacadeConversationHandlerObserver {
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation);
    public void newMessageSent(Message sentMessage);
    public void listenerPortChosen(int port);
    public void messagesRetrieved(ArrayList<Message> retrievedMessages);
}
