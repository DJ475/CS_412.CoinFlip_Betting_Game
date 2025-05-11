
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewLeaderboard {
    private JList topEarnerJlist;
    private DefaultListModel<String> dlm = new DefaultListModel();
    private ArrayList<String> arrayList = new ArrayList<>();

    private JButton switchToPlay;

    ViewLeaderboard() {
        dlm = new DefaultListModel<>();
        this.topEarnerJlist = new JList(this.dlm);
        this.switchToPlay = new JButton("BACK TO GAME");
    }

    public JPanel MakeLeaderboard() {
        try {
            LeaderBoardModel leaderBoardModel = new LeaderBoardModel();
            JPanel panelContainer = new JPanel();

            panelContainer.setLayout(new BoxLayout(panelContainer, 1));
            JLabel labelLeaderboard = new JLabel("TOP 3 Players");
            this.topEarnerJlist.setFixedCellWidth(200);
            panelContainer.add(labelLeaderboard, "Center");
            panelContainer.add(this.topEarnerJlist, "Center");
            panelContainer.add(switchToPlay,"CENTER");


            return panelContainer;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public DefaultListModel<String> getDlm() {
        return this.dlm;
    }

    public JList getTopEarnerJlist() {
        return this.topEarnerJlist;
    }

    public void setDlm(DefaultListModel<String> dlm) {
        if (!dlm.isEmpty()) {
            this.dlm = dlm;
        } else {
            System.out.println("Model Cannot be Null");
        }

    }

    public void setALSwitchToPlay(ActionListener switchToPlayAL) {
        this.switchToPlay.addActionListener(switchToPlayAL);
    }
}
