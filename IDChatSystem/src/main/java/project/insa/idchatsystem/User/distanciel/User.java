
package project.insa.idchatsystem.User.distanciel;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.sql.Timestamp;

public class User {

    private String username;

    private String id;
    private static int maxLength = 5;
    private final String ipAddress;
    private boolean local_user;

    private Timestamp lastSeen;
    private int conversationHandlerListenerPort;
    private static String current_username = "";
    private static String current_id;
    private static String current_ipAddress;
    private static int current_conversationHandlerListenerPort;
    private static boolean current_local_user;

    public static void init_current_user(String id,boolean local) {
        User.current_id = id;
        User.current_username = String.format("--user%s",id);
        User.current_ipAddress = User.calculate_current_ip();
        User.current_local_user = local;
    }
    public static String calculate_current_ip()  {
        return "127.0.0.1";
    }
    public static void set_current_username(String username) throws Uninitialized {
        if (User.current_id.equals("")) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        User.current_username = username;
    }

    public static String get_current_username() throws Uninitialized {
        if (User.current_id.equals("")) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_username;
    }

    public static String get_current_id() throws Uninitialized {
        if (User.current_id.equals("")) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_id;
    }
    public static String get_current_id_to_show() throws Uninitialized {
        String id = User.get_current_id();
        if (id.length() > User.maxLength)
            return id.substring(0,User.maxLength) + "...";
        else
            return id;
    }

    public static String get_current_ipAddress() throws Uninitialized {
        if (User.current_id.equals("")) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_ipAddress;
    }
    public static String current_user_transfer_string() {
        String transfert_string = String.format("%s,%s,%s,%d,%s",User.current_username,User.current_id,User.current_ipAddress,User.current_conversationHandlerListenerPort,
                User.current_local_user ? "t" : "f");
        return transfert_string;
    }

    public static User getCurrentUser() throws Uninitialized {
        return new User(User.get_current_username(), User.get_current_id(), User.get_current_ipAddress(), User.current_local_user);
    }
    public static void setCurrentConversationHandlerListenerPort(int conversationHandlerListenerPort) {
        User.current_conversationHandlerListenerPort = conversationHandlerListenerPort;
    }
    /***********************Other users methods***************************/
    public User(String username, String id, String ipAddress) {
        this.username = username;
        this.id = id;
        this.ipAddress = ipAddress;
        this.lastSeen = new Timestamp(System.currentTimeMillis());
        this.conversationHandlerListenerPort = -1;
        this.local_user = true;
    }

    public User(String username, String id, String ipAddress,boolean local_user) {
        this.username = username;
        this.id = id;
        this.ipAddress = ipAddress;
        this.lastSeen = new Timestamp(System.currentTimeMillis());
        this.conversationHandlerListenerPort = -1;
        this.local_user = local_user;
    }
    public void setConversationHandlerListenerPort(int conversationHandlerListenerPort) {
        this.conversationHandlerListenerPort = conversationHandlerListenerPort;
    }

    public String get_username() {
        return this.username;
    }

    public String get_id() {
        return this.id;
    }
    public String get_id_to_show() {
        String id = this.get_id();
        if (id.length() > User.maxLength)
            return id.substring(0,User.maxLength) + "...";
        else
            return id;
    }

    public String get_ipAddress() {
        return this.ipAddress;
    }

    public Timestamp get_lastSeen() {
        return this.lastSeen;
    }

    public boolean isLocal_user() {
        return local_user;
    }

    public void setUsername(String username) { this.username = username;}
    public void setLastSeen(Timestamp lastSeen) {this.lastSeen = lastSeen;}

    /*********************Utilities methods*****************************/
    @Override
    public String toString() {
        return String.format("User %s ; id %s ; ipAddress %s ; lastSeen %s ; convListPort %d ; local %b", this.username, this.id, this.ipAddress, this.lastSeen.toString(),this.conversationHandlerListenerPort,this.local_user);
    }

    public int getConversationHandlerListenerPort() {
        return conversationHandlerListenerPort;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User))
            return false;
        User other = (User) o;
        if (this.id.equals(other.get_id())) {
            if(this.getConversationHandlerListenerPort()!= other.getConversationHandlerListenerPort() ||
                    !this.get_ipAddress().equals(other.get_ipAddress()))
                System.out.print("WARNING : the two objects have the same ids but have different conversationListenerPort or ipAdress\n");
            return true;
        }
        else
            return false;
    }
    public boolean fullEquals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User))
            return false;
        User other = (User) o;
        if (this.id.equals(other.get_id())
                && this.get_username().equals(other.get_username())
                && this.getConversationHandlerListenerPort()!= other.getConversationHandlerListenerPort()
                && this.get_ipAddress().equals(other.get_ipAddress())
                && this.isLocal_user() == other.isLocal_user())
            return true;
        else
            return false;
    }

}
