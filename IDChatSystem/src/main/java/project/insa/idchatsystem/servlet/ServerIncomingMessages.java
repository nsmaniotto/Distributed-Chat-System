package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Observers.Server.Observables.ServerIncomingMessagesObservable;
import project.insa.idchatsystem.Observers.Server.Observers.ServerIncomingMessagesObserver;

public class ServerIncomingMessages implements Runnable, ServerIncomingMessagesObservable {
    private ServerIncomingMessagesObserver observer;
    public ServerIncomingMessages(){
    }
    public void run() {
        while(true) {
            this.askForUpdate();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void askForUpdate() {
        observer.askForUpdate();
    }

    @Override
    public void addOserver(ServerIncomingMessagesObserver obs) {
        this.observer = obs;
    }
}
