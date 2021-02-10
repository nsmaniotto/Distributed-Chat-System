package logins.distanciel.LocalUserModel.testAddUser;

import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2 {
    public static void main(String [] argv) throws InterruptedException {
        int DUREE_DIFF_EMISSION = 500;
        Thread.sleep(DUREE_DIFF_EMISSION*2);//On veut être sûr que le premier utilisateur est celui de Main1
        UserModel model = new UserModel("1",2701,2706,new ArrayList<>(){{
            add(2700);
        }});
        model.setUsername("user1");
        for (int i=0;i<5;i++){
            Thread.sleep(DUREE_DIFF_EMISSION);
            Main2.print_HashMap(model.getOnlineUsers());
        }
        HashMap hashmap_th = new HashMap();
        hashmap_th.put(0,new User("user0","0","127.0.0.1"));
        if(model.getOnlineUsers().equals(hashmap_th))
            System.out.println("--------------------------------------------TEST OK--------------------------------------");
        else
            System.out.println("---------------------------------------------ECHEC---------------------------------------");
    }
    public static void print_HashMap(HashMap<String, User> users) {
        System.out.println("Liste des utilisateurs distants connus : ");
        users.forEach((k,v)->System.out.printf("%s\n",v.toString()));
    }
}
