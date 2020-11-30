
package project.insa.idchatsystem.Observers;

public interface ConversationObservable {
    public void addObserver();

    public void deleteObserver();

    public void notifyObservers();
}
