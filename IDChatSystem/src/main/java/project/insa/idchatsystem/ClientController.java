
package project.insa.idchatsystem;

class ClientController extends AgentController {
    private LocalUserModel localUserModel;
    private ClientView clientView;
    private LocalUserModel centralizedUserModel;
    
    public ClientController(int id) {
        this.localUserModel = new LocalUserModel(id);
    }
}
