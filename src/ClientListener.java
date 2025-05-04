import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class ClientListener implements Runnable {
    private Socket listeningSocket;
    private View view;

    private String[] objData;

    private DefaultListModel<String> dlmListener;

    ObjectInputStream objectInputStream;

    private CardLayout cardOBJ;

    private MessageClass mc;

    public ClientListener(Socket connectionListen, View view) throws IOException {
        dlmListener = new DefaultListModel();
        this.listeningSocket = connectionListen;
        this.view = view;
        objectInputStream = new ObjectInputStream(listeningSocket.getInputStream());
        mc = new MessageClass();
        cardOBJ = view.getCardLayoutOBJ();
    }

    @Override
    public void run() {
        try {
            Object readInObj;
            while (true) {
                String serverResponse = null;

//                ArrayList<LeaderBoard> leaderBoardObj = (ArrayList<LeaderBoard>) objectInputStream.readObject();
//                System.out.println("OBJECT IS NOW: " + objectInputStream.readObject());

                readInObj = objectInputStream.readObject();

                System.out.println("CLASS IS NOW: " + readInObj.getClass().getTypeName());


                switch (readInObj.getClass().getTypeName())
                {
                    case "java.util.ArrayList"->
                    {
                        System.out.println("Found class array list");

                        ArrayList<LeaderBoard> leaderBoardArrayList = null;
                        try
                        {
                            leaderBoardArrayList = (ArrayList<LeaderBoard>) readInObj;

                            dlmListener = view.getLeaderBoardView().getDlm();
                            dlmListener.clear();

                            for (LeaderBoard leader : leaderBoardArrayList) {
//                                String usernameDisplay = leader.getUsername();
//                                double earningsDisplay = leader.getEarnings();;
//                                System.out.println("Leader is now: " + usernameDisplay + " has " + earningsDisplay);

                                dlmListener.addElement(leader.toString());
                            }
                            view.getLeaderBoardView().setDlm(dlmListener);
                            view.getLeaderBoardView().getTopEarnerJlist().setListData(dlmListener.toArray());
                        }
                        catch (Exception e)
                        {
                            System.out.println("Error:" + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    case "MessageClass"->
                    {
                        System.out.println("Message Found");
                        MessageClass Message = (MessageClass) readInObj;
                        System.out.println(Message.toString());

                        // this looks for 4 colons and skips them in parsing the value sent from the server
                        int valueMessageIndex = Message.toString().indexOf("::::")+4; // this lets our program know its a message from server

                        if(Message.toString().startsWith("ERROR_BET_COIN"))
                        {
                            view.getGameplayView().getTextResult().setText(Message.toString().substring(valueMessageIndex));
                        }
                        if(Message.toString().startsWith("ERROR_BET_DICE"))
                        {
                            view.getViewDiceroll().getTextResult().setText(Message.toString().substring(valueMessageIndex));
                        }
                        if(Message.toString().startsWith("LOGIN_SUCCESS"))
                        {
                            cardOBJ.show(view.getCardsDeck(),"COINFLIP_VIEW");
                        }
                        if(Message.toString().startsWith("EARNINGS_COIN"))
                        {
                            if(valueMessageIndex!=-1 && !Message.toString().isEmpty())
                            {
                                view.getGameplayView().getTextEarnings().setText(Message.toString().substring(valueMessageIndex));
                            }
                        }
                        if(Message.toString().startsWith("EARNINGS_DICE"))
                        {
                            if(valueMessageIndex!=-1 && !Message.toString().isEmpty())
                            {
                                view.getViewDiceroll().getTextEarnings().setText(Message.toString().substring(valueMessageIndex));
                            }
                        }
                        if(Message.toString().startsWith("ERROR_LOGIN"))
                        {
                            view.getLoginView().getLoginStatusLabel().setText("ERROR LOGIN:" + Message.toString().substring(valueMessageIndex));
                        }
                        if(Message.toString().startsWith("ERROR_CREATE"))
                        {
                            view.getCreateView().getCreateStatusLabel().setText("ERROR_CREATE:" + Message.toString().substring(valueMessageIndex));
                        }
                        if(Message.toString().startsWith("CREATE_SUCCESS"))
                        {
                            view.getLoginView().getLoginStatusLabel().setText("NEW USER CREATED, YOU MAY NOW LOGIN.");
                            view.getCardLayoutOBJ().show(view.getCardsDeck(),"LOGIN_VIEW");
                        }
                        if(Message.toString().startsWith("OUTCOME_COIN"))
                        {
                            if(Message.toString().substring(valueMessageIndex).endsWith("heads"))
                            {
                                try {
                                    // get file heads.png from photos directory
                                    // set label of coin to be outcome
                                    System.out.println("File Dir: " + System.getProperty("user.dir") + "\\Photos");
                                    view.getGameplayView().setPicture(new File(System.getProperty("user.dir") + "\\Photos\\" + "heads.png"));
                                    // Would also need to change Jlabel to reflect change of new picture
                                    //            view.getGameplayView().getPictureLabel().setIcon();
                                    view.getGameplayView().setPictureLabel(new ImageIcon(view.getGameplayView().getPicture()));
                                }
                                catch (IOException e)
                                {
                                    System.out.println("Picture not found: " + e.getMessage());
                                }
                            }
                            else
                            {
                                try {
                                    System.out.println("File Dir: " + System.getProperty("user.dir") + "\\Photos");
                                    view.getGameplayView().setPicture(new File(System.getProperty("user.dir") + "\\Photos\\" + "tails.png"));
                                    // Would also need to change Jlabel to reflect change of new picture
                                    //            view.getGameplayView().getPictureLabel().setIcon();
                                    view.getGameplayView().setPictureLabel(new ImageIcon(view.getGameplayView().getPicture()));
                                }
                                catch (IOException e)
                                {
                                    System.out.println("Picture not found: " + e.getMessage());
                                }
                            }
                            view.getGameplayView().getTextResult().setText(Message.toString().substring(valueMessageIndex));
                        }
                        if(Message.toString().startsWith("OUTCOME_ROLL"))
                        {
                            view.getViewDiceroll().getTextResult().setText(Message.toString().substring(valueMessageIndex));
                        }
                    }
                }

            }
        }
        catch (IOException e)
        {
            System.out.println("ERROR IO: " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error Class Data Sent From Server : " + e.getMessage());
        }
    }
}
