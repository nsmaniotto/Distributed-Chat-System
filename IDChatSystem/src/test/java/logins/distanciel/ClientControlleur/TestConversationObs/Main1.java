package logins.distanciel.ClientControlleur.TestConversationObs;

import logins.distanciel.ClientControlleur.ControllerEncapsulation;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;

public class Main1 {
    public static void main (String [] argv) {
        ArrayList<Integer> others1 = new ArrayList<>();
        others1.add(4000);
        System.out.println(User.calculate_current_ip());
        ControllerEncapsulation controller_appli1 = new ControllerEncapsulation(0,2000,2500,others1);
        try {
            Thread.sleep(6000);
            System.out.println("Starting conversation....");
            controller_appli1.startConversation(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
