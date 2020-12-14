package project.insa.idchatsystem.gui;

import project.insa.idchatsystem.User.distanciel.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JPanel {
    private final User user ;
    private JPanel mainPanel;
        private JLabel usernameLabel;
        private JButton selectButton;
    /* BEGIN: constants definitions */
    protected static final Color COLOR_SOFTWHITE = new Color(236, 240, 241);
    /* END: constants definitions */

    public UserView(User user) {
        super();
        this.user = user;

        this.initComponents();
        this.initListeners();
        this.buildPanel();
    }

    protected void initComponents() {
        this.mainPanel = new JPanel();
        this.usernameLabel = new JLabel(String.format("%s #%d",this.user.get_username(),this.user.get_id()));
        this.selectButton = new JButton("Select");
    }

    protected void buildPanel() {
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        this.mainPanel.add(this.usernameLabel);
        this.mainPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        this.mainPanel.add(selectButton);
        this.mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        this.add(mainPanel);
    }

    protected void initListeners() {
        UserView parent = this;
        this.selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.printf("Utilisateur %s sélectionné\n",parent.user);
                //Action to open the conversation ...
            }
        });
    }
}
