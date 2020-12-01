package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.User;
import project.insa.idchatsystem.logins.UserModel;

import java.util.ArrayList;

public class LocalUserModel extends UserModel {
    private final LocalUserModelEmitter emitter;
    public LocalUserModel(int id, int receiver_port, int emitter_port, ArrayList<Integer> others)  {
        super(id);
        new Thread(new LocalUserModelReceiver(this,receiver_port)).start();
        this.emitter = new LocalUserModelEmitter(emitter_port,others);
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
        return this.checkLocallyAvailable(username);
    }
}
