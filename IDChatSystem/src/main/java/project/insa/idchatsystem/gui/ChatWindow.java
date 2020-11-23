package project.insa.idchatsystem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author nsmaniotto
 */
public class ChatWindow extends Window {
    /* BEGIN: variables declaration */
    private JPanel userPanel = null;
        private JPanel userInfoPanel = null;
            private JLabel usernameLabel = null;
            private JButton changeUsernameButton = null;
        private JTabbedPane conversationTabs = null;
            private JScrollPane recentConversationsTab = null;
            private JScrollPane onlineUsersTab = null;
            private JScrollPane offlineUsersTab = null;
            private JScrollPane allUsersTab = null;
    private JPanel chatPanel = null;
    /* END: variables declarations */
    
    public ChatWindow() {
        super("IDChat");
    }
    
    @Override
    protected void init() {        
        /* BEGIN: frame initialization */
        this.frame.setSize(800,600);
        this.frame.setLayout(new GridBagLayout());
        this.frame.setLocationRelativeTo(null);
        this.setResizable(false); // Not resizable for now
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initLookAndFeel();
        this.frame.getContentPane().setBackground(Window.COLOR_SOFTWHITE);
        /* END: frame initialization */
        
        /* BEGIN: variables initialization */
        this.userPanel = new JPanel(new GridBagLayout());
        
        this.userInfoPanel = new JPanel(new GridLayout(2,0));
        this.userInfoPanel.setBorder(BorderFactory.createEmptyBorder(
                10, //top
                10, //left
                10, //bottom
                10) //right
                );
        this.userInfoPanel.setBackground(Window.COLOR_SOFTWHITE);
        
        this.usernameLabel = new JLabel("AAAAA#xx", JLabel.LEFT);
        
        this.changeUsernameButton = new JButton();
        this.changeUsernameButton.setText("edit");
        
        this.conversationTabs = new JTabbedPane();
        this.conversationTabs.setPreferredSize(new Dimension(200, 500));
        //this.recentConversationsTab.setViewportView(this.recentConversationsTab);
        
        this.recentConversationsTab = new JScrollPane();
        this.onlineUsersTab = new JScrollPane();
        this.offlineUsersTab = new JScrollPane();
        this.allUsersTab = new JScrollPane();
        
        this.chatPanel = new JPanel();
        this.chatPanel.setPreferredSize(new Dimension(600, HEIGHT));
        this.chatPanel.setBackground(Window.COLOR_SOFTWHITE);
        /* END: variables initialization */
    }
    
    @Override
    protected void build() {
        /* BEGIN: userPanel build */
        GridBagConstraints userInfoPanelConstraints = new GridBagConstraints();
        userInfoPanelConstraints.gridx = 0;
        userInfoPanelConstraints.gridy = 0;
        userInfoPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        userInfoPanelConstraints.anchor = GridBagConstraints.NORTH;
        this.userPanel.add(this.userInfoPanel, userInfoPanelConstraints);
        this.userPanel.add(this.userInfoPanel, userInfoPanelConstraints);
        
        this.userInfoPanel.add(this.usernameLabel);
        this.userInfoPanel.add(this.changeUsernameButton);
        
        GridBagConstraints conversationTabsConstraints = new GridBagConstraints();
        conversationTabsConstraints.gridx = 0;
        conversationTabsConstraints.gridy = 1;
        conversationTabsConstraints.fill = GridBagConstraints.BOTH;
        conversationTabsConstraints.anchor = GridBagConstraints.SOUTH;
        this.userPanel.add(this.conversationTabs, conversationTabsConstraints);
        
        this.conversationTabs.addTab("Recent", this.recentConversationsTab);
        this.conversationTabs.addTab("Online", this.onlineUsersTab);
        this.conversationTabs.addTab("Offline", this.offlineUsersTab);
        //this.conversationTabs.addTab("All", this.allUsersTab); // Maybe later
        /* END: userPanel build */
        
        /* BEGIN: frame build */
        GridBagConstraints userPanelConstraints = new GridBagConstraints();
        userPanelConstraints.gridx = 0;
        userPanelConstraints.gridy = 0;
        userPanelConstraints.fill = GridBagConstraints.VERTICAL;
        this.frame.getContentPane().add(this.userPanel, userPanelConstraints);
        
        GridBagConstraints chatPanelConstraints = new GridBagConstraints();
        chatPanelConstraints.gridx = 1;
        chatPanelConstraints.gridy = 0;
        chatPanelConstraints.fill = GridBagConstraints.BOTH;
        this.frame.getContentPane().add(this.chatPanel, chatPanelConstraints);
        /* END: frame build */
    }
    
    public void displayUsername(String username, int id) {
        if(this.usernameLabel != null) {
            this.usernameLabel.setText(username + " #" + id);
        }
    }
}