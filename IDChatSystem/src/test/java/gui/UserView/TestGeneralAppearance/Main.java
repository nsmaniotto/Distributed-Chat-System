package gui.UserView.TestGeneralAppearance;

import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.UserView;

import javax.swing.*;

public class Main {
    public static void main(String [] argv ) {
        User user = new User("fondue",24, "172.168.1.2");
        UserView view = new UserView(user);
        JFrame frame = new JFrame();
        frame.add(view);
        frame.setVisible(true);
        frame.pack();
    }
}
