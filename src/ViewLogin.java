import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ViewLogin {
    private JButton buttonCreate;
    private JButton buttonLogin;
    private JLabel loginLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel textEntry;
    private JPanel buttonEntry;
    private JPanel panelContainer;
    private JFrame jFrame;
    private JTextField usernameEntry;
    private JTextField passwordEntry;

    private JLabel loginStatusLabel;


    ViewLogin() {
        this.buttonLogin = new JButton("Login");
        this.buttonCreate = new JButton("Don't Have an Account? -> Create Account");
        this.loginLabel = new JLabel("Login To Your Account or Select Create Account");
        this.loginStatusLabel = new JLabel();
        this.usernameLabel = new JLabel("Username");
        this.passwordLabel = new JLabel("Password");
        this.textEntry = new JPanel();
        this.buttonEntry = new JPanel();
        this.panelContainer = new JPanel();
        this.jFrame = new JFrame();
        this.usernameEntry = new JTextField(15);
        this.passwordEntry = new JTextField(15);
    }

    public JPanel MakeLogin() {
        //set layout for our 2 Jpanels
        textEntry.setLayout(new BoxLayout(textEntry, 1));
        buttonEntry.setLayout(new BoxLayout(buttonEntry, 1));

        //add our items to our first Jpanel
        textEntry.add(loginLabel, BorderLayout.NORTH);
        textEntry.add(loginStatusLabel,BorderLayout.SOUTH);

        textEntry.add(usernameLabel, BorderLayout.CENTER);
        textEntry.add(usernameEntry, BorderLayout.CENTER);
        textEntry.add(passwordLabel, BorderLayout.CENTER);
        textEntry.add(passwordEntry, BorderLayout.CENTER);

        //add items to our second jpanel (login and create button)
        buttonEntry.add(buttonLogin, BorderLayout.SOUTH);
        buttonEntry.add(buttonCreate,BorderLayout.NORTH);

        //panel container to hold our 2 main panels
        panelContainer.add(textEntry, BorderLayout.NORTH);
        panelContainer.add(buttonEntry, BorderLayout.SOUTH);

        Component spaceArea = Box.createRigidArea(new Dimension(50, 20));
        panelContainer.add(spaceArea);

        return panelContainer;
    }

    public void setActionListenerButtonLogin(ActionListener al) {
        this.buttonLogin.addActionListener(al);
    }

    public void setActionListenerButtonCreate(ActionListener al) {
        this.buttonCreate.addActionListener(al);
    }

    public JTextField getUsernameEntry() {
        return this.usernameEntry;
    }

    public JTextField getPasswordEntry() {
        return this.passwordEntry;
    }

    public JLabel getLoginStatusLabel() {
        return loginStatusLabel;
    }
}
