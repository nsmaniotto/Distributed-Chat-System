/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.insa.idchatsystem.gui;

import javax.swing.*;

/**
 *
 * @author nsmaniotto
 */
public class ChatWindow extends Window {
    /* BEGIN: variables declaration */
    private JPanel userPanel = null;
    private JPanel chatPanel = null;
    /* END: variables declarations */
    
    public ChatWindow() {
        super("IDChat");
    }
    
    @Override
    protected void init() {
        /* BEGIN: frame initialization */
        this.frame.setSize(600,400);
        this.frame.setLocationRelativeTo(null);
        this.setResizable(false); // Not resizable for now
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* END: frame initialization */
        
        /* BEGIN: variables initialization */
        
        /* END: variables initialization */
    }
}