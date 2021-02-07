package servlet;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Main;

import java.util.ArrayList;

public class Local3 {
    public static void main(String[] args) throws Exception {
        String [] arguments = {"local","noClean","10"};
        Main.main(arguments);
    }
}
