package project.insa.idchatsystem.logins;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class UserModelEmitter  implements Runnable{
    protected String last_user_updated_string;
    protected  boolean emission = true;
    protected String state = "connected";
    public void diffuseNewUsername(String updatedUserString) {
        this.last_user_updated_string = updatedUserString;
        this.diffuse();
    }
    abstract public void diffuse();
    public void stopperEmission(){
        this.emission = false;
    }
    @Override
    public void run() {
        while(this.emission) {
            //System.out.println("I am in a loooooooooop");
            this.diffuse();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
