
package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.net.InetAddress;

class User {

    private final String username;

    private int id = -1;

    private final String ipAddress;

    private final Timestamp lastSeen;
    private static  String current_username = "";
    private static int current_id;
    private static String current_ipAddress;
    /************************Current user methods***************************************/
    public static void init_current_user(int id) throws UnknownHostException {
        User.current_id = id;
        User.current_ipAddress = InetAddress.getLocalHost().getHostAddress();
    }
    public static void set_current_username(String username) throws Uninitialized {
        if(User.current_id == -1){
            throw new Uninitialized("CurrentUser not initialized");
        }
        User.current_username = username;
    }
    public static  String get_current_username() throws Uninitialized {
        if(User.current_id == -1){
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_username;
    }
    public static int get_current_id () throws Uninitialized {
        if(User.current_id == -1){
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_id;
    }
    public static String get_current_ipAddress() throws Uninitialized {
        if(User.current_id == -1){
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_ipAddress;
    }
    /***********************Other users methods***************************/
    public User(String username,int id,String ipAddress){
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
	/*********************Utilities methods*****************************/
    @Override
    public String toString() {
        return String.format("User %s ; id %d ; ipAddress %s ; lastSeen %s",this.username,this.id,this.ipAddress,this.lastSeen.toString());
    }
}
