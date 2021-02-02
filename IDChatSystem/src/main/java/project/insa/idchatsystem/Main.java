
package project.insa.idchatsystem;

//import project.insa.idchatsystem.Exceptions.NoPortAvailable;

//import java.util.ArrayList;

import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.database.LoginsBroadcastDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length < 2)
            throw new Exception("Arguments expected");
        String id;
        String filename = "ClientID.txt";
        if(args.length == 3) {
            System.out.printf(".(Main.java:30) - main : 3 arguments\n");
            filename = String.format("ClientID%s.txt", args[2]);
        }
        try {
            File f = new File(filename);
            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
                try {
                    id = String.valueOf(UUID.randomUUID());
                    FileWriter myWriter = new FileWriter(filename);
                    myWriter.write(id);
                    myWriter.close();
                    Main.createClient(id,args[0].equals("local"),args[1].equals("clean"));
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("File already exists.");
                Scanner myReader = new Scanner(f);
                id = myReader.next();
                myReader.close();
                Main.createClient(id,args[0].equals("local"),args[1].equals("clean"));
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void createClient(String id,boolean local,boolean clean) {
        try {
            ClientController controller = new ClientController(id,local,clean);
        } catch (NoPortAvailable noPortAvailable) {
            noPortAvailable.printStackTrace();
        }
    }
}
