package servlet;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Main;

import java.util.ArrayList;

public class Local2 {
    public static void main(String[] args) throws Exception {
        String [] arguments = {"local","noClean","1"};
        Main.main(arguments);
    }
}
