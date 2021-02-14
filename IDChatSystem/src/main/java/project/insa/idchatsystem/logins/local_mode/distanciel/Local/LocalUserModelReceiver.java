package project.insa.idchatsystem.logins.local_mode.distanciel.Local;

import project.insa.idchatsystem.Observers.logins.Observers.UserModelReceiverObserver;

import java.io.IOException;
import java.net.*;

public class LocalUserModelReceiver implements Runnable {
    private UserModelReceiverObserver model;
    private InetAddress group;
    private DatagramSocket socket;
    //Format of UDP packets : username,id,ipAddress with :
    // - username : max 25 letters
    // - id : between 36 characters
    // - ipAdress : maximum 3*4 + 3 = 15 characters
    // - lastSeen : 23-24 characters
    // - conversationHandlerListenerPort : 65535 = 5 characters
    // Result = 25 + 36 + 15 + 24 + 5 = 76 character -> we will take 256 characters
    protected byte[] in_buf = new byte[256];

    public LocalUserModelReceiver(UserModelReceiverObserver model, int in_port_broadcast) {
        this.model = model;
        try {
            socket = new DatagramSocket(in_port_broadcast);
            socket.setSoTimeout(0);//infinite timeout to block on receive
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Broadcast socket in error");
        }
    }

    /**
     * Waits for message and notify the model when a message arrives
     */
    @Override
    public void run() {
        //Création des objets pour recevoir de l'UDP en broadcast (multicast ici pour éviter de trop utiliser de ressources) des communications
        try {

            while (true) {
                DatagramPacket packet = new DatagramPacket(in_buf, in_buf.length);
                socket.receive(packet);//blocked until message arrived : see comment above
                String received = new String(packet.getData(), 0, packet.getLength());
                if(!received.equals("")) {
                    this.model.notifyNewMsg(received);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot join multicast group");
        }
    }
}
