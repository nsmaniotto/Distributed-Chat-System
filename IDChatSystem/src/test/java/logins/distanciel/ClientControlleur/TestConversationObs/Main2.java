package logins.distanciel.ClientControlleur.TestConversationObs;

import logins.distanciel.ClientControlleur.ControllerEncapsulation;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

import java.util.ArrayList;

public class Main2 {
    public static void main (String [] argv) throws NoPortAvailable {
        ArrayList<Integer> others2 = new ArrayList<>();
        others2.add(2000);
        ControllerEncapsulation controller_appli2 = new ControllerEncapsulation(1,4000,4500,others2);
    }
}
