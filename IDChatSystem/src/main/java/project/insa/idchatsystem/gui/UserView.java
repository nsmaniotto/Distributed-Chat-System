package project.insa.idchatsystem.gui;

import project.insa.idchatsystem.Observers.UserViewObservable;
import project.insa.idchatsystem.Observers.UserViewObserver;
import project.insa.idchatsystem.User.distanciel.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
//TODO : s'inspirer de generateDisplayedMessage pour régler le problème de taille
public class UserView extends JPanel implements UserViewObservable {
    private User user ;
    private JPanel mainPanel;
        private JLabel usernameLabel;
        private JButton selectButton;
    private int priority;
    private UserViewObserver observer;
    private boolean online;
    /* BEGIN: constants definitions */
    protected static final Color COLOR_SOFTWHITE = new Color(236, 240, 241);
    /* END: constants definitions */

    public UserView(User user) {
        super();
        this.user = user;
        this.priority = 0;//the most recent new connected user has a priority of 0 and it will grow  with time
        this.online = true;//as we detect them we consider that they are online
        this.initComponents();
        this.buildPanel();
    }
    public void offline(){this.online=false;}
    public boolean getOnline() {
        return this.online;
    }
    //TODO change colors to notify new message
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

    public void initListeners(UserViewObserver observer) {
        UserView parent = this;
        this.selectButton.addActionListener(e -> {
            System.out.printf("Utilisateur %s sélectionné\n",parent.user);
            this.mainPanel.setBackground(Color.RED);
            this.setOpaque(true);

            System.out.printf("%s\n",this.getBackground());
            //Action to open the conversation ...
        });
    }
    public void notificationAvailable() {
        this.mainPanel.setBackground(Color.GREEN);
    }
    public void notificationSeen() {
        this.setOpaque(false);
    }
    public void userSelected() {
        this.observer.userSelected(this);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void userObserved() {
        this.priority ++;
    }
    public String getUsername() {
        return this.user.get_username();
    }
    public Timestamp getLastSeen(){
        return this.user.get_lastSeen();
    }
    public void setUsername(String username){
        this.user.setUsername(username);
        this.usernameLabel.setText(String.format("%s #%d",this.user.get_username(),this.user.get_id()));
    }
    public void setLastSeen(Timestamp timestamp){
        this.user.setLastSeen(timestamp);
    }
    public int getId(){
        return this.user.get_id();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof UserView){
            UserView v = (UserView) o;
            return this.getId()==v.getId();
        } else {
            return false;
        }
    }

}
