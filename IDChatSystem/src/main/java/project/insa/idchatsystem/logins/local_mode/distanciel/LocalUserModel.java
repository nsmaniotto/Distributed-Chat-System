package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.UserModel;

import java.util.ArrayList;

public class LocalUserModel extends UserModel {
    private final LocalUserModelEmitter emitter;
    private ArrayList<UsersStatusObserver> observers;
    public LocalUserModel(int id, int receiver_port, int emitter_port, ArrayList<Integer> others)  {
        super(id);
        observers = new ArrayList<>();
        new Thread(new LocalUserModelReceiver(this,receiver_port)).start();
        this.emitter = new LocalUserModelEmitter(emitter_port,others);
        this.setUsername(String.format("--user%d",id));
        new Thread(this.emitter).start();
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
        this.emitter.askUpdate();
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
        this.emitter.stopperEmission();
    }
    @Override
    public void disconnect() {
        try {
            while(!this.emitter.getState().equals("disconnected")) {
                this.emitter.disconnect(User.get_current_id());
            }
            this.stopperEmission();
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }
    @Override
    public void diffuseNewUsername(){
        String response = User.current_user_transfer_string();
        this.emitter.diffuseNewUsername(response);
    }

    @Override
    public boolean checkavailable(String username) {
        this.emitter.askUpdate();
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
}
