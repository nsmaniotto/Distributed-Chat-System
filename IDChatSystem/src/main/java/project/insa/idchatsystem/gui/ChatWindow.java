/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.insa.idchatsystem.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
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
        this.frame.setSize(800,600);
        this.frame.setLayout(new BorderLayout());
        this.frame.setLocationRelativeTo(null);
        this.setResizable(false); // Not resizable for now
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* END: frame initialization */
        
        /* BEGIN: variables initialization */
        this.userPanel = new JPanel();
        this.userPanel.setPreferredSize(new Dimension(200, HEIGHT));
        this.userPanel.setBackground(new java.awt.Color(23, 237, 237));
        
        this.chatPanel = new JPanel();
        this.chatPanel.setPreferredSize(new Dimension(600, HEIGHT));
        this.chatPanel.setBackground(new java.awt.Color(202, 246, 250));
        /* END: variables initialization */
    }
    
    @Override
    protected void build() {
        this.frame.add(this.userPanel,BorderLayout.WEST);
        this.frame.add(this.chatPanel,BorderLayout.EAST);
    }
}