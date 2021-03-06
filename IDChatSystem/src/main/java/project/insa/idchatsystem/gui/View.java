package project.insa.idchatsystem.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.gui.Observers.ViewObserver;
import project.insa.idchatsystem.User.distanciel.User;

import project.insa.idchatsystem.Observers.gui.Observers.ChatWindowObserver;

public class View implements Runnable, ChatWindowObserver {
    private LoginWindow login_window;
    private ChatWindow chat_window = null;
    private ViewObserver viewObserver;
    
    @Override
    public void run() {
        this.chat_window = new ChatWindow();
        this.chat_window.addChatWindowObserver(this);
        this.login_window = new LoginWindow();
        this.chat_window.setVisible(false);
        this.login_window.setLoginOKObserver(this);
        this.login_window.display();
    }
    public void loginOk(String login) {
        System.out.printf("Opening chat window\n");
        try {
            this.chat_window.displayUsername(login,User.get_current_id_to_show());
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
        this.chat_window.display();
    }
    public void addObserver(ViewObserver observer){
        this.viewObserver = observer;
    }
    public boolean setUsername(String login) {
        return this.viewObserver.newLogin(login);
    }

    public void onlineUser(User user){
        //Indicates that the user has been seen
        assert this.chat_window != null;
        //System.out.printf("VIEW onlineUser : Online user %s\n",user);
        if(this.chat_window != null) {
            this.chat_window.onlineUser(user);
        }
    }
    public void offlineUser(User user) {
        assert this.chat_window != null : "Vous n'êtes pas login";
        System.out.printf("VIEW : offlineUser %s\n",user);
        this.chat_window.offlineUser(user);
    }
    public void clearMessages() {
        this.chat_window.clearMessages();
    }
    /**
     * Relay the message treatment and display to the chat window
     * 
     * @param message : Message - message to be displayed
     */
    public void displayMessage(Message message) {
        assert this.chat_window != null : "You are not logged in";
        
        this.chat_window.displayMessage(message);
    }
    public void enableLoginTextField() {
        while(this.login_window == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.login_window.enableLoginField();
    }
    /**
     * Relay the notification treatment and display to the chat window
     * 
     * @param message : Message - message of which the current user must be notified
     */
    public void displayNotification(Message message) {
        assert this.chat_window != null : "You are not logged in";
        this.chat_window.displayNotification(message);
    }

    /* CHAT WINDOW OBSERVER METHODS */

    @Override
    public void newMessageSending(Message sendingMessage) {
        System.out.printf(".(View.java:82) : newMessageSending\n");
        this.viewObserver.newMessageSending(sendingMessage);
    }

    @Override
    public void userSelected(UserView userview) {
        this.viewObserver.userSelected(userview);
    }

    @Override
    public void closing() {
        this.viewObserver.closing();
    }
    @Override
    public void loginModify(String login) {
        try {
            this.chat_window.displayUsername(login,User.get_current_id_to_show());
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }

}
