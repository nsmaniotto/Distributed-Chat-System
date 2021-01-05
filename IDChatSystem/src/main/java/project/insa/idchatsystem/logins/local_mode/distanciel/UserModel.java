package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.Server.ServerLoginControllerObserver;
import project.insa.idchatsystem.Observers.logins.UserModelEmittersObserver;
import project.insa.idchatsystem.Observers.logins.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.AbstractUserModel;
import project.insa.idchatsystem.logins.local_mode.distanciel.Facades.UserModelEmitters;
import project.insa.idchatsystem.logins.local_mode.distanciel.Facades.UserModelReceivers;
import project.insa.idchatsystem.servlet.ServerController;

import java.util.ArrayList;

public class UserModel extends AbstractUserModel implements ServerLoginControllerObserver, UserModelEmittersObserver {
    private final UserModelEmitters emitters;
    private final ServerController serverController;
    private final UserModelReceivers receivers;
    private ArrayList<UsersStatusObserver> observers;
    public UserModel(int id, int receiver_port, int emitter_port, ArrayList<Integer> others)  {
        super(id, others != null);
        observers = new ArrayList<>();
        this.emitters = new UserModelEmitters(this,emitter_port,others,others != null);
        this.receivers = new UserModelReceivers(this,receiver_port);
        this.serverController = ServerController.getInstance();
        this.serverController.publish("Connected");
        this.serverController.addLoginListener(this);
        this.setUsername(String.format("--user%d",id));
        new Thread(this.emitters).start();
        new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkUserStillActive();
            }
        }).start();
    }
    @Override
    public boolean setUsername(String username) {
        this.emitters.askUpdate();
        try {
            this.serverController.sendMessage(String.format("login,update,%d",User.get_current_id()),null);
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(this.checkavailable(username)) {
            super.setUsername(username);
            return true;
        }
        else {
            System.out.printf("Username %s already taken !\n",username);
            return false;
        }
    }
    @Override
    public void stopperEmission(){
        this.emitters.stopperEmission();
        try {
            this.serverController.publish(String.format("login,%d,disconnected",User.get_current_id()));
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }
    @Override
    public void disconnect() {
        try {
            while(!this.emitters.getState().equals("disconnected")) {
                this.emitters.disconnect(User.get_current_id());
            }
            this.stopperEmission();
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }
    @Override
    public void diffuseNewUsername(){
        String response = User.current_user_transfer_string();
        this.emitters.diffuseNewUsername(response);
        this.serverController.sendMessage(String.format("login,%s",response),null);
    }

    @Override
    public boolean checkavailable(String username) {
        this.emitters.askUpdate();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.checkLocallyAvailable(username);
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
        this.serverController.sendMessage(String.format("login,%s",message),null);
    }
}
