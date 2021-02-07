package project.insa.idchatsystem.logins.local_mode.distanciel.Facades;

import project.insa.idchatsystem.Observers.logins.Observers.UserModelEmittersObserver;
import project.insa.idchatsystem.logins.local_mode.distanciel.Local.LocalUserModelEmitter;

import java.util.ArrayList;

public class UserModelEmitters implements Runnable {
    private LocalUserModelEmitter localEmitter;
    protected String last_user_updated_string;
    protected  boolean emission = true;
    protected String state = "connected";
    private UserModelEmittersObserver obs;
    private boolean local;
    public UserModelEmitters(UserModelEmittersObserver obs,int emitter_port, int receiver_port, ArrayList<Integer> others,boolean local) {
        this.local = local;
        this.obs = obs;
        if(this.local) {
            localEmitter = new LocalUserModelEmitter(emitter_port, receiver_port,others);
            new Thread(localEmitter).start();
        }
        this.last_user_updated_string = "";
    }
    public void sendMessage(String message) {
        if(this.local)
            this.localEmitter.sendBroadcast(message);
        this.obs.newMsgToSend(message);

    }
    public void askUpdate() {
        //ask to the other users to send their infos
        this.sendMessage("update");
    }
    public String getState(){
        return this.state;
    }
    public void stopperEmission(){
        this.emission = false;
    }
    public void disconnect(String id) {
        this.stopperEmission();
        String disconnected_str = String.format("%s,disconnected",id);
        this.sendMessage(disconnected_str);
        this.state = "disconnected";
    }
    private void diffuse(){
        this.sendMessage(last_user_updated_string);
    }

    public void diffuseNewUsername(String updatedUserString) {
        this.last_user_updated_string = updatedUserString;
        this.diffuse();
    }
    @Override
    public void run() {
        while(this.emission) {
            //System.out.println("I am in a loooooooooop");
            this.diffuse();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
