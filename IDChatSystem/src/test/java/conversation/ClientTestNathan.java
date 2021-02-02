/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author smani
 */
public class ClientTestNathan {    
    public ClientTestNathan() throws NoPortAvailable {
        int id = 10;
        ArrayList<Integer> arrayBroadCast = new ArrayList<Integer>();
        arrayBroadCast.add(2001);
        ClientController controller = new ClientController(id,2000,2010,arrayBroadCast);
        try {
            Thread.sleep(5000);//On laisse le temps d'entrer le login
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
        
        /*try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<Integer,User> userHashMap = controller.getConversationHandler().getUsers();
        User user = userHashMap.get(userHashMap.keySet().toArray()[0]);*/
        
        //controller.getConversationHandler().open(user); // Opening the first conversation
    }
}
