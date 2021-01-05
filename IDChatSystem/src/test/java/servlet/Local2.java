package servlet;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

import java.util.ArrayList;

public class Local2 {
    public static void main(String[] args) {
        ArrayList<Integer> broadcast = new ArrayList<>();
        broadcast.add(1500);
        try {
            ClientController controller = new ClientController(1,1501,2001,broadcast);
        } catch (NoPortAvailable noPortAvailable) {
            noPortAvailable.printStackTrace();
        }
    }
}
