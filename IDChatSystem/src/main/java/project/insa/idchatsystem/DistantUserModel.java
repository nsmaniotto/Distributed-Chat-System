package project.insa.idchatsystem;

public class DistantUserModel  extends UserModel {
    private final DistantUserModelEmitter emitter;
    public DistantUserModel(int id) {
        super(id);
        new Thread(new DistantUserModelReceiver(this)).start();
        this.emitter = new DistantUserModelEmitter();
    }
    public void diffuseNewUsername() {
        String response = User.current_user_transfer_string();
        this.emitter.diffuseNewUsername(response);

    }

    @Override
    public boolean checkAvailable(String username) {
        return super.checkAvailable(username);
    }
}
