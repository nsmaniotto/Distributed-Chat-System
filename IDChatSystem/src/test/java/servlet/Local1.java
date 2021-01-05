package servlet;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

import java.util.ArrayList;

public class Local1 {
    public static void main(String[] args) {
        ArrayList<Integer> broadcast = new ArrayList<>();
        broadcast.add(1501);
        try {
            ClientController controller = new ClientController(0,1500,2000,broadcast);
        } catch (NoPortAvailable noPortAvailable) {
            noPortAvailable.printStackTrace();
        }
    }
}
