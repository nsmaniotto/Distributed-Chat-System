package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.User.distanciel.User;

import java.io.IOException;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalUserModelReceiver implements Runnable {
    private LocalUserModel model;
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

    public LocalUserModelReceiver(LocalUserModel model, int in_port_broadcast) {
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
            Pattern pattern_new_host = Pattern.compile("(?<username>[A-Za-z_.0-9]+),(?<id>[0-9]+),(?<ip>[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+),(?<convListPort>[0-9]+)");
            Pattern pattern_disconnected = Pattern.compile("(?<id>[0-9]+),disconnected");
            Pattern pattern_update = Pattern.compile("update");
            while (true) {
                DatagramPacket packet = new DatagramPacket(in_buf, in_buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //System.out.printf("Received %s\n",received);
                //Extraction of the informations of the packet thanks to a regex and named group
                Matcher m = pattern_new_host.matcher(received);
                while (m.find()){
                    //System.out.println("NEW USER");
                    User new_user = new User(m.group("username"),Integer.parseInt(m.group("id")),m.group("ip"));
                    new_user.setConversationHandlerListenerPort(Integer.parseInt(m.group("convListPort")));
                    this.model.addOnlineUser(new_user);//Add or refresh informations of the user based on the id
                }
                m = pattern_disconnected.matcher(received);
                while (m.find()){
                    System.out.printf("User %s disconnection signal\n",m.group("id"));
                    this.model.removeOnlineUser(Integer.parseInt(m.group("id")));//Add or refresh informations of the user based on the id
                }
                m = pattern_update.matcher(received);
                while (m.find()){
                    System.out.print("Received ask for update\n");
                    this.model.diffuseNewUsername();//Add or refresh informations of the user based on the id
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot join multicast group");
        }
    }
}
