package project.insa.idchatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class LocalUserModelEmitter implements Runnable{
    private InetAddress group;
    private MulticastSocket socket;
    private String last_user_updated_string;
    public LocalUserModelEmitter(){
        this.last_user_updated_string = "";
        try {
            this.group = InetAddress.getByName("230.0.0.0");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //init in -> broadcast socket
        try {
            int out_port_broadcast = 4001;
            socket = new MulticastSocket(out_port_broadcast);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Mutlicast socket out error");
        }
    }
    public void diffuseNewUsername(String updatedUserString) {
        this.last_user_updated_string = updatedUserString;
        this.diffuse();
    }
    private void diffuse(){
        DatagramPacket outPacket = new DatagramPacket(last_user_updated_string.getBytes(), last_user_updated_string.length());
        try {
            this.socket.send(outPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(true) {
            this.diffuse();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
