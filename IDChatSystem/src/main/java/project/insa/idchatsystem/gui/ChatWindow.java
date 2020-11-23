package project.insa.idchatsystem.gui;

import java.awt.BorderLayout;
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
        this.frame.setLayout(new BorderLayout());
        this.frame.setLocationRelativeTo(null);
        this.setResizable(false); // Not resizable for now
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initLookAndFeel();
        /* END: frame initialization */
        
        /* BEGIN: variables initialization */
        this.userPanel = new JPanel();
        this.userPanel.setPreferredSize(new Dimension(200, HEIGHT));
        this.userPanel.setBackground(new java.awt.Color(23, 237, 237));
        
        this.userInfoPanel = new JPanel();
        this.userInfoPanel.setLayout(new GridLayout(2,0));
        this.userInfoPanel.setMaximumSize(new Dimension(200, HEIGHT));
        this.userInfoPanel.setBackground(new java.awt.Color(227, 250, 252));
        
        this.usernameLabel = new JLabel("AAAAA#xx", JLabel.LEFT);
        
        this.changeUsernameButton = new JButton();
        this.changeUsernameButton.setText("edit");
        
        this.conversationTabs = new JTabbedPane();
        this.conversationTabs.setPreferredSize(new Dimension(200, 525));
        this.conversationTabs.setBackground(new java.awt.Color(153, 153, 255));
        //this.recentConversationsTab.setViewportView(this.recentConversationsTab);
        
        this.recentConversationsTab = new JScrollPane();
        this.onlineUsersTab = new JScrollPane();
        this.offlineUsersTab = new JScrollPane();
        this.allUsersTab = new JScrollPane();
        
        this.chatPanel = new JPanel();
        this.chatPanel.setPreferredSize(new Dimension(600, HEIGHT));
        this.chatPanel.setBackground(new java.awt.Color(202, 246, 250));
        /* END: variables initialization */
    }
    
    @Override
    protected void build() {
        /* BEGIN: userPanel build */
        this.userPanel.add(this.userInfoPanel, BorderLayout.NORTH);
        
        this.userInfoPanel.add(this.usernameLabel);
        this.userInfoPanel.add(this.changeUsernameButton);
        
        
        this.userPanel.add(this.conversationTabs);
        
        this.conversationTabs.addTab("Recent", this.recentConversationsTab);
        this.conversationTabs.addTab("Online", this.onlineUsersTab);
        this.conversationTabs.addTab("Offline", this.offlineUsersTab);
        //this.conversationTabs.addTab("All", this.allUsersTab); // Maybe later
        /* END: userPanel build */
        
        /* BEGIN: frame build */
        this.frame.add(this.userPanel,BorderLayout.WEST);
        this.frame.add(this.chatPanel,BorderLayout.EAST);
        /* END: frame build */
    }
    
    public void displayUsername(String username, int id) {
        if(this.usernameLabel != null) {
            this.usernameLabel.setText(username + " #" + id);
        }
    }
}