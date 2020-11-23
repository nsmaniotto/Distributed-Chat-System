/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.insa.idchatsystem.gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author nsmaniotto
 */
public abstract class Window extends JFrame {
    /* BEGIN: constants definitions */
    protected static final Color COLOR_SOFTWHITE = new Color(236, 240, 241);
    /* END: constants definitions */
    
    /* BEGIN: graphical components declaration */
    protected JFrame frame = null;
    /* END: graphical components declaration */
    
    public Window(String title) {
        this.frame = new JFrame(title);
        this.init();
        this.build();
    }
    
    protected void initLookAndFeel() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
            System.err.println("Using the default look and feel.");
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
    }
    
    public void display() {
        //this.frame.pack(); // causes main frame to fit its content
        this.frame.setVisible(true);
    }
    
    protected abstract void init();
    protected abstract void build();
}