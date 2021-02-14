package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Observers.Server.Observables.ServerSendMessageObservable;
import project.insa.idchatsystem.Observers.Server.Observers.ServerSendMessageObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ServerSendMessage implements ServerSendMessageObservable {
    private final String URLServlet;
    private final String protocole;
    private ServerSendMessageObserver obs;
    public ServerSendMessage(String protocole){

        URLServlet = "https://srv-gei-tomcat.insa-toulouse.fr/NathanRobinServlet/";
        //URLServlet = "http://localhost:8080/NathanRobinServlet/";
        this.protocole = protocole;
    }
    public synchronized void sendPost(String message, User corresp){
        try {
            URL obj = new URL(URLServlet);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST"); // PUT is another valid option
            con.setDoOutput(true);
            con.setDoInput(true);

            Map<String,String> arguments = new HashMap<>();
            arguments.put("message", String.format("message=%s,%s,%s,%s&",
                    User.get_current_id(), corresp == null ? "" : corresp.get_id(),this.protocole,
                    message));
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            con.setFixedLengthStreamingMode(length);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.connect();
            try(OutputStream os = con.getOutputStream()) {
                os.write(out);
                os.flush();
            }
            if(message.equals("getMessages")) {
                if(message.contains("conv"))
                    System.out.printf(".(ServerSendMessage.java:57) - sendPost : dans equals\n");
                try {
                    BufferedReader in =
                            new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = " ";
                    while(line != null) {
                        line = in.readLine();
                        if(this.obs != null && line != null) {
                            this.obs.notifyNewMessage(line);
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.printf(".(ServerSendMessage.java:59) - %s sendPost : Erreur de réception\n",this.protocole);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addObserver(ServerSendMessageObserver obs) {
        this.obs = obs;
    }
}
