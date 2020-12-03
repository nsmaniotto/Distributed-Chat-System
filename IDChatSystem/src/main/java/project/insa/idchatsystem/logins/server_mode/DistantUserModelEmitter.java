package project.insa.idchatsystem.logins.server_mode;

import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.UserModelEmitter;

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
    public void disconnect(int id){
        assert out != null;
        String disconnected_str = String.format("%d,disconnected",id);
        out.println(disconnected_str);
    }
    @Override
    public void diffuse(){
        assert out != null;
        out.println(last_user_updated_string);
    }
}
