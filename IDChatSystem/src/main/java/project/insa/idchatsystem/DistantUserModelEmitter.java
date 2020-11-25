package project.insa.idchatsystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class DistantUserModelEmitter implements Runnable{
    private String last_user_updated_string;
    private final String ip_server = User.calculate_current_ip();//BOF as a way to retrieve current ip regarding POO...
    private final int out_port_broadcast = 4003;
    private final PrintWriter out;
    public DistantUserModelEmitter() throws IOException {
        this.last_user_updated_string = "";
        //init in -> server
        Socket socket = new Socket(ip_server, out_port_broadcast);
        out = new PrintWriter(socket.getOutputStream(),true);
    }
    public void diffuseNewUsername(String updatedUserString) {
        this.last_user_updated_string = updatedUserString;
        this.diffuse();
    }
    private void diffuse(){
        out.println(last_user_updated_string);
    }
    @Override
    public void run() {
        while(true) {
            this.diffuse();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
