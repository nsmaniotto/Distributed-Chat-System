/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idchatsystem.simulation.multiple;

import project.insa.idchatsystem.Exceptions.NoPortAvailable;

public class MainClientTest2 {
    public static void main(String [] argv) throws NoPortAvailable {
        ClientTest clientTest2 = new ClientTest("20", 2001, 2011, 2000);
    }
}
