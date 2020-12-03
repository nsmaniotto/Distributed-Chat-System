package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.logins.UserModelEmitter;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class LocalUserModelEmitter extends UserModelEmitter {
    private final ArrayList<Integer> liste_ports_others;
    private DatagramSocket socket;
    public LocalUserModelEmitter(int emitter_port,ArrayList<Integer> others){
        this.liste_ports_others = others;
        try {
            this.socket = new DatagramSocket(emitter_port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.last_user_updated_string = "";
    }
    private void sendBroadcast(String msg) {
        System.out.printf("SENT %s\n",msg);
        for(int port:this.liste_ports_others) {
            DatagramPacket outPacket = new DatagramPacket(msg.getBytes(),
                    msg.length(),
                    socket.getLocalAddress(),port);
            try {
                this.socket.send(outPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void askUpdate() {
        this.sendBroadcast("update");
    }
    public String getState(){
        return this.state;
    }
    public void disconnect(int id) {
        this.stopperEmission();
        String disconnected_str = String.format("%d,disconnected",id);
        this.sendBroadcast(disconnected_str);
        this.state = "disconnected";
    }
    public void diffuse(){
        this.sendBroadcast(last_user_updated_string);
    }
}
