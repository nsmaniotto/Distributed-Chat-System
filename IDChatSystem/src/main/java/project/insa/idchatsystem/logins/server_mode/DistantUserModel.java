package project.insa.idchatsystem.logins.server_mode;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.UserModel;

public class DistantUserModel  extends UserModel {
    private final DistantUserModelEmitter emitter;
    public DistantUserModel(int id) {
        super(id);
        new Thread(new DistantUserModelReceiver(this)).start();
        this.emitter = new DistantUserModelEmitter();
    }

    @Override
    public void disconnect() {
        try {
            this.emitter.disconnect(User.get_current_id());
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }

    @Override
    public boolean checkavailable(String username) {
        return super.checkLocallyAvailable(username);
    }

    public void diffuseNewUsername() {
        String response = User.current_user_transfer_string();
        this.emitter.diffuseNewUsername(response);

    }
    @Override
    public void stopperEmission(){
        this.emitter.stopperEmission();
    }
    //conflit table local données sur le réseau

    @Override
    public void addUserModelObserver(UsersStatusObserver obs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteUserModelObserver(UsersStatusObserver obs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyNewUserObservers(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyDisconnectedObservers(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
