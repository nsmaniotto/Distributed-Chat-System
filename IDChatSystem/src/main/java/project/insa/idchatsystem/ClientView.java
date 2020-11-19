/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.insa.idchatsystem;

import javax.swing.*;
import project.insa.idchatsystem.gui.ChatWindow;

/**
 *
 * @author nsmaniotto
 */
public class ClientView {
    private ChatWindow chatWindow = new ChatWindow();
    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> {
            initClientView();
        });
    }
    
    private static void initClientView() {
        //Set the look and feel.
        //initLookAndFeel();
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        //chatWindow.init();
    }
}