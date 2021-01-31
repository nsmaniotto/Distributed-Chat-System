/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idchatsystem.simulation.multiple;

import project.insa.idchatsystem.Exceptions.NoPortAvailable;

public class MainClientTest1 {
    public static void main(String [] argv) throws NoPortAvailable {
        ClientTest clientTest1 = new ClientTest("10", 2000, 2010, 2001);
    }
}
