
package project.insa.idchatsystem;

class ClientController extends AgentController {
    private LocalUserModel localUserModel;
    private ClientView clientView;
    private DistantUserModel centralizedUserModel;
    
    public ClientController(int id) {
        this.localUserModel = new LocalUserModel(id);
    }
}
