package project.insa.idchatsystem.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;

public class LoginWindow extends Window {
    private View observer;
    private JPanel loginPannel;
    private JLabel label;
    private JTextField loginTextField;

    public LoginWindow() {
        super("Login");
        this.setupInternalListeners();
    }

    @Override
    protected void init() {
        /* BEGIN: frame initialization */
        this.frame.setLayout(new GridBagLayout());
        this.frame.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initLookAndFeel();
        this.frame.getContentPane().setBackground(Window.COLOR_SOFTWHITE);
        this.frame.setMinimumSize(frame.getMinimumSize());
        /* END: frame initialization */
        /* BEGIN: variables initialization */
        this.loginPannel = new JPanel(new GridLayout(1,2));

        this.label = new JLabel("Please, choose a login :");

        GridBagConstraints c1 = new GridBagConstraints();
        this.loginTextField = new JTextField();
    }
    @Override
    protected void build() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.loginPannel.add(label,c);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 1;
        c1.gridy = 0;
        this.loginPannel.add(loginTextField,c1);
        this.loginPannel.setBorder(new EmptyBorder(10,10,10,10));
        this.frame.getContentPane().add(this.loginPannel);
        this.frame.pack();
        this.frame.setMinimumSize(frame.getMinimumSize());

    }
    public void setupInternalListeners(){
        Window parent = this;
        //Check if login is available and if so close the window and start the app
        this.loginTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key ==KeyEvent.VK_ENTER){
                    System.out.printf("PRESSED\n");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //On enter check if the username is not already taken
                            if(!observer.setUsername(loginTextField.getText())) {
                                JOptionPane.showMessageDialog(parent,"Error, this login is already taken");
                                loginTextField.setText("");
                            }
                            else {
                                loginOk();
                                frame.setVisible(false);
                            }
                        }
                    });
                }
            }
        });
    }
    public void setLoginOKObserver(View v) {
        this.observer = v;
    }
    public void loginOk(){
        this.observer.loginOk();
    }
}
