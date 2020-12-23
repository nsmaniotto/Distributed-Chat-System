package gui.UserView.TestGlobal;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

import java.util.ArrayList;

public class Main3 {
    public static void main(String[] args) throws NoPortAvailable {
        ArrayList<Integer> loginsReceivers = new ArrayList<>();
        loginsReceivers.add(1501);
        loginsReceivers.add(1500);
        ClientController controller = new ClientController(2,1502,2002, loginsReceivers);
    }
}
