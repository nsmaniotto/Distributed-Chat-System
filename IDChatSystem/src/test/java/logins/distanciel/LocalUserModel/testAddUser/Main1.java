package logins.distanciel.LocalUserModel.testAddUser;

import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Main1 {
    public static void main(String [] argv) {
        int DUREE_DIFF_EMISSION = 5000;
        UserModel model = new UserModel(0,2700,2705, new ArrayList<>() {{
            add(2701);
        }});
        model.setUsername("user0");
        for (int i=0;i<5;i++){
            try {
                Thread.sleep(DUREE_DIFF_EMISSION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main1.print_HashMap(model.getOnlineUsers());
        }
        HashMap hashmap_th = new HashMap();
        hashmap_th.put(1,new User("user1",1,"127.0.0.1"));
        if(model.getOnlineUsers().equals(hashmap_th))
            System.out.println("--------------------------------------------TEST OK--------------------------------------");
        else
            System.out.println("---------------------------------------------ECHEC---------------------------------------");
    }
    public static void print_HashMap(HashMap<Integer, User> users) {
        System.out.println("Liste des utilisateurs distants connus : ");
        users.forEach((k,v)->System.out.printf("%s\n",v.toString()));
    }
}
