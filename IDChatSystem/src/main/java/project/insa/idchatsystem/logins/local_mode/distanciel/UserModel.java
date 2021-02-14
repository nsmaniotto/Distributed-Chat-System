package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.Server.Observers.ServerLoginControllerObserver;
import project.insa.idchatsystem.Observers.logins.Observables.ObservableUserModel;
import project.insa.idchatsystem.Observers.logins.Observers.UserModelEmittersObserver;
import project.insa.idchatsystem.Observers.logins.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.Facades.UserModelEmitters;
import project.insa.idchatsystem.logins.local_mode.distanciel.Facades.UserModelReceivers;
import project.insa.idchatsystem.servlet.ServerController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserModel implements ServerLoginControllerObserver, UserModelEmittersObserver, ObservableUserModel {
    private final UserModelEmitters emitters;
    private final ServerController serverController;
    private final UserModelReceivers receivers;
    private ArrayList<UsersStatusObserver> observers;
    private HashMap<String, User> users;
    private final long limite_tolerance_ss_nouvelles = 600;


    public UserModel(String id, int receiver_port, int emitter_port, ArrayList<Integer> others)  {
        User.init_current_user(id,others != null);
        this.users = new HashMap<>();
        observers = new ArrayList<>();
        this.emitters = new UserModelEmitters(this,emitter_port,receiver_port,others,others != null);
        this.receivers = new UserModelReceivers(this,receiver_port,others != null);
        this.serverController = new ServerController("login");
        this.serverController.addLoginListener(this);
        this.serverController.publish("ready");
        this.setUsername(String.format("--user%s",id));
        new Thread(this.emitters).start();
        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.checkUserStillActive();
            }
        }).start();
    }

    /**
     * Adding a new user to the model and inform the controller of this event
     * @param user
     */
    public void addOnlineUser(User user) {
        this.users.put(user.get_id(),user);//Replace automatically the previous version if already in the HashMap
        this.notifyNewUserObservers(user);
    }

    /**
     * Removing an online user and inform the controller of this event
     * @param id
     */
    public void removeOnlineUser(String id) {
        User removed_user = this.users.remove(id);
        if(removed_user == null) {
            System.out.printf("%s was not connected%n",id);
        }
        else {
            this.notifyDisconnectedObservers(removed_user);
        }
    }

    /**
     * Check if all users are still active thanks to their timestamps set when they arrive. Then remove offline users
     */
    protected void checkUserStillActive() {
        long time = System.currentTimeMillis();
        ArrayList<String> toRemove = new ArrayList<>();
        this.users.forEach((k,v) -> {
            if(time-v.get_lastSeen().getTime() > limite_tolerance_ss_nouvelles) {
                toRemove.add(k);
            }
        });
        for (String index :toRemove) {
            this.removeOnlineUser(index);
        }
    }

    public HashMap<String,User> getOnlineUsers() {
        return this.users;
    }

    /**
     * Check if a login is already used in the local hashmap
     * @param username
     * @return
     */
    public boolean checkLocallyAvailable(String username) {
        for (Map.Entry<String, User> integerUserEntry : this.users.entrySet()) {
            User user = (User) ((Map.Entry) integerUserEntry).getValue();
            if (user.get_username().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the desired username for the current user is not already taken by another connected client
     * @param username
     * @return
     */
    public boolean setUsername(String username) {
        this.emitters.askUpdate();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(this.checkLocallyAvailable(username)) {
            System.out.print("Changing username\n");
            try {
                User.set_current_username(username);
            } catch (Uninitialized uninitialized) {
                uninitialized.printStackTrace();
            }
            this.diffuseNewUsername();
            return true;
        }
        else {
            System.out.printf("Username %s already taken !\n",username);
            return false;
        }
    }

    /**
     * Stop the local emission publish state disconnected
     */
    public void stopperEmission(){
        this.emitters.stopperEmission();
        try {
            this.serverController.publish(String.format("login,%s,disconnected",User.get_current_id()));
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }


    /**
     * Stop the local emission publish state disconnected
     */
    //TODO : Duplicate
    public void disconnect() {
        try {
            this.emitters.disconnect(User.get_current_id());
            this.stopperEmission();
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }

    /**
     * Diffuse the new login informations containing new username on the global network
     */
    public void diffuseNewUsername(){
        String response = User.current_user_transfer_string();
        this.emitters.diffuseNewUsername(response);
    }


    @Override
    public void addUserModelObserver(UsersStatusObserver obs) {
        System.out.printf("%s\n",obs.toString());
        this.observers.add(obs);
    }

    @Override
    public void deleteUserModelObserver(UsersStatusObserver obs) {
        int index = this.observers.indexOf(obs);
        if(index != -1)
            this.observers.remove(index);
        else
            System.out.println("Object not an observer stored");
    }

    /**
     * Notify that a new user is connected
     * @param user
     */
    @Override
    public void notifyNewUserObservers(User user)  {
        //System.out.printf("LOCALUSERMODEL : Online : %s\n",user);
        for (UsersStatusObserver obs :
                this.observers) {
            obs.onlineUser(user);
        }
    }

    @Override
    public void notifyDisconnectedObservers(User user) {
        for (UsersStatusObserver obs :
                this.observers) {
            obs.offlineUser(user);
        }
    }

    @Override
    public void notifyNewMessage(String message) {
        this.receivers.notifyNewMsg(message);
    }

    @Override
    public void newMsgToSend(String message) {
        this.serverController.sendMessage(message,null);
    }
}
