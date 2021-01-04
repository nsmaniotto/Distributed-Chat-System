package project.insa.idchatsystem.servlet;

import project.insa.idchatsystem.User.distanciel.User;

import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServerSendMessage {
    private final String URLServlet;
    public ServerSendMessage(){
        URLServlet = "";
    }
    public void sendGet(String message,User corresp){
        new Thread(() -> {
            try {
                URL obj = new URL(URLServlet);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    con.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(con.getOutputStream());
                    out.writeBytes(URLEncoder.encode(String.format("message=%d,%d,%s&",
                            User.get_current_id(), corresp == null ? -1 : corresp.get_id(),
                            message)));
                    out.flush();
                    out.close();
                } else {
                    System.out.println("GET request not worked");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
