
package project.insa.idchatsystem;

interface ConversationObservable {
    public void addObserver(ConversationHandlerObservable conversationObserver);

    public void deleteObserver(ConversationHandlerObservable conversationObserver);

    public void notifyObserversNewMessageSent(Message sentMessage);
    
    public void notifyObserversNewMessageReceived(Message receivedMessage);
}
