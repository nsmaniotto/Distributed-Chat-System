package project.insa.idchatsystem.tools;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class TestPort {
    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     */
    public static boolean portIsavailable(int port) {
        if (port < 1024) {
            return false;
        }

        ServerSocket tcpTest = null;
        DatagramSocket UDPtes = null;
        try {
            tcpTest = new ServerSocket(port);
            tcpTest.setReuseAddress(true);
            UDPtes = new DatagramSocket(port);
            UDPtes.setReuseAddress(true);
            return true;
        } catch (Exception e) {}
        finally {
            if (UDPtes != null) {
                UDPtes.close();
            }
            if (tcpTest != null) {
                try {
                    tcpTest.close();
                } catch (IOException e) {}
            }
        }
        return false;
    }
}
