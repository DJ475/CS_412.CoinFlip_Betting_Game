import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionListener;

public class ViewGameplay {
    private JButton buttonBet = new JButton("Place Bet Now");
    private JButton buttonChangeGame;
    private BufferedImage picture;
    private TextField textFBetAmount;
    private TextField textResult;
    private TextField textEarnings;
    private JLabel pictureLabel;

    private JButton buttonSeeLeaderboard;

    private final String[] head_tailsOptions = new String[]{"heads", "tails"};
    JList<String> betOptionsSelect;

    ViewGameplay() {
//        try {
//            // Source: https://stackoverflow.com/questions/4871051/how-to-get-the-current-working-directory-in-java
//            //  System.getProperty("user.dir") allows the retrieving of the current working
//            //  directory path.
//            //  Setting Default image to be heads inside jlabel
//            System.out.println("Path is: " + System.getProperty("user.dir") + "\\Photos\\" + "heads.png");
//            this.picture = ImageIO.read(new File(System.getProperty("user.dir") + "\\Photos\\" + "heads.png"));
//        } catch (Exception var2) {
//            System.out.println("Picture Not Found");
//        }

        this.textFBetAmount = new TextField(15);
        this.textResult = new TextField(15);
        this.textEarnings = new TextField(15);
        this.buttonChangeGame = new JButton("Play DICEROLL?");
        this.buttonSeeLeaderboard = new JButton("SEE LEADERBOARD");
    }

    public JPanel MakeGameplay() {
        JPanel panelContainer = new JPanel();
        JPanel betPanel = new JPanel();
        JPanel outcomePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        betPanel.setLayout(new BoxLayout(betPanel, 1));
        outcomePanel.setLayout(new BoxLayout(outcomePanel, 1));
        this.textEarnings.setEditable(false);
        this.textResult.setEditable(false);

        betOptionsSelect = new JList<>(head_tailsOptions);
        betOptionsSelect.setFixedCellWidth(200);
        betPanel.add(this.textFBetAmount, "North");
        Component spaceAreaVert1 = Box.createRigidArea(new Dimension(20, 10));
        betPanel.add(spaceAreaVert1);
        betPanel.add(betOptionsSelect, "South");
        try
        {
//            this.pictureLabel = new JLabel(new ImageIcon(picture));
        }
        catch (Exception e)
        {
            System.out.println("Image Icon Not Found: " + e.getMessage());
            this.pictureLabel = new JLabel("Image Not Found ");
        }
//        outcomePanel.add(this.pictureLabel);
        Component spaceAreaVert3 = Box.createRigidArea(new Dimension(20, 10));
        outcomePanel.add(spaceAreaVert3);
        outcomePanel.add(this.textResult);
        Component spaceAreaVert2 = Box.createRigidArea(new Dimension(20, 10));
        outcomePanel.add(spaceAreaVert2);
        outcomePanel.add(this.textEarnings);
        Component spaceAreaVert4 = Box.createRigidArea(new Dimension(20, 10));
        outcomePanel.add(spaceAreaVert4);
        outcomePanel.add(buttonChangeGame, BorderLayout.CENTER);
        Component spaceAreaVert5 = Box.createRigidArea(new Dimension(20, 10));
        outcomePanel.add(spaceAreaVert5);
        outcomePanel.add(buttonSeeLeaderboard,BorderLayout.CENTER);
        this.buttonBet.setPreferredSize(new Dimension(250, 50));
        buttonPanel.add(this.buttonBet, "South");
        panelContainer.add(betPanel, "West");
        Component spaceArea = Box.createRigidArea(new Dimension(50, 20));
        panelContainer.add(spaceArea);
        panelContainer.add(outcomePanel, "East");
        panelContainer.add(buttonPanel, "South");
        return panelContainer;
    }

    public void setPicture(File imageResult) throws IOException {
        if (imageResult.exists()) {
            this.picture = ImageIO.read(imageResult);
        } else {
            System.out.println("Image for head/tails not found");
        }
    }

    public JLabel getPictureLabel()
    {
        return pictureLabel;
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public void setPictureLabel(ImageIcon imageSet) {
        pictureLabel.setIcon(imageSet);
    }

    public JList<String> getBetOptionsJlist() {
        return betOptionsSelect;
    }

    public void setBetOptionsListener(ListSelectionListener alOptions) {
        betOptionsSelect.addListSelectionListener(alOptions);
    }

    public TextField getTextFBetAmount() {
        return this.textFBetAmount;
    }

    public void setActionListenerButtonBet(ActionListener al) {
        this.buttonBet.addActionListener(al);
    }

    public TextField getTextResult() {
        return textResult;
    }

    public TextField getTextEarnings() {
        return textEarnings;
    }

    public void setActionListenerChangeGame(ActionListener alChangeGame) {
        this.buttonChangeGame.addActionListener(alChangeGame);
    }

    public void setALButtonSeeLeaderboard(ActionListener alLeaderboard) {
        this.buttonSeeLeaderboard.addActionListener(alLeaderboard);
    }

    public void clearText()
    {
        this.textFBetAmount.setText("");
        this.textResult.setText("");
        this.textEarnings.setText("");
    }
}

