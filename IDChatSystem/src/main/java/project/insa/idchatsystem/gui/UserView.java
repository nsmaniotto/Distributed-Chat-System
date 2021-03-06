package project.insa.idchatsystem.gui;

import project.insa.idchatsystem.Observers.gui.Observables.UserViewObservable;
import project.insa.idchatsystem.Observers.gui.Observers.UserViewObserver;
import project.insa.idchatsystem.User.distanciel.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;

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
    protected static final Color COLOR_SELECTED = new Color(41, 148, 178);
    protected static final Color COLOR_NOTIFICATION = new Color(34, 167, 204);
    /* END: constants definitions */

    public UserView(User user) {
        super();
        this.user = user;
        this.priority = 0;//the most recent new connected user has a priority of 0 and it will grow  with time
        this.online = true;//as we detect them we consider that they are online
        this.initComponents();
        this.buildPanel();
        this.setMaximumSize(new Dimension(Short.MAX_VALUE,this.selectButton.getPreferredSize().height*2+10));
    }
    public void offline(){
        this.online=false;
        this.selectButton.setEnabled(false);
    }
    public void online() {
        this.online = true;
        this.selectButton.setEnabled(true);

    }
    public boolean getOnline() {
        return this.online;
    }
    protected void initComponents() {
        this.mainPanel = new JPanel();
        this.usernameLabel = new JLabel(String.format("%s #%s",this.user.get_username(),this.user.get_id_to_show()));
        this.usernameLabel.setToolTipText(this.user.get_id());
        this.selectButton = new JButton("Select");
    }

    protected void buildPanel() {
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        this.mainPanel.add(this.usernameLabel);
        this.mainPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        selectButton.setOpaque(false);
        this.mainPanel.add(selectButton);
        this.mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        this.add(mainPanel);

    }

    public void initListeners(UserViewObserver observer) {
        UserView parent = this;
        this.selectButton.addActionListener(e -> {
            parent.notificationSeen();
            //parent.mainPanel.setBackground(COLOR_SELECTED);
            parent.setOpaque(true);
            parent.userSelected();
        });
        this.observer = observer;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.printf(".(UserView.java:68) - mouseClicked : CALLED1\n");
                parent.notificationSeen();
                parent.userSelected();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
    public void notificationAvailable() {
        this.mainPanel.setBackground(COLOR_NOTIFICATION);
    }
    public void notificationSeen() {
        this.mainPanel.setOpaque(false);
        this.mainPanel.setBackground(new Color(0,0,0,0));
    }

    public void userSelected() {
        this.observer.startCommunicationWith(this);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUsername() {
        return this.user.get_username();
    }
    public Timestamp getLastSeen(){
        return this.user.get_lastSeen();
    }
    public void setUsername(String username){
        this.user.setUsername(username);
        this.usernameLabel.setText(String.format("%s #%s",this.user.get_username(),this.user.get_id_to_show()));
        this.usernameLabel.setToolTipText(this.user.get_id());
    }
    public void setLastSeen(Timestamp timestamp){
        this.user.setLastSeen(timestamp);
    }
    public String getId(){
        return this.user.get_id();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof UserView){
            UserView v = (UserView) o;
            return this.getId().equals(v.getId());
        } else {
            return false;
        }
    }
    public boolean fullEqual(Object o) {
        if(o instanceof UserView){
            UserView v = (UserView) o;
            return this.getUser().fullEquals(v.getUser()) && this.online == v.getOnline();
        } else {
            return false;
        }
    }


    public User getUser() {
        return user;
    }
    public void setUser(User u){
        this.user = u;
        this.usernameLabel.setText(String.format("%s #%s",this.user.get_username(),this.user.get_id_to_show()));
    }
}
