
package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class LocalUserModel extends UserModel {
    private final LocalUserModelEmitter emitter;

    public LocalUserModel(int id)  {
        super(id);
        new Thread(new LocalUserModelReceiver(this)).start();
        this.emitter = new LocalUserModelEmitter();
    }
    @Override
    public void diffuseNewUsername(){
        String response = User.current_user_transfer_string();
        this.emitter.diffuseNewUsername(response);
    }

}
