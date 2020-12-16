/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Exceptions.NoPortAvailable;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author smani
 */
public class ClientTestRobin {
    public ClientTestRobin() throws NoPortAvailable {
        int id = 20;
        ArrayList<Integer> arrayBroadCast = new ArrayList<Integer>();
        arrayBroadCast.add(2000);
        ClientController controller = new ClientController(id,2001,2011,arrayBroadCast,2500);
        
        /* TESTING TCP COMMUNICATIONS */
        try {
            Thread.sleep(10000);//waiting for input login
        } catch (Exception ignored) {
            
        }
        HashMap<Integer,User> userHashMap = controller.getConversationHandler().getUsers();
        User user = userHashMap.get(userHashMap.keySet().toArray()[0]);
        controller.getConversationHandler().open(user); // Sending a message to Nathan
        controller.getConversationHandler().getCurrentConversation().send(new Message("Hi Nathan !"));
        
        try {
            Thread.sleep(1000);
        } catch (Exception ignored) {
            
        }

        controller.getConversationHandler().getCurrentConversation().send(new Message("Hello again !"));
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            
        }

        controller.getConversationHandler().getCurrentConversation().send(new Message(":-)"));
    }
}