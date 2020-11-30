package project.insa.idchatsystem.logins.local_mode.presentiel;

import project.insa.idchatsystem.logins.UserModelEmitter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class LocalUserModelEmitter extends UserModelEmitter {
    private MulticastSocket socket;
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
    private void sendBroadcast(String msg) {
        DatagramPacket outPacket = new DatagramPacket(msg.getBytes(), msg.length());
        try {
            this.socket.send(outPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void diffuse(){
        this.sendBroadcast(last_user_updated_string);
    }

    public void disconnect(int id) {
        String disconnected_str = String.format("%d,disconnected",id);
        this.sendBroadcast(disconnected_str);
    }
    @Override
    public void stopperEmission(){
        this.emission = false;
    }
}
