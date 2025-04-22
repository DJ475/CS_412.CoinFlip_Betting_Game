
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class ViewLeaderboard {
    private JList topEarnerJlist;
    private DefaultListModel<String> dlm = new DefaultListModel();

    ViewLeaderboard() {
        this.topEarnerJlist = new JList(this.dlm);
    }

    public JPanel MakeLeaderboard() {
        JPanel panelContainer = new JPanel();
        this.dlm.addElement("Miles");
        this.dlm.addElement("DJ");
        panelContainer.setLayout(new BoxLayout(panelContainer, 1));
        JLabel labelLeaderboard = new JLabel("TOP 3 Players");
        this.topEarnerJlist.setFixedCellWidth(200);
        panelContainer.add(labelLeaderboard, "Center");
        panelContainer.add(this.topEarnerJlist, "Center");
        return panelContainer;
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

    public void setTopEarnerJlist(String[] newList) {
        if (newList.length != 0) {
            this.topEarnerJlist.setListData(newList);
        } else {
            System.out.println("List Cannot Be Empty");
        }

    }
}
