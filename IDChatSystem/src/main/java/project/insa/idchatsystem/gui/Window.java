/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.insa.idchatsystem.gui;

import javax.swing.JFrame;

/**
 *
 * @author nsmaniotto
 */
public abstract class Window extends JFrame {
    protected JFrame frame = null;
    
    public Window(String title) {
        this.frame = new JFrame(title);
        this.init();
        this.build();
    }
    
    public void display() {
        //this.frame.pack(); // causes main frame to fit its content
        this.frame.setVisible(true);
    }
    
    protected abstract void init();
    protected abstract void build();
}