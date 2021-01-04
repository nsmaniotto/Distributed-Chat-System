package project.insa.idchatsystem.Observers.Conversations;

import project.insa.idchatsystem.Message;

public interface FacadeConversationHandlerObserver {
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation);
    public void newMessageSent(Message sentMessage);
    public void listenerPortChosen(int port);
}
