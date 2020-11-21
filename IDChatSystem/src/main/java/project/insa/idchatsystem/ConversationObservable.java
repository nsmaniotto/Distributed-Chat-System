
package project.insa.idchatsystem;

interface ConversationObservable {
    public void addObserver();

    public void deleteObserver();

    public void notifyObservers();
}
