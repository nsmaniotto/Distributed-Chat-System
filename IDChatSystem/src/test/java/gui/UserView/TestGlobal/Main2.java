package gui.UserView.TestGlobal;

import project.insa.idchatsystem.ClientController;

import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) {
        ArrayList<Integer> loginsReceivers = new ArrayList<>();
        loginsReceivers.add(1500);
        ClientController controller = new ClientController(0,1501,2001, loginsReceivers,3500,3000);
    }
}
