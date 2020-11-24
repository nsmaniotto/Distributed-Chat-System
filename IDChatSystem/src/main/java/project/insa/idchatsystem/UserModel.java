
package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import javax.swing.JList;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.*;

class UserModel implements Runnable {
    private String networkMode;
    private final HashMap<Integer,User> users;
    //Format of UDP packets : username,id,ipAddress with :
    // - username : max 25 letters
    // - id : between 0 and 1 000 000 > 100 000 -> between 1 and 7 digits
    // - ipAdress : maximum 3*4 + 3 = 15 characters
    // Result = 25 + 7 + 15 = 47 character -> we will take 256 characters
    protected byte[] in_buf = new byte[256];
    protected byte[] out_buf = new byte[256];
    private final int in_port_broadcast = 4000;
    private final int out_port_broadcast = 4001;
    private InetAddress group;
    private MulticastSocket socket;

    public UserModel(String networkMode, int id)  {
        this.networkMode = networkMode;
        this.users = new HashMap<Integer,User>();
        User.init_current_user(id);
        try {
            this.group = InetAddress.getByName("230.0.0.0");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //Init broadcast -> in socket
        try {
            socket = new MulticastSocket(in_port_broadcast);
            socket.setSoTimeout(0);//infinite timeout to block on receive
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Mutlicast socket in error");
        }
        //init in -> broadcast socket
        try {
            socket = new MulticastSocket(out_port_broadcast);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Mutlicast socket out error");
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

    public void diffuseNewUsername(String username) throws Uninitialized {
        User.set_current_username(username);
        String response = User.current_user_transfer_string();
        DatagramPacket outPacket = new DatagramPacket(response.getBytes(), response.length());
        try {
            this.socket.send(outPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAvailable(String username) {//TODO : prototype to be modified in the class diagram
        for (Map.Entry<Integer, User> integerUserEntry : this.users.entrySet()) {
            Map.Entry map_entry = (Map.Entry) integerUserEntry;
            User user = (User) map_entry.getValue();
            if (user.get_username().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        //Création des objets pour communiquer sur le réseau de login
        //Création des objets pour envoyer et recevoir de l'UDP en broadcast (multicast ici pour éviter de trop utiliser de ressources) des communications
        try {
            socket.joinGroup(group);
            Pattern pattern_new_host = Pattern.compile("(?<username>[A-Za-z_.]+),(?<id>[0-9]+),(?<ip>[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+)");
            while (true) {
                DatagramPacket packet = new DatagramPacket(in_buf, in_buf.length);
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
