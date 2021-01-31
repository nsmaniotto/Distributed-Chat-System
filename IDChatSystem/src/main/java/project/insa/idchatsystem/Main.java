
package project.insa.idchatsystem;

//import project.insa.idchatsystem.Exceptions.NoPortAvailable;

//import java.util.ArrayList;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        StringBuilder sb = new StringBuilder(String.valueOf(UUID.randomUUID()));
        System.out.printf("%s\n",sb);
//        ArrayList<Integer> broadcast = new ArrayList<>();
//        broadcast.add(1501);
//        try {
//            ClientController controller = new ClientController(0,1500,2000,broadcast);
//        } catch (NoPortAvailable noPortAvailable) {
//            noPortAvailable.printStackTrace();
//        }
    }
}
