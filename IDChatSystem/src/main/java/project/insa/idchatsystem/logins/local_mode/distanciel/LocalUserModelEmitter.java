package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.logins.UserModelEmitter;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class LocalUserModelEmitter {
    private final ArrayList<Integer> liste_ports_others;
    private DatagramSocket socket;
    public LocalUserModelEmitter(int emitter_port,ArrayList<Integer> others){
        this.liste_ports_others = others;
        try {
            this.socket = new DatagramSocket(emitter_port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public void sendBroadcast(String msg) {
        for(int port:this.liste_ports_others) {
            DatagramPacket outPacket = null;
            try {
                outPacket = new DatagramPacket(msg.getBytes(),
                        msg.length(),
                        InetAddress.getByName("127.0.0.1"),port);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.out.print("Cannot find host\n");
            }
            try {
                this.socket.send(outPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
