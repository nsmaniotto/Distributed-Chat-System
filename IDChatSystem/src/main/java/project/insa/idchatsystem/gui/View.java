package project.insa.idchatsystem.gui;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.Observers.ChatWindowObserver;
import project.insa.idchatsystem.User.distanciel.User;

public class View implements Runnable, ChatWindowObserver {
    private LoginWindow login_window;
    private ChatWindow chat_window;
    private ChatWindowObserver chatWindowObserver;
    
    @Override
    public void run() {
        this.login_window = new LoginWindow();
    }
    public void loginOk() {
        this.chat_window = new ChatWindow();
        this.chat_window.addViewObserver(this);
        this.chat_window.display();
    }
    public void offlineUser(User user){
        assert this.chat_window != null : "Vous n'êtes pas login";

    }

    public void onlineUser(User user){
        assert this.chat_window != null : "Vous n'êtes pas login";

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
    
    public void addChatWindowObserver(ChatWindowObserver observer) {
        this.chatWindowObserver = observer;
    }
    
    /* CHAT WINDOW OBSERVER METHODS */

    @Override
    public void newMessageSending(Message sendingMessage) {
        this.chatWindowObserver.newMessageSending(sendingMessage);
    }
}
