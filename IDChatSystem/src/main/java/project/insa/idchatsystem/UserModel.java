
package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import javax.swing.JList;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.regex.*;

class UserModel implements Runnable {
    private String networkMode;
    private HashMap<Integer,User> users;
    protected MulticastSocket socket = null;
    //Format of UDP packets : username,id,ipAddress with :
    // - username : max 25 letters
    // - id : between 0 and 1 000 000 > 100 000 -> between 1 and 7 digits
    // - ipAdress : maximum 3*4 + 3 = 15 characters
    // Result = 25 + 7 + 15 = 47 character -> we will round this number to 50 characters
    protected byte[] buf = new byte[256];
    int port_broadcast = 4000;

    public UserModel(String networkMode, int id) throws UnknownHostException {//TODO : revoir si exception à gérer plus haut ou pas
        this.networkMode = networkMode;
        this.users = new HashMap<Integer,User>();
        User.init_current_user(id);

        InetAddress group = null;
        try {
            group = InetAddress.getByName("230.0.0.0");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String newUserName) {
        try {
            User.set_current_username(newUserName);
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }

    public void addOnlineUser(User user) {
        this.users.put(user.get_id(),user);//Replace automatically the previous version if already in the HashMap
    }

    public void removeOnlineUser(User user) {
        if(this.users.remove(user.get_id()) == null) {
            System.out.printf("%s is not connected%n",user.toString());
        }
    }

    public HashMap<Integer,User> getOnlineUsers() {
        return this.users;
    }

    public void diffuseNewUsername(String username, int id) {
        
    }

    public boolean checkAvailable(String username, int id) {
        return false;   
    }

    @Override
    public void run() {
        //Création des objets pour communiquer sur le réseau de login
        //Création des objets pour envoyer et recevoir de l'UDP en broadcast (multicast ici pour éviter de trop utiliser de ressources) des communications
        try {
            socket = new MulticastSocket(port_broadcast);
            socket.setSoTimeout(0);//infinite timeout to block on receive
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Mutlicast socket error");
        }
        Pattern pattern_new_host = Pattern.compile("(?<username>[A-Za-z_.]+),(?<id>[0-9]+),(?<ip>[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+)");
        try {
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //Extraction of the informations of the packet thanks to a regex and named group
                Matcher m = pattern_new_host.matcher(received);
                while (m.find()){
                    User new_user = new User(m.group("username"),Integer.parseInt(m.group("id")),m.group("ip"));
                    this.addOnlineUser(new_user);//Add or refresh informations of the user based on the id
                }
            }
            //socket.leaveGroup(group);
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot join multicast group");
        }
    }
}
