package gui.UserView.TestGlobal;

import project.insa.idchatsystem.ClientController;

import java.util.ArrayList;

public class Main1 {
    public static void main(String[] args) {
        ArrayList<Integer> loginsReceivers = new ArrayList<>();
        loginsReceivers.add(1501);
        ClientController controller = new ClientController(0,1500,2000, loginsReceivers,3000,3500);
    }
}
