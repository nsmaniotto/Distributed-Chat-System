package servlet;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

import java.util.ArrayList;

public class Distant1 {
    public static void main(String[] args) {
        try {
            ClientController controller = new ClientController(2,1502,2002,null);
        } catch (NoPortAvailable noPortAvailable) {
            noPortAvailable.printStackTrace();
        }
    }
}
