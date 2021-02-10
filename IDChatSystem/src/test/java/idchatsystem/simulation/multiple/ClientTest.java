/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idchatsystem.simulation.multiple;

import java.util.ArrayList;
import java.util.HashMap;
import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.User.distanciel.User;

/**
 *
 * @author smani
 */
public class ClientTest {
    public ClientTest(String id, int loginReceiverPort, int loginEmiterPort, int broadcastPort) throws NoPortAvailable {
        ArrayList<Integer> arrayBroadCast = new ArrayList<Integer>();
        arrayBroadCast.add(2001);
        ClientController controller = new ClientController(id,true,false);
        try {
            Thread.sleep(500);//On laisse le temps d'entrer le login
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if(User.get_current_username().equals(String.format("--user%d",id))) {
                System.out.print("Error, the login has not be taken into account\n");
            }
            else {
                System.out.printf("All Green, login correct ? %s\n",User.get_current_username());
            }
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }
}
