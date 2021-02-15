
package project.insa.idchatsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author nsmaniotto
 */
public abstract class Window extends JFrame {
    /* BEGIN: constants definitions */
    private static final String ICON_IMAGE_PATH = "/chatsystem_icon.png";
    protected static final Color COLOR_SOFTWHITE = new Color(236, 240, 241);
    /* END: constants definitions */
    
    /* BEGIN: graphical components declaration */
    protected JFrame frame = null;
    /* END: graphical components declaration */
    
    public Window(String title) {
        this.frame = new JFrame(title);
        
        this.setIconImage(ICON_IMAGE_PATH);
        this.initComponents();
        this.initListeners();
        this.buildFrame();
    }
    
    protected void initLookAndFeel() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
            System.err.println("Using the default look and feel.");
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
    }
    
    public void display() {
        //this.frame.pack(); // causes main frame to fit its content
        this.frame.setVisible(true);
    }

    private void setIconImage(String ICON_IMAGE_PATH) {
        URL iconURL = getClass().getResource(ICON_IMAGE_PATH);
        
        // iconURL is null when not found
        if(iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            this.frame.setIconImage(icon.getImage());
        } else {
            System.out.println("(Window) - Icon image not found");
        }
    }
    
    protected abstract void initComponents();
    protected abstract void initListeners();
    protected abstract void buildFrame();
}