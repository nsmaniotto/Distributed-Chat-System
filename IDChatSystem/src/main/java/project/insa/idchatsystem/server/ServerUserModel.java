
package project.insa.idchatsystem.server;

import project.insa.idchatsystem.User;

public class ServerUserModel {
    private ServerController serverController;
    private User users;

    public ServerUserModel() {

    }

    public void addOnlineUser(User user) {

    }

    public void removeOnlineUser(User user) {

    }

    public boolean checkAvailable(String username, int id) {
        return false;
    }

    public User getOnlineUsers() {
        return null;
    }
}
