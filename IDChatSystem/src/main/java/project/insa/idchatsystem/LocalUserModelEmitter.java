package project.insa.idchatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class LocalUserModelEmitter extends UserModelEmitter{
    private MulticastSocket socket;
    private String last_user_updated_string;
    public LocalUserModelEmitter(){
        this.last_user_updated_string = "";
        try {
            InetAddress group = InetAddress.getByName("230.0.0.0");
            //init in -> broadcast socket
            try {
                int out_port_broadcast = 4001;
                MulticastSocket tmp_socket = new MulticastSocket(out_port_broadcast);
                tmp_socket.joinGroup(group);
                this.socket = tmp_socket;

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Mutlicast socket out error");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public void diffuse(){
        DatagramPacket outPacket = new DatagramPacket(last_user_updated_string.getBytes(), last_user_updated_string.length());
        try {
            this.socket.send(outPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
