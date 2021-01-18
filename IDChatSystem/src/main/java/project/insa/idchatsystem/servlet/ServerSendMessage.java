package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.Observers.Server.Observables.ServerSendMessageObservable;
import project.insa.idchatsystem.Observers.Server.Observers.ServerSendMessageObserver;
import project.insa.idchatsystem.User.distanciel.User;

import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServerSendMessage implements ServerSendMessageObservable {
    private final String URLServlet;
    private final String protocole;
    private ServerSendMessageObserver obs;
    public ServerSendMessage(String protocole){

        URLServlet = "http://localhost:8080/NathanRobinServlet/";
        this.protocole = protocole;
    }
    public void sendPost(String message, User corresp){

            try {
                URL obj = new URL(URLServlet);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST"); // PUT is another valid option
                con.setDoOutput(true);
                con.setDoInput(true);

                Map<String,String> arguments = new HashMap<>();
                arguments.put("message", String.format("message=%d,%d,%s,%s&",
                        User.get_current_id(), corresp == null ? -1 : corresp.get_id(),this.protocole,
                        message));
                StringJoiner sj = new StringJoiner("&");
                for(Map.Entry<String,String> entry : arguments.entrySet())
                    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                            + URLEncoder.encode(entry.getValue(), "UTF-8"));
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
                    try {
                        BufferedReader in =
                                new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line = " ";
                        while(line != null) {
                            line = in.readLine();
//                            if(line != null)
//                                System.out.printf(".(ServerSendMessage.java:63) - %s sendPost : Read : %s\n",this.protocole,line == null ? "null" : line);
                            if(this.obs != null && line != null)
                                this.obs.notifyNewMessage(line);
//                            else if(this.obs == null)
//                                System.out.printf(".(ServerSendMessage.java:68) - %s sendPost : Missed message\n",this.protocole);
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
