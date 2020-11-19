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
    }
    
    public abstract void display();
}