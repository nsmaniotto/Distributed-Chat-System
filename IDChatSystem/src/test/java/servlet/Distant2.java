package servlet;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;

public class Distant2 {
    public static void main(String[] args) {
        try {
            ClientController controller = new ClientController(3,1503,2003,null);
        } catch (NoPortAvailable noPortAvailable) {
            noPortAvailable.printStackTrace();
        }
    }
}
