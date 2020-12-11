package logins.distanciel.ClientControlleur.TestNotifyLogins;

import logins.distanciel.ClientControlleur.ControllerEncapsulation;

import java.util.ArrayList;

public class Main2 {
    public static void main (String [] argv) {
        ArrayList<Integer> others2 = new ArrayList<>();
        others2.add(2000);
        ControllerEncapsulation controller_appli2 = new ControllerEncapsulation(1,4000,4500,others2,2601,2600);
    }
}
