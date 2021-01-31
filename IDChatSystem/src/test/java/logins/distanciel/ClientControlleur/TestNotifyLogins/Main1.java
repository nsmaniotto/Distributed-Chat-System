package logins.distanciel.ClientControlleur.TestNotifyLogins;

import logins.distanciel.ClientControlleur.ControllerEncapsulation;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

import java.util.ArrayList;

public class Main1 {
    public static void main (String [] argv) throws NoPortAvailable {
        ArrayList<Integer> others1 = new ArrayList<>();
        others1.add(4000);
        ControllerEncapsulation controller_appli1 = new ControllerEncapsulation("0",2000,2500,others1);
    }
}
