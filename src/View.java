import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class View {
    private JFrame frameMain = new JFrame();
    private JTabbedPane jtabs = new JTabbedPane();
    private ViewLeaderboard leaderBoardView = new ViewLeaderboard();
    private ViewGameplay gameplayView = new ViewGameplay();

    public View() {
    }

    public void InitializeUI() {
        this.jtabs.add("Play", this.gameplayView.MakeGameplay());
        this.jtabs.add("Leaderboard", this.leaderBoardView.MakeLeaderboard());
        this.frameMain.add(this.jtabs);
        this.frameMain.setSize(600, 600);
        this.frameMain.setVisible(true);
    }

    public ViewLeaderboard getLeaderBoardView() {
        return this.leaderBoardView;
    }

    public ViewGameplay getGameplayView() {
        return this.gameplayView;
    }
}
