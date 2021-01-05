package logins.distanciel.UserModelEmitters;


import project.insa.idchatsystem.logins.local_mode.distanciel.Facades.UserModelEmitters;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Main {
    public static void main(String [] args) {
        System.out.println("-------------------------------------------DEBUT ZONE TEST-------------------------------------------");
        int DUREE_DIFF_EMISSION = 5000;

        //Création des ports sur lesquels écoutent les autres clients et création des clients qui écoutes
        ArrayList<Integer> others = new ArrayList<Integer>();
        others.add(2101);
        //Démarrage de l'émission (émission périodique)
        System.out.println("Démarrage émission");
        UserModelEmitters emitter = new UserModelEmitters(null,2100,others,true);
        new Thread(emitter).start();
        emitter.diffuseNewUsername("monUserName");
        //Attente en réception
        try {
            DatagramSocket dgramSocket = new DatagramSocket(2101);
            dgramSocket.setSoTimeout(0);
            byte[] buffer = new byte[1000];
            DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
            int nb_recu = 0;
            long difference_temps = 0, prec_temps = 0, temps_act = 0;
            int i=0;
            while(i<5) {
                try {
                    System.out.println("Attente...");
                    dgramSocket.receive(inPacket);
                    String message = new String(inPacket.getData(), 0, inPacket.getLength());
                    System.out.printf("Recu : %s\n",message);
                    nb_recu++;
                    if(prec_temps == 0) {
                        prec_temps = System.currentTimeMillis();
                        nb_recu = 0;//On exclut le 1er paquet du décompte pour avoir une référence
                    }
                    difference_temps = System.currentTimeMillis() - prec_temps;
                    System.out.printf("Pertes estimées : %f\n",nb_recu-(float)difference_temps/DUREE_DIFF_EMISSION);
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if((nb_recu-(float)difference_temps/DUREE_DIFF_EMISSION) < 1) {
                System.out.println("-------------------------------------------TEST OK-------------------------------------------");
            }
            else {
                System.out.println("------------------------------------------A REVERIFIER-----------------------------------------");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
