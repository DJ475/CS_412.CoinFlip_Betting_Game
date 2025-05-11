import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class View {
    private JFrame frameMain = new JFrame();
    private CardLayout cardLayoutOBJ = new CardLayout();
    private JPanel cardsDeck = new JPanel(cardLayoutOBJ);

    //    private JTabbedPane jtabs = new JTabbedPane();
    private ViewLeaderboard leaderBoardView = new ViewLeaderboard();
    private ViewGameplay gameplayView = new ViewGameplay();
    private ViewCreate createView = new ViewCreate();
    private ViewLogin loginView = new ViewLogin();
    private ViewDiceroll viewDiceroll = new ViewDiceroll();

    // Source: https://web.mit.edu/javadev/doc/tutorial/ui/layout/card.html
    // Source: https://www.sarthaks.com/3507672/how-do-i-switch-between-cards-using-cardlayout
    // Using this source we were able to dynamically show different cards based on actions
    // The card layout works by having multiple views within a JPanel parent that can call aliases like CREATE_VIEW
    // which then shows what view is associated with that alias.
    public void InitializeUI() {
        cardsDeck.add(loginView.MakeLogin(),"LOGIN_VIEW");
        cardsDeck.add(createView.MakeCreate(),"CREATE_VIEW");
        cardsDeck.add(gameplayView.MakeGameplay(),"COINFLIP_VIEW");
        cardsDeck.add(viewDiceroll.MakeDiceroll(),"DICEROLL_VIEW");
        cardsDeck.add(leaderBoardView.MakeLeaderboard(),"LEADER_VIEW");
        this.frameMain.add(cardsDeck);
        this.frameMain.setSize(600, 600);
        this.frameMain.setVisible(true);
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

    public CardLayout getCardLayoutOBJ() {
        return cardLayoutOBJ;
    }

    public JPanel getCardsDeck() {
        return cardsDeck;
    }

    public void setWindowListener(WindowAdapter wa)
    {
        frameMain.addWindowListener(wa);
    }

}

