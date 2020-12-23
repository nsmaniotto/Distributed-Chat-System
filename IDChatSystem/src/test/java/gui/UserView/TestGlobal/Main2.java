package gui.UserView.TestGlobal;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) throws NoPortAvailable {
        ArrayList<Integer> loginsReceivers = new ArrayList<>();
        loginsReceivers.add(1500);
        ClientController controller = new ClientController(0,1501,2001, loginsReceivers);
    }
}
