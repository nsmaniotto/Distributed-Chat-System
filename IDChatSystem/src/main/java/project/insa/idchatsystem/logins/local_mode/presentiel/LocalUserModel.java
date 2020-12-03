
package project.insa.idchatsystem.logins.local_mode.presentiel;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.UsersStatusObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.UserModel;

public class LocalUserModel extends UserModel {
    private final LocalUserModelEmitter emitter;

    public LocalUserModel(int id)  {
        super(id);
        new Thread(new LocalUserModelReceiver(this)).start();
        this.emitter = new LocalUserModelEmitter();
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
    public void diffuseNewUsername(){
        String response = User.current_user_transfer_string();
        this.emitter.diffuseNewUsername(response);
    }
    @Override
    public void stopperEmission(){
        this.emitter.stopperEmission();
    }

    @Override
    public boolean checkavailable(String username) {
        return this.checkLocallyAvailable(username);
    }

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