
package project.insa.idchatsystem.User.distanciel;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.net.*;
import java.sql.Timestamp;

public class User {

    private final String username;

    private int id = -1;

    private final String ipAddress;

    private final Timestamp lastSeen;
    private static String current_username = "";
    private static int current_id;
    private static String current_ipAddress;

    /************************Current user methods***************************************/
    public static void init_current_user(int id) {
        User.current_id = id;
        User.current_ipAddress = User.calculate_current_ip();
    }
    public static String calculate_current_ip()  {
        return "127.0.0.1";
    }
    public static void set_current_username(String username) throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        User.current_username = username;
    }

    public static String get_current_username() throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_username;
    }

    public static int get_current_id() throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_id;
    }

    public static String get_current_ipAddress() throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_ipAddress;
    }
    public static String current_user_transfer_string() {
        return String.format("%s,%d,%s",User.current_username,User.current_id,User.current_ipAddress);
    }

    /***********************Other users methods***************************/
    public User(String username, int id, String ipAddress) {
        this.username = username;
        this.id = id;
        this.ipAddress = ipAddress;
        this.lastSeen = new Timestamp(System.currentTimeMillis());
    }

    public String get_username() {
        return this.username;
    }

    public int get_id() {
        return this.id;
    }

    public String get_ipAddress() {
        return this.ipAddress;
    }

    public Timestamp get_lastSeen() {
        return this.lastSeen;
    }
    public String transfer_string() {
        return String.format("%s,%d,%s",this.username,this.id,this.ipAddress);
    }

    /*********************Utilities methods*****************************/
    @Override
    public String toString() {
        return String.format("User %s ; id %d ; ipAddress %s ; lastSeen %s", this.username, this.id, this.ipAddress, this.lastSeen.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User))
            return false;
        User other = (User) o;
        if (this.id == other.get_id())
            return true;
        else
            return false;
    }
}
