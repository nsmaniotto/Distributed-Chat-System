package project.insa.idchatsystem.logins.local_mode.distanciel.Facades;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.logins.Observers.UserModelEmittersObserver;
import project.insa.idchatsystem.User.distanciel.User;
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

    /**
     * Allows to send login message to all the clients knows
     * @param message
     */
    public void sendMessage(String message) {
        if(this.local)
            this.localEmitter.sendBroadcast(message);
        this.obs.newMsgToSend(message);

    }

    /**
     * Asking update for logins to all users
     */
    public void askUpdate() {
        //ask to the other users to send their infos
        try {
            this.sendMessage(String.format("update,%s", User.get_current_id()));
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop login emission
     */
    public void stopperEmission(){
        this.emission = false;
    }

    /**
     * Stop current user, the emission of its informations and notify everyone
     * @param id
     */
    public void disconnect(String id) {
        this.stopperEmission();
        String disconnected_str = String.format("%s,disconnected",id);
        this.sendMessage(disconnected_str);
    }

    /**
     * Send last user logins informations
     */
    private void diffuse(){
        this.sendMessage(last_user_updated_string);
    }

    /**
     * Force the diffusion of new login informations
     * @param updatedUserString
     */
    public void diffuseNewUsername(String updatedUserString) {
        this.last_user_updated_string = updatedUserString;
        this.diffuse();
    }

    /**
     * Diffuse login informations periodically
     */
    @Override
    public void run() {
        while(this.emission) {
            //System.out.println("I am in a loooooooooop");
            this.diffuse();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
