package logins.distanciel.LocalUserModel.testSetUsername;

import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.ArrayList;

public class Main {
    public static void main(String [] argv) {
        ArrayList<Integer> others1 = new ArrayList<>();
        others1.add(3000);
        UserModel model = new UserModel("0",2000, 2500, others1);
        model.setUsername("raclette");
    }
}
