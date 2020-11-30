package project.insa.idchatsystem.gui;

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
        private JPanel correspondentPanel = null;
            private JLabel correspondentInfoLabel = null;
        private JScrollPane chatScrollPane = null;
            private JPanel chatHistoryPanel = null;
        private JPanel chatFormPanel = null;
            private JTextField chatTextInputField = null;
            private JButton chatSendButton = null;
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
        this.conversationTabs.setPreferredSize(new Dimension(200, HEIGHT));
        //this.recentConversationsTab.setViewportView(this.recentConversationsTab);
        
        this.recentConversationsTab = new JScrollPane();
        this.onlineUsersTab = new JScrollPane();
        this.offlineUsersTab = new JScrollPane();
        this.allUsersTab = new JScrollPane();
        
        this.chatPanel = new JPanel(new GridBagLayout());
        this.chatPanel.setBorder(BorderFactory.createEmptyBorder(
                0, //top
                1, //left
                0, //bottom
                1) //right
                );
        
        this.chatPanel.setBackground(Color.LIGHT_GRAY/*Window.COLOR_SOFTWHITE*/);
        
        this.correspondentPanel = new JPanel();
        this.correspondentPanel.setBorder(BorderFactory.createEmptyBorder(
                10, //top
                10, //left
                10, //bottom
                10) //right
                );
        this.correspondentPanel.setBackground(Color.white);
        
        this.correspondentInfoLabel = new JLabel("BBBBB#yy", JLabel.LEFT);
        
        this.chatScrollPane = new JScrollPane();
        this.chatPanel.setBackground(Color.GRAY/*Window.COLOR_SOFTWHITE*/);
        
        this.chatHistoryPanel = new JPanel();
        this.chatHistoryPanel.setLayout(new BoxLayout(this.chatHistoryPanel, BoxLayout.Y_AXIS));
        
        this.chatFormPanel = new JPanel(new GridBagLayout());
        this.chatFormPanel.setBorder(BorderFactory.createEmptyBorder(
                10, //top
                10, //left
                10, //bottom
                10) //right
                );
        this.chatFormPanel.setBackground(Window.COLOR_SOFTWHITE);
        
        this.chatTextInputField = new JTextField();
        
        this.chatSendButton = new JButton("SEND");
            
        /* END: variables initialization */
    }
    
    @Override
    protected void build() {
        /* BEGIN: userPanel build */
        GridBagConstraints userInfoPanelConstraints = new GridBagConstraints();
        userInfoPanelConstraints.gridx = 0;
        userInfoPanelConstraints.weightx = 1.0;
        userInfoPanelConstraints.weighty = 0; // Fixed height
        userInfoPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        userInfoPanelConstraints.anchor = GridBagConstraints.NORTH;
        this.userPanel.add(this.userInfoPanel, userInfoPanelConstraints);
        
        this.userInfoPanel.add(this.usernameLabel);
        this.userInfoPanel.add(this.changeUsernameButton);
        
        GridBagConstraints conversationTabsConstraints = new GridBagConstraints();
        conversationTabsConstraints.gridx = 0;
        conversationTabsConstraints.gridy = 1;
        conversationTabsConstraints.weightx = 1.0;
        conversationTabsConstraints.weighty = 1.0;
        conversationTabsConstraints.fill = GridBagConstraints.BOTH;
        conversationTabsConstraints.anchor = GridBagConstraints.SOUTH;
        this.userPanel.add(this.conversationTabs, conversationTabsConstraints);
        
        this.conversationTabs.addTab("Recent", this.recentConversationsTab);
        this.conversationTabs.addTab("Online", this.onlineUsersTab);
        this.conversationTabs.addTab("Offline", this.offlineUsersTab);
        //this.conversationTabs.addTab("All", this.allUsersTab); // Maybe later
        /* END: userPanel build */
        
        /* BEGIN: chatPanel build */
        GridBagConstraints correspondentPanelConstraints = new GridBagConstraints();
        correspondentPanelConstraints.gridx = 0;
        correspondentPanelConstraints.weightx = 1.0;
        correspondentPanelConstraints.weighty = 0; // Fixed height
        correspondentPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        correspondentPanelConstraints.anchor = GridBagConstraints.NORTH;
        this.chatPanel.add(this.correspondentPanel, correspondentPanelConstraints);
        
        this.correspondentPanel.add(this.correspondentInfoLabel);
        
        GridBagConstraints chatScrollPaneConstraints = new GridBagConstraints();
        chatScrollPaneConstraints.gridx = 0;
        chatScrollPaneConstraints.gridy = 1;
        chatScrollPaneConstraints.weightx = 1.0;
        chatScrollPaneConstraints.weighty = 1.0;
        chatScrollPaneConstraints.fill = GridBagConstraints.BOTH;
        chatScrollPaneConstraints.anchor = GridBagConstraints.CENTER;
        this.chatPanel.add(this.chatScrollPane, chatScrollPaneConstraints);
        
        this.chatScrollPane.setViewportView(this.chatHistoryPanel);
        
        GridBagConstraints chatFormPanelConstraints = new GridBagConstraints();
        chatFormPanelConstraints.gridx = 0;
        chatFormPanelConstraints.gridy = 2;
        chatFormPanelConstraints.weightx = 1.0;
        chatFormPanelConstraints.weighty = 0; // Fixed height
        chatFormPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        chatFormPanelConstraints.anchor = GridBagConstraints.SOUTH;
        this.chatPanel.add(this.chatFormPanel, chatFormPanelConstraints);
        
        
        GridBagConstraints chatTextInputFieldConstraints = new GridBagConstraints();
        chatTextInputFieldConstraints.gridx = 0;
        chatTextInputFieldConstraints.weightx = 1.0;
        chatTextInputFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        chatTextInputFieldConstraints.anchor = GridBagConstraints.WEST;
        this.chatFormPanel.add(this.chatTextInputField, chatTextInputFieldConstraints);
        /* END: chatPanel build */
        
        /* BEGIN: frame build */
        GridBagConstraints userPanelConstraints = new GridBagConstraints();
        userPanelConstraints.gridx = 0;
        userPanelConstraints.gridy = 0;
        userPanelConstraints.weightx = 0.0; // Fixed width
        userPanelConstraints.weighty = 1.0;
        userPanelConstraints.fill = GridBagConstraints.BOTH;
        this.frame.getContentPane().add(this.userPanel, userPanelConstraints);
        
        GridBagConstraints chatPanelConstraints = new GridBagConstraints();
        chatPanelConstraints.gridx = 1;
        chatPanelConstraints.gridy = 0;
        chatPanelConstraints.weightx = 1.0;
        chatPanelConstraints.weighty = 1.0;
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