package project.insa.idchatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalUserModelReceiver implements Runnable {
    private LocalUserModel model;
    private InetAddress group;
    private MulticastSocket socket;
    //Format of UDP packets : username,id,ipAddress with :
    // - username : max 25 letters
    // - id : between 0 and 1 000 000 > 100 000 -> between 1 and 7 digits
    // - ipAdress : maximum 3*4 + 3 = 15 characters
    // Result = 25 + 7 + 15 = 47 character -> we will take 256 characters
    protected byte[] in_buf = new byte[256];

    public LocalUserModelReceiver(LocalUserModel model) {
        this.model = model;
        //Init broadcast -> in socket
        try {
            this.group = InetAddress.getByName("230.0.0.0");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            int in_port_broadcast = 4000;
            socket = new MulticastSocket(in_port_broadcast);
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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot join multicast group");
        }
    }
}
