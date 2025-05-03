
import com.sun.source.tree.Tree;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

public class Controller {
    private Socket socket = null;
    private View view;

    private String chosenOptionH_T = "";

    private ObjectOutputStream objSendServer;

    private TreeMap<String, String> treeMapSendData;

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
        }
        catch (IOException ex)
        {
            System.out.println("Error Establishing Connection With Server: " + ex.getMessage());
        }

        view.getGameplayView().setActionListenerButtonBet(new ActionBet());
        view.getGameplayView().setBetOptionsListener(new ActionSelectOptions());
        view.getLoginView().setActionListenerButtonLogin(new ActionListenerLogin());
        view.getCreateView().setActionListenerButtonCreate(new ActionListenerCreate());
        view.getJtabs().addChangeListener(new ActionChangeTab());

    }

    public class ActionBet implements ActionListener {
        public ActionBet() {
        }

        public void actionPerformed(ActionEvent e) {
            if (Controller.this.socket != null) {
                treeMapSendData = new TreeMap<>();
                String userInputBet = view.getGameplayView().getTextFBetAmount().getText();
                String guessCoinFlip = chosenOptionH_T;



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
                chosenOptionH_T = listSelection.getSelectedValue().toString();
                System.out.println("Value Selected is: " + chosenOptionH_T);
            }
        }
    }

    public class ActionListenerCreate implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            treeMapSendData = new TreeMap<>();
            view.getJtabs().setSelectedIndex(3);

            view.getJtabs().setSelectedIndex(3); // change login tab
            // disable create tabe
            view.getJtabs().setEnabledAt(0,false);
            view.getJtabs().setEnabledAt(1,false);
            view.getJtabs().setEnabledAt(2,false);
            view.getJtabs().setEnabledAt(3,true);


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

    public class ActionListenerLogin implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getJtabs().setSelectedIndex(0);
            view.getJtabs().setEnabledAt(0,true);
            view.getJtabs().setEnabledAt(1,true);
            view.getJtabs().setEnabledAt(2,true);
            view.getJtabs().setEnabledAt(3,true);

            treeMapSendData = new TreeMap<>();
            String usernameLogin = view.getLoginView().getUsernameEntry().getText();
            String passwordLogin = view.getLoginView().getPasswordEntry().getText();


            treeMapSendData.put("LOGIN_USER",usernameLogin);
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

    public class ActionChangeTab implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e) {
            // Source: http://www.java2s.com/Tutorial/Java/0240__Swing/ListeningforSelectedTabChanges.htm
            // Using this source to be able to see if they switch to the leaderboard tab to send a request to the server to get the leaderboard.
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));


                switch (sourceTabbedPane.getTitleAt(index))
                {
                    case "Play"->
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
                    }
                    case "Leaderboard"->
                    {
                        TreeMap<String, String> treeMapLeaderboard = new TreeMap<>();

                        treeMapLeaderboard.put("LEADERBOARD","");
                        treeMapLeaderboard.put("REQUEST","");

                        try {
                            objSendServer.writeObject(treeMapLeaderboard);
                        } catch (IOException ex) {
                            System.out.println("Error Asking for LEADERBOARD");
                        }
                    }

            }
        }
    }
}
