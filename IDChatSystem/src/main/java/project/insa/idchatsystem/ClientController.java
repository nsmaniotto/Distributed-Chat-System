
package project.insa.idchatsystem;

import project.insa.idchatsystem.logins.server_mode.DistantUserModel;
import project.insa.idchatsystem.logins.local_mode.presentiel.LocalUserModel;

public class ClientController extends AgentController {
    private LocalUserModel localUserModel;
    private ClientView clientView;
    private DistantUserModel centralizedUserModel;
    
    public ClientController(int id) {

        this.localUserModel = new LocalUserModel(id);
    }
}
