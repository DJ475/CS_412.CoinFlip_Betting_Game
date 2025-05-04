import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ViewCreate {
    private JButton buttonCreate;
    private JButton buttonLogin;
    private JLabel createLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel textEntry;
    private JPanel buttonEntry;
    private JPanel panelContainer;
    private JFrame jFrame;
    private JTextField usernameEntry;
    private JTextField passwordEntry;

    private JLabel createStatusLabel;

    ViewCreate() {
        this.buttonCreate = new JButton("Create Account");
        this.buttonLogin = new JButton("Already Have An Account?: Login Instead");
        this.createLabel = new JLabel("Create Your Account or Select Login");
        this.createStatusLabel = new JLabel();
        this.usernameLabel = new JLabel("Username");
        this.passwordLabel = new JLabel("Password");
        this.textEntry = new JPanel();
        this.buttonEntry = new JPanel();
        this.panelContainer = new JPanel();
        this.jFrame = new JFrame();
        this.usernameEntry = new JTextField(15);
        this.passwordEntry = new JTextField(15);
    }

    public JPanel MakeCreate() {
        //set layout for our 2 Jpanels
        textEntry.setLayout(new BoxLayout(textEntry, 1));
        buttonEntry.setLayout(new BoxLayout(buttonEntry, 1));

        //add our items to our first Jpanel
        textEntry.add(createLabel, BorderLayout.SOUTH);
        textEntry.add(createStatusLabel, BorderLayout.NORTH);
        textEntry.add(usernameLabel, BorderLayout.CENTER);
        textEntry.add(usernameEntry, BorderLayout.CENTER);
        textEntry.add(passwordLabel, BorderLayout.CENTER);
        textEntry.add(passwordEntry, BorderLayout.CENTER);

        //add items to our second jpanel (login and create button)
        buttonEntry.add(buttonCreate, BorderLayout.SOUTH);
        buttonEntry.add(buttonLogin,BorderLayout.NORTH);

        //panel container to hold our 2 main panels
        panelContainer.add(textEntry, BorderLayout.NORTH);
        panelContainer.add(buttonEntry, BorderLayout.SOUTH);

        Component spaceArea = Box.createRigidArea(new Dimension(50, 20));
        panelContainer.add(spaceArea);

        return panelContainer;
    }


    public JTextField getUsernameEntry() {
        return this.usernameEntry;
    }

    public JTextField getPasswordEntry() {
        return this.passwordEntry;
    }

    public JLabel getCreateStatusLabel() {
        return createStatusLabel;
    }

    public void setActionListenerButtonCreate(ActionListener al) {
        this.buttonCreate.addActionListener(al);
    }

    public void setActionListenerButtonLogin(ActionListener al) {
        this.buttonLogin.addActionListener(al);
    }


}
