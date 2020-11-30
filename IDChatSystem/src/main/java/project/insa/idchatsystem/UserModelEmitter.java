package project.insa.idchatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

abstract class UserModelEmitter  implements Runnable{
    public void diffuseNewUsername(String updatedUserString) {
        this.diffuse();
    }
    abstract public void diffuse();
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
