package project.insa.idchatsystem.servlet;

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
    public void sendGet(String message){
        new Thread(() -> {
            try {
                URL obj = new URL(URLServlet);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    con.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(con.getOutputStream());
                    out.writeBytes(URLEncoder.encode(String.format("message=%s&", message)));
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
