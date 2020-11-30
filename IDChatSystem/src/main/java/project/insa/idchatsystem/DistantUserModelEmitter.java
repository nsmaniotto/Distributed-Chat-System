package project.insa.idchatsystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class DistantUserModelEmitter extends UserModelEmitter {
    private String last_user_updated_string;
    private PrintWriter out = null;
    public DistantUserModelEmitter() {
        this.last_user_updated_string = "";
        //init in -> server
        int out_port_broadcast = 4003;
        //BOF as a way to retrieve current ip regarding POO...
        String ip_server = User.calculate_current_ip();
        try {
            Socket socket = new Socket(ip_server, out_port_broadcast);
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void diffuseNewUsername(String updatedUserString) {
        this.last_user_updated_string = updatedUserString;
        this.diffuse();
    }
    @Override
    public void diffuse(){
        assert out != null;
        out.println(last_user_updated_string);
    }
}
