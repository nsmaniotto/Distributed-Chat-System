package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.util.HashMap;
import java.util.Map;

public interface UserModel {
    void setUsername(String newUserName);

    void addOnlineUser(User user) ;

    void removeOnlineUser(User user);

    HashMap<Integer,User> getOnlineUsers();

    void diffuseNewUsername();

    boolean checkAvailable(String username);
}
