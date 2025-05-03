import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class View {
    private JFrame frameMain = new JFrame();
    private JTabbedPane jtabs = new JTabbedPane();
    private ViewLeaderboard leaderBoardView = new ViewLeaderboard();
    private ViewGameplay gameplayView = new ViewGameplay();
    private ViewCreate createView = new ViewCreate();
    private ViewLogin loginView = new ViewLogin();
    private ViewDiceroll viewDiceroll = new ViewDiceroll();

    public View()
    {

    }

    public void InitializeUI() {
        this.jtabs.add("Play Coin flip", this.gameplayView.MakeGameplay());
        this.jtabs.add("Play Dice", this.viewDiceroll.MakeDiceroll());
        this.jtabs.add("Leaderboard", this.leaderBoardView.MakeLeaderboard());
        this.jtabs.add("Create", this.createView.MakeCreate());
        this.jtabs.add("Login", this.loginView.MakeLogin());
        this.frameMain.add(this.jtabs);
        this.frameMain.setSize(600, 600);
        this.frameMain.setVisible(true);
        jtabs.setSelectedIndex(3);
        jtabs.setEnabledAt(0,false);
        jtabs.setEnabledAt(1,false);

    }

    public JTabbedPane getJtabs() {
        return jtabs;
    }

    public ViewLeaderboard getLeaderBoardView() {
        return this.leaderBoardView;
    }

    public ViewGameplay getGameplayView() {
        return this.gameplayView;
    }

    public ViewDiceroll getViewDiceroll() {
        return this.viewDiceroll;
    }

    public ViewLogin getLoginView() {
        return this.loginView;
    }

    public ViewCreate getCreateView() {
        return this.createView;
    }
}

