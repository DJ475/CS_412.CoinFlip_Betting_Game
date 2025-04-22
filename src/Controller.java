
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

public class Controller {
    Socket socket = null;
    View view;

    private String chosenOptionH_T = "";

    ObjectOutputStream objSendServer;

    public Controller(View view) {
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
    }

    public class ActionBet implements ActionListener {
        public ActionBet() {
        }

        public void actionPerformed(ActionEvent e) {
            if (Controller.this.socket != null) {
                String userInputBet = view.getGameplayView().getTextFBetAmount().getText();
                String guessCoinFlip = chosenOptionH_T;

                TreeMap<String, String> treeMapBet = new TreeMap<>();

                // tree map is container for users input, used to pass action being done as well as users bet amount
                treeMapBet.put("GAMEPLAY_BET",userInputBet);
                treeMapBet.put("GUESS",guessCoinFlip);

                try
                {
                    // Source: https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
                    // Using this source I was able to send the key and value pair over socket
                    objSendServer.writeObject(treeMapBet);
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
}
