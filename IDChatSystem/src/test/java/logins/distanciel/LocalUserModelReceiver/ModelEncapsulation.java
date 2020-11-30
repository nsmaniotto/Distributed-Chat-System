package logins.distanciel.LocalUserModelReceiver;

import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;
import project.insa.idchatsystem.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelEncapsulation extends LocalUserModel {
    public ModelEncapsulation(int id, int receiver_port, int emitter_port, ArrayList<Integer>others) {
        super(id,receiver_port,emitter_port,others);
        System.out.println("--------------------------------------DEBUT CHAMP TEST : tester LLocalUserModel si erreur avant------------------------------------------");
        this.testAjoutNouvelUtilisateur(receiver_port);

    }
    public boolean testAjoutNouvelUtilisateur (int receiver_port) {
        int port_emetteur_test = 2000;
        //On affiche les utilisateurs en mémoire
        HashMap<Integer, User> users = this.getOnlineUsers();
        this.printOnlineUsers(users);
        //On compte le nb d'utilisateurs
        int nb_users_avant = users.size();
        //On signale un nouvel utilisateur (émission d'un paquet indiquant un nvl user vers port de réception)
        try {
            DatagramSocket socket = new DatagramSocket(port_emetteur_test);
            String message = "monUsername,1,127.0.0.1";
            DatagramPacket outPacket = new DatagramPacket(message.getBytes(),
                    message.length(),
                    socket.getLocalAddress(), receiver_port);
            try {
                socket.send(outPacket);
                System.out.printf("Envoi du packet %s\n",message);
                //On attend quelques secondes
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //On affiche les utilisateurs en mémoire
                HashMap<Integer, User> users_updated = this.getOnlineUsers();
                this.printOnlineUsers(users_updated);
                //On compte le nb d'utilisateurs
                int nb_users_apres = users_updated.size();
                if (!(nb_users_apres == (nb_users_avant + 1))) {
                    System.out.printf("Nb users attendus : %d contre %d trouvés%n", nb_users_avant + 1, nb_users_apres);
                    System.out.println("------------------------------------ECHEC-------------------------------");
                    return false;
                } else {
                    System.out.println("-------------------------------------------------------------Test ok-------------------------------------------------");
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de l'envoi du paquet");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Cannot create socket ");
        }
        System.out.println("------------------------------------ECHEC-------------------------------");
        return false;
    }
    public void printOnlineUsers (HashMap < Integer, User > users){
        if(users.size()==0){
            System.out.println("Pas d'utilisateur");
        }
        users.forEach((k, v) -> System.out.printf("%s\n", v.toString()));
    }

    @Override
    public boolean checkavailable(String username) {
        System.out.println("Error checkavailable is not available for this test");
        return false;
    }

    @Override
    public void diffuseNewUsername() {
        System.out.println("Error diffuseNewUsername is not available for this test");
    }
}
