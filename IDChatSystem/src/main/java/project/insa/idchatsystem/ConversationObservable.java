
package project.insa.idchatsystem;

interface ConversationObservable {
    public void addObserver(Object conversationObserver);

    public void deleteObserver(Object conversationObserver);

    public void notifyObserversNewMessageSent(Message sentMessage);
    
    public void notifyObserversNewMessageReceived(Message receivedMessage);
}
