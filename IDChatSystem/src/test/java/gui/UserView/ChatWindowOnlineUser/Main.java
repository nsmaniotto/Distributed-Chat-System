package gui.UserView.ChatWindowOnlineUser;

import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.gui.ChatWindow;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ChatWindow window = new ChatWindow();
        HashMap<Integer, User> users = new HashMap<>();
        users.put(0,new User("navet","0","127.0.0.1"));
        users.put(1,new User("chat","1","127.0.0.1"));
        window.display();
        window.updateUsers(users);
    }
}
