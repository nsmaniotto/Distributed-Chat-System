package project.insa.idchatsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistantUserModelReceiver  implements Runnable {
    private final DistantUserModel model;
    private final ServerSocket socket;
    private Socket server_socket;
    BufferedReader in_buffer;
    private final int in_port = 4002;

    public DistantUserModelReceiver(DistantUserModel model) throws IOException {
        this.model = model;
        //Init server -> in socket
        socket = new ServerSocket(in_port);
        in_buffer = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));
    }

    @Override
    public void run() {
        while(true) {
            try {
                server_socket = socket.accept();
                String message = in_buffer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }/*
        try {
            socket.joinGroup(group);
            Pattern pattern_new_host = Pattern.compile("(?<username>[A-Za-z_.]+),(?<id>[0-9]+),(?<wip>[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+)");
            while (true) {
                DatagramPacket packet = new DatagramPacket(in_buf, in_buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //Extraction of the informations of the packet thanks to a regex and named group
                Matcher m = pattern_new_host.matcher(received);
                while (m.find()){
                    User new_user = new User(m.group("username"),Integer.parseInt(m.group("id")),m.group("ip"));
                    this.model.addOnlineUser(new_user);//Add or refresh informations of the user based on the id
                }
            }
            //socket.leaveGroup(group);
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot join multicast group");
        }*/
    }
}
