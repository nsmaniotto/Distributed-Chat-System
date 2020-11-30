package project.insa.idchatsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistantUserModelReceiver  implements Runnable {
    private final DistantUserModel model;
    private ServerSocket socket;
    private Socket server_socket;//scoket receiving informations from the server
    BufferedReader in_buffer;
    private final int in_port = 4002;

    public DistantUserModelReceiver(DistantUserModel model) {
        this.model = model;
        //Init server -> in socket
        try {
            socket = new ServerSocket(in_port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Cannot create server socket on port %d%n",in_port);
        }
        try {
            in_buffer = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot create TCP input buffer");
        }
    }

    @Override
    public void run() {
        assert socket !=  null && in_buffer != null;
        Pattern pattern_new_host = Pattern.compile("(?<username>[A-Za-z_.]+),(?<id>[0-9]+),(?<wip>[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+)");
        while(true) {
            try {
                server_socket = socket.accept();
                String received = in_buffer.readLine();
                //Extraction of the informations of the packet thanks to a regex and named group
                Matcher m = pattern_new_host.matcher(received);
                while (m.find()){
                    User new_user = new User(m.group("username"),Integer.parseInt(m.group("id")),m.group("ip"));
                    this.model.addOnlineUser(new_user);//Add or refresh informations of the user based on the id
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
