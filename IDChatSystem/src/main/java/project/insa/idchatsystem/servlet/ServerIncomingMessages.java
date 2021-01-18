package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Observers.Server.Observables.ServerIncomingMessagesObservable;
import project.insa.idchatsystem.Observers.Server.Observers.ServerIncomingMessagesObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
