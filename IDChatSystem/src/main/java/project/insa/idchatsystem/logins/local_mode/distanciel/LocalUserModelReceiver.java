package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.Observers.UserModelReceiverObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.io.IOException;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalUserModelReceiver implements Runnable {
    private UserModelReceiverObserver model;
    private InetAddress group;
    private DatagramSocket socket;
    //Format of UDP packets : username,id,ipAddress with :
    // - username : max 25 letters
    // - id : between 0 and 1 000 000 > 100 000 -> between 1 and 7 digits
    // - ipAdress : maximum 3*4 + 3 = 15 characters
    // - lastSeen : 23-24 characters
    // - conversationHandlerListenerPort : 65535 = 5 characters
    // Result = 25 + 7 + 15 + 24 + 5 = 76 character -> we will take 256 characters
    protected byte[] in_buf = new byte[256];

    public LocalUserModelReceiver(UserModelReceiverObserver model, int in_port_broadcast) {
        this.model = model;
        try {
            socket = new DatagramSocket(in_port_broadcast);
            socket.setSoTimeout(0);//infinite timeout to block on receive
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Mutlicast socket in error");
        }
    }
    @Override
    public void run() {
        //Création des objets pour recevoir de l'UDP en broadcast (multicast ici pour éviter de trop utiliser de ressources) des communications
        try {

            while (true) {
                DatagramPacket packet = new DatagramPacket(in_buf, in_buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                this.model.notifyNewMsg(received);
                //System.out.printf("Received %s\n",received);

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot join multicast group");
        }
    }
}
