package logins.distanciel.LocalUserModel.testRemoveUser;

import project.insa.idchatsystem.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2 {
    public static void main(String [] argv) throws InterruptedException {
        int DUREE_DIFF_EMISSION = 5000;
        int mode = 1;
        Thread.sleep(DUREE_DIFF_EMISSION*2);//On veut être sûr que le premier utilisateur est celui de Main1
        LocalUserModel model = new LocalUserModel(1,2701,2706,new ArrayList<>(){{
            add(2700);
        }});
        model.setUsername("user1");
        for (int i=0;i<5;i++){
            Thread.sleep(DUREE_DIFF_EMISSION);
            logins.distanciel.LocalUserModel.testAddUser.Main2.print_HashMap(model.getOnlineUsers());
            Main2.print_HashMap(model.getOnlineUsers());
        }
        if(mode == 0)
            model.stopperEmission();
        else
            model.disconnect();
    }
    public static void print_HashMap(HashMap<Integer, User> users) {
        System.out.println("Liste des utilisateurs distants connus : ");
        users.forEach((k,v)->System.out.printf("%s\n",v.toString()));
    }
}
