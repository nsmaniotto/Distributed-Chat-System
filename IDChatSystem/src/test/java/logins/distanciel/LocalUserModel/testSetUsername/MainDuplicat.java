package logins.distanciel.LocalUserModel.testSetUsername;

import project.insa.idchatsystem.logins.local_mode.distanciel.LocalUserModel;

import java.util.ArrayList;

public class MainDuplicat {
    public static void main(String [] argv) {
        ArrayList<Integer> others1 = new ArrayList<>();
        others1.add(2000);
        LocalUserModel model = new LocalUserModel(0,3000, 3500, others1);
        model.setUsername("raclette");
    }
}
