package project.insa.idchatsystem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.ChatWindowObservable;
import project.insa.idchatsystem.Observers.ChatWindowObserver;
import project.insa.idchatsystem.User.distanciel.User;

/**
 *
 * @author nsmaniotto
 */
public class ChatWindow extends Window implements ActionListener, ChatWindowObservable {
    /* BEGIN: variables declaration */
    private JPanel userPanel;
        private JPanel userInfoPanel;
            private JLabel usernameLabel;
            private JButton changeUsernameButton;
        private JTabbedPane conversationTabs;
            private JScrollPane recentConversationsTab;
            private JScrollPane onlineUsersTab;
            private JScrollPane offlineUsersTab;
            private JScrollPane allUsersTab;
    private JPanel chatPanel;
        private JPanel correspondentPanel;
            private JLabel correspondentInfoLabel;
        private JScrollPane chatScrollPane;
            private JPanel chatHistoryPanel;
        private JPanel chatFormPanel;
            private JTextField chatTextInputField;
            private JButton chatSendButton;
    /* END: variables declarations */
    
    /* OBSERVERS */
    private ChatWindowObserver chatWindowObserver;
            
    public ChatWindow() {
        super("IDChat");
    }
    
    @Override
    protected void initComponents() {
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
        this.userPanel.setMinimumSize(new Dimension(200, HEIGHT));

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

        this.chatScrollPane = new JScrollPane(this.chatHistoryPanel);
        this.chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.chatScrollPane.setBackground(Color.GRAY/*Window.COLOR_SOFTWHITE*/);

        this.chatHistoryPanel = new ScrollableChat();
        this.chatHistoryPanel.setLayout(new BoxLayout(this.chatHistoryPanel, BoxLayout.Y_AXIS));
        this.chatHistoryPanel.setBorder(BorderFactory.createEmptyBorder(
                10, //top
                10, //left
                10, //bottom
                10) //right
                );

        this.chatFormPanel = new JPanel(new GridBagLayout());
        this.chatFormPanel.setBorder(BorderFactory.createEmptyBorder(
                10, //top
                10, //left
                10, //bottom
                10) //right
                );
        this.chatFormPanel.setBackground(Window.COLOR_SOFTWHITE);

        this.chatTextInputField = new JTextField();
        this.chatTextInputField.setToolTipText("Share your thoughts!");

        this.chatSendButton = new JButton("SEND");
        /* END: variables initialization */
    }
    
    @Override
    protected void initListeners() {
        ChatWindow chatWindowReference = this;
        
        // Chat input text field on ENTER
        this.chatTextInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                
                if(key == KeyEvent.VK_ENTER){
                    chatWindowReference.sendMessage();
                }
            }
        });
        
        // Chat send button on click
        this.chatSendButton.addActionListener(this);
    }
    
    @Override
    protected void buildFrame() {
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

        GridBagConstraints chatSendButtonConstraints = new GridBagConstraints();
        chatSendButtonConstraints.gridx = 1;
        chatSendButtonConstraints.weightx = 0.0;
        chatSendButtonConstraints.fill = GridBagConstraints.NONE;
        chatSendButtonConstraints.anchor = GridBagConstraints.EAST;
        this.chatFormPanel.add(this.chatSendButton, chatSendButtonConstraints);
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
    
    /**
     * Treat and display the message according to its data
     * 
     * @param message 
     */
    public void displayMessage(Message message) {
        //TODO Generate the graphical instance
        JPanel messageInstancePanel = this.generateDisplayedMessage(message);
        
        //TODO Add the instance to the display conversation
        this.chatHistoryPanel.add(messageInstancePanel);
        this.chatHistoryPanel.validate();
    }
    
    /**
     * Treat and display of a notification according to the given message
     * 
     * @param message : Message - message based on which the notification will be built
     * @deprecated - to be implemented
     */
    public void displayNotification(Message message) {
        //TODO
    }
    
    /**
     * Create a graphical instance which will be displayed, based on the given message
     * 
     * @param message : Message - message from which will be generated the graphical instance
     * @return corresponding displayed message
     */
    private JPanel generateDisplayedMessage(Message message) {
        JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.setBorder(BorderFactory.createEmptyBorder(
                10, //top
                5, //left
                10, //bottom
                5) //right
                );
        
        // text area
        JTextArea messageTextArea = new JTextArea();
        messageTextArea.setEnabled(false);
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        
        GridBagConstraints messageTextAreaConstraints = new GridBagConstraints();
        messageTextAreaConstraints.weightx = 0.7;
        messageTextAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
        
        // timestamp area
        JLabel messageTimestampLabel = new JLabel(message.getTimestamp());
        GridBagConstraints messageTimestampLabelConstraints = new GridBagConstraints();
        messageTimestampLabelConstraints.weightx = 0.3;
        messageTimestampLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
        
        // Check if this is an incoming or outgoing message
        try {
            if(message.getSource().equals(User.getCurrentUser())) {
                // Outgoing message
                messageTextAreaConstraints.gridx = 0; // Text at left
                messageTextAreaConstraints.anchor = GridBagConstraints.WEST;
                
                messageTimestampLabel.setBorder(BorderFactory.createEmptyBorder(
                0, //top
                20, //left
                0, //bottom
                0) //right
                );
                
                messageTimestampLabelConstraints.gridx = 1; // Timestamp at right
                messageTimestampLabelConstraints.anchor = GridBagConstraints.SOUTHEAST;
            } else {
                // Incoming message
                messageTextAreaConstraints.gridx = 1; // Text at right
                messageTextAreaConstraints.anchor = GridBagConstraints.EAST;
                
                messageTimestampLabel.setBorder(BorderFactory.createEmptyBorder(
                0, //top
                0, //left
                0, //bottom
                20) //right
                );
                
                messageTimestampLabelConstraints.gridx = 0; // Timestamp at left
                messageTimestampLabelConstraints.anchor = GridBagConstraints.SOUTHWEST;
            }
        } catch (Uninitialized e) {
            // Current user (thereforce message source) is not initialized
            System.out.println("ChatWindow: EXCEPTION WHILE COMPARING MESSAGE SOURCE " + e);
        }
        
        messageTextArea.setText(message.getText());
        
        messagePanel.add(messageTextArea, messageTextAreaConstraints);
        messagePanel.add(messageTimestampLabel, messageTimestampLabelConstraints);
        
        return messagePanel;
    }
    
    /* UTILITIES */
    
    private void sendMessage() {
        // Retrieve text from input
        String messageInputText = this.chatTextInputField.getText();

        boolean isMessageEmpty = messageInputText.isBlank(); // To be later modified to support file sending

        if(!isMessageEmpty) {
            // Create a message based on the retrieved text
            Message newMessage = new Message(messageInputText);

            // Clear text input
            this.chatTextInputField.setText("");

            // Notify the view that there is a new message to be sent
            this.notifyObserverSendingMessage(newMessage);
        }
    }
    
    /* ACTION LISTENER METHODS */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object sourceObject = event.getSource();
        
        if(sourceObject == this.chatSendButton) {
            this.sendMessage();
        }
    }
    
    /* CHAT WINDOW OBSERVABLE METHODS */

    @Override
    public void addChatWindowObserver(ChatWindowObserver observer) {
        this.chatWindowObserver = observer;
    }

    @Override
    public void deleteChatWindowObserver(ChatWindowObserver observer) {
        this.chatWindowObserver = null;
    }

    @Override
    public void notifyObserverSendingMessage(Message sentMessage) {
        if(this.chatWindowObserver != null) {
            this.chatWindowObserver.newMessageSending(sentMessage);
        }
    }
    
    class ScrollableChat extends JPanel implements Scrollable {
        public void ScrollableChat() {
            
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return super.getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 64;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 64;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}