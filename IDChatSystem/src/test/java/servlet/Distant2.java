package servlet;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Main;

public class Distant2 {
    public static void main(String[] args) throws Exception {
        String [] arguments = {"local","noClean","3"};
        Main.main(arguments);
    }
}
