package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Observers.ServerIncomingMessagesObservable;
import project.insa.idchatsystem.Observers.ServerIncomingMessagesObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerIncomingMessages implements Runnable, ServerIncomingMessagesObservable {
    private final String URLServlet;
    private ServerIncomingMessagesObserver observer;
    public ServerIncomingMessages(){
        URLServlet = "";
    }
    public void run() {
        URL obj;
        try {
            obj = new URL(URLServlet);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            while (true) {
                String input = in.readLine();
                if(input != null)
                    this.notifyNewMessage(input);
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notifyNewMessage(String message) {
        observer.notifyNewMessage(message);
    }

    @Override
    public void addOserver(ServerIncomingMessagesObserver obs) {
        this.observer = obs;
    }
}
