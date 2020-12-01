package project.insa.idchatsystem.gui;

import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User;

public class View implements Runnable {
    private LoginWindow login_window;
    private ChatWindow chat_window;
    @Override
    public void run() {
        this.login_window = new LoginWindow();
    }
    public void loginOk() {
        this.chat_window = new ChatWindow();
    }
    public void offlineUser(User user){
        assert this.chat_window != null : "Vous n'êtes pas login";

    }

    public void onlineUser(User user){
        assert this.chat_window != null : "Vous n'êtes pas login";

    }
    public void newMessageRcv(Message message){
        assert this.chat_window != null : "Vous n'êtes pas login";

    }
    public void newMessageSent(Message message){
        assert this.chat_window != null : "Vous n'êtes pas login";

    }
}
