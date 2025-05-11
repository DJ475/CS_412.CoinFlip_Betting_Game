import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

public class Controller {
    private Socket socket = null;
    private View view;

    private String chosenOptionH_TCoinFlip = "";
    private String chosenOptionH_TDiceRoll = "";

    private ObjectOutputStream objSendServer;

    private TreeMap<String, String> treeMapSendData;

    private String CurrentUser = "";

    public Controller(View view) {
        treeMapSendData = new TreeMap<>();
        this.view = view;
        view.InitializeUI();


        try {
            this.socket = new Socket("localhost", 5000);
            objSendServer = new ObjectOutputStream(socket.getOutputStream());
            // set up 2 way connection between server and client thread that's always is listening for server response
            Thread threadListen = new Thread(new ClientListener(socket,view));
            threadListen.start();
            view.getLoginView().getLoginStatusLabel().setText("SERVER FOUND");
        }
        catch (IOException ex)
        {
            view.getLoginView().getLoginStatusLabel().setText("Error Establishing Connection With Server: " + ex.getMessage());
        }

        view.getGameplayView().setActionListenerButtonBet(new ActionBetCoinFlip());
        view.getGameplayView().setBetOptionsListener(new ActionSelectOptions());

        view.getViewDiceroll().setActionListenerButtonBet(new ActionBetDiceRoll());
        view.getViewDiceroll().setBetOptionsListener(new ListenerDiceRollOptions());

        view.getLoginView().setActionListenerButtonLogin(new ActionLogin_ButtonL());
        view.getLoginView().setActionListenerButtonCreate(new ActionLogin_ButtonCreate());

        view.getCreateView().setActionListenerButtonCreate(new ActionCreate_ButtonC());
        view.getCreateView().setActionListenerButtonLogin(new ActionCreate_ButtonLogin());

        view.getGameplayView().setActionListenerChangeGame(new ActionListenerChangeGameDice());

        view.getViewDiceroll().setActionListenerChangeGame(new ActionListenerChangeGameCoinFlip());

        view.getGameplayView().setALButtonSeeLeaderboard(new ActionSeeLeaderboardButton());
        view.getViewDiceroll().setALButtonSeeLeaderboard2(new ActionSeeLeaderboardButton());

        view.getLeaderBoardView().setALSwitchToPlay(new ActionListenerChangeGameCoinFlip());

        view.setWindowListener(new windowListenerClosing());
    }

    public class ListenerDiceRollOptions implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            if(e.getValueIsAdjusting())
            {
                JList listSelectionDiceR = view.getViewDiceroll().getBetOptionsJlist();
                chosenOptionH_TDiceRoll = listSelectionDiceR.getSelectedValue().toString();
                System.out.println("Value Selected is: " + chosenOptionH_TDiceRoll);
            }
        }
    }

    public class ActionBetDiceRoll implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            TreeMap<String, String> treeMapDiceRoll = new TreeMap<>();
            String betAmountDiceRoll = view.getViewDiceroll().getTextFBetAmount().getText();

            treeMapDiceRoll.put("GAMEPLAY_BET_DICE",betAmountDiceRoll);
            treeMapDiceRoll.put("GUESS",chosenOptionH_TDiceRoll);

            try {
                objSendServer.writeObject(treeMapDiceRoll);
            } catch (IOException ex) {
                System.out.println("ERROR SENDING BETTING INFO");
            }
        }
    }

    public class ActionBetCoinFlip implements ActionListener {
        public ActionBetCoinFlip() {
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Sending to server");
            if (Controller.this.socket != null) {
                treeMapSendData = new TreeMap<>();
                String userInputBet = view.getGameplayView().getTextFBetAmount().getText();
                String guessCoinFlip = chosenOptionH_TCoinFlip;

                // tree map is container for users input, used to pass action being done as well as users bet amount
                treeMapSendData.put("GAMEPLAY_BET",userInputBet);
                treeMapSendData.put("GUESS",guessCoinFlip);

                try
                {
                    // Source: https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
                    // Using this source I was able to send the key and value pair over socket
                    objSendServer.writeObject(treeMapSendData);
                    treeMapSendData.clear();
                }
                catch (IOException ex)
                {
                    System.out.println("Error With Connection: " + ex.getMessage());
                }
            }
        }
    }

    public class ActionSelectOptions implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(e.getValueIsAdjusting())
            {
                JList listSelection = view.getGameplayView().getBetOptionsJlist();
                chosenOptionH_TCoinFlip = listSelection.getSelectedValue().toString();
                System.out.println("Value Selected is: " + chosenOptionH_TCoinFlip);
            }
        }
    }

    // This is the button in the creation view that switches to login view when pressed
    public class ActionCreate_ButtonLogin implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getCardLayoutOBJ().show(view.getCardsDeck(),"LOGIN_VIEW");
        }
    }

    // action done here is creating a new user.
    // This button click Action sends username and password to the server to verify if it can be used
    public class ActionCreate_ButtonC implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            treeMapSendData = new TreeMap<>();

            String usernameCreate = view.getCreateView().getUsernameEntry().getText();
            String passwordCreate = view.getCreateView().getPasswordEntry().getText();

            treeMapSendData.put("CREATE_ACCOUNT",usernameCreate);
            treeMapSendData.put("PASSWORD",passwordCreate);

            try {
                objSendServer.writeObject(treeMapSendData);
            }
            catch (NullPointerException npe)
            {
                System.out.println("ERROR Server Request: " + npe.getMessage());
            }
            catch (IOException ex)
            {
                System.out.println("Invalid Entry");
            }
            finally {
                treeMapSendData.clear();
            }
        }
    }

    // This is the button in the login view that switches to create view when pressed
    public class ActionLogin_ButtonCreate implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getCardLayoutOBJ().show(view.getCardsDeck(),"CREATE_VIEW");
        }
    }

    // action being done here is login of user using credentials username and password
    // This button click Action sends username and password to the server,
    // Which then verifies if user exists and if credentials are correct
    public class ActionLogin_ButtonL implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            treeMapSendData = new TreeMap<>();
            String usernameLogin = view.getLoginView().getUsernameEntry().getText();
            String passwordLogin = view.getLoginView().getPasswordEntry().getText();

            CurrentUser = usernameLogin;
            treeMapSendData.put("LOGIN_USER", CurrentUser);
            treeMapSendData.put("PASSWORD",passwordLogin);

            try
            {
                objSendServer.writeObject(treeMapSendData);
            }
            catch (IOException ex)
            {
                System.out.println("Error In Login Stage Please try Again: " + ex.getMessage());
            }
            catch (NullPointerException npe)
            {
                System.out.println("ERROR Server Request: " + npe.getMessage());
                System.out.println("Please make sure the server is connected");
            }
            finally {
                treeMapSendData.clear();
            }
        }
    }

    public class ActionListenerChangeGameDice implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getGameplayView().clearText();
            view.getCardLayoutOBJ().show(view.getCardsDeck(),"DICEROLL_VIEW");
        }
    }

    public class ActionListenerChangeGameCoinFlip implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.getViewDiceroll().clearText();
            view.getCardLayoutOBJ().show(view.getCardsDeck(),"COINFLIP_VIEW");
        }
    }

    public class ActionSeeLeaderboardButton implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Sending LeaderBoard Request");
            TreeMap<String, String> treeMapLeaderboard = new TreeMap<>();

            treeMapLeaderboard.put("LEADERBOARD","");
            treeMapLeaderboard.put("REQUEST","");

            try {
                objSendServer.writeObject(treeMapLeaderboard);
            } catch (IOException ex) {
                System.out.println("Error Asking for LEADERBOARD");
            }
            view.getCardLayoutOBJ().show(view.getCardsDeck(),"LEADER_VIEW");
        }
    }

    public class windowListenerClosing extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            TreeMap<String, String> closingTreemap = new TreeMap<>();

            if(!CurrentUser.isBlank())
            {
                closingTreemap.put("LOGOUT_USER", CurrentUser);
                closingTreemap.put("DISCONNECTED",CurrentUser);

                try
                {
                    objSendServer.writeObject(closingTreemap);

                }
                catch (IOException ex)
                {
                    view.getLoginView().getLoginStatusLabel().setText(ex.getMessage());
                }
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
            try {
                socket.close();
                objSendServer.close();
            } catch (IOException ex) {
                System.out.println("UNABLE TO CLOSE: " + ex.getMessage());
            }
        }
    }

}
