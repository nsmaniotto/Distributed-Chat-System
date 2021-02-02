package project.insa.idchatsystem.logins.local_mode.distanciel.Local;

import project.insa.idchatsystem.database.LoginsBroadcastDatabase;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class LocalUserModelEmitter implements Runnable {
    private ArrayList<Integer> liste_ports_others;
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
        String full_msg = String.format("login,%s",msg);
        System.out.printf(".(LocalUserModelEmitter.java:20) - sendBroadcast : %s\n",msg);
        for(int port:this.liste_ports_others) {
            DatagramPacket outPacket = null;
            try {
                outPacket = new DatagramPacket(full_msg.getBytes(),
                        full_msg.length(),
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

    @Override
    public void run() {
        LoginsBroadcastDatabase database = new LoginsBroadcastDatabase(false);
        while (true) {
            try {
                Thread.sleep(200);
                ArrayList<Integer> ports = database.getPortReceivers();
                ArrayList<Integer> merged = new ArrayList<>();
                merged.addAll(ports);
                merged.addAll(this.liste_ports_others);
                this.liste_ports_others = merged;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
