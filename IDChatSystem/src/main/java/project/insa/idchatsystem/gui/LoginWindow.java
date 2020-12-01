package project.insa.idchatsystem.gui;

import java.util.Observable;

public class LoginWindow extends Observable {
    private View observer;

    public void setLoginOKObserver(View v) {
        this.observer = v;
    }
    public void loginOk(){
        this.observer.loginOk();
    }
}
