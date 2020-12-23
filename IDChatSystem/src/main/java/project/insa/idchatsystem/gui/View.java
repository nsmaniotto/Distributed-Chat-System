package project.insa.idchatsystem.gui;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.ViewObservable;
import project.insa.idchatsystem.Observers.ViewObserver;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.HashMap;

import project.insa.idchatsystem.Observers.ChatWindowObserver;

public class View implements Runnable, ChatWindowObserver, ViewObservable {
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
            this.chat_window.displayUsername(login,User.get_current_id());
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
        this.chat_window.onlineUser(user);
    }
    public void offlineUser(User user) {
        assert this.chat_window != null : "Vous n'êtes pas login";
        System.out.printf("VIEW : offlineUser %s\n",user);
        this.chat_window.offlineUser(user);
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
    
    /**
     * Relay the notification treatment and display to the chat window
     * 
     * @param message : Message - message of which the current user must be notified
     */
    public void displayNotification(Message message) {
        assert this.chat_window != null : "You are not logged in";
        
        this.chat_window.displayNotification(message);
    }

    public void checkUsernameAvailable(String username){
        
    }
    
    /* CHAT WINDOW OBSERVER METHODS */

    @Override
    public void newMessageSending(Message sendingMessage) {

        this.viewObserver.newMessageSending(sendingMessage);
    }

    @Override
    public void userSelected(UserView userview) {
        this.viewObserver.userSelected(userview);
    }

    @Override
    public void askForMessages(User user) {
        this.notifyAskForMessage(user);
    }

    @Override
    public void notifyAskForMessage(User user) {
        this.viewObserver.askForMessages(user);
    }
    public void messagesToShow(ArrayList<Message> messages ) {
        this.chat_window.messagesToShow(messages);
    }
}
