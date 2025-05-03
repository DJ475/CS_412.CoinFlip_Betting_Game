import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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

    private MessageClass mc;

    public ClientListener(Socket connectionListen, View view) throws IOException {
        dlmListener = new DefaultListModel();
        this.listeningSocket = connectionListen;
        this.view = view;
        objectInputStream = new ObjectInputStream(listeningSocket.getInputStream());
        mc = new MessageClass();
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
                    }
                }


                //set list data from the j list for updating
//                try
//                {
//                    // using clear, I was able to basically refresh default model contents
//                    // then get from the Model class and add it to the default list model and use the new dlm to replace the old dlm which is being displayed in the view
////                dlm.clear();
////                for(String item : arrayList)
////                {
////                    this.dlm.addElement(item);
////                }
////                view.setDLM(dlm);
////                view.setListData(model.getData().toArray(new String[0]));
//                }
//                catch (NullPointerException e)
//                {
//                    System.out.println("Error " + e.getMessage());
//                } catch (Exception e) {
//                    System.out.println("Error" + e.getMessage());
//                }


//            ViewLeaderboard.setDlm(dlm);
//            view.setListData(LeaderBoardModel.selectUserEarnings().toArray(new String[0]));

//                if(serverResponse.startsWith("LEADERBOARD:"))
//                {
//                    System.out.println("Updating Leaderboard: " + );
//                }
//                System.out.println("Server response is: " + serverResponse);

                // Plan: Since server will implement game logic, have the photo switch based on if the server comes back as heads or tails,
                // making changes in the view then happens after processing from server sends information back

                // Idea: Have Response formated like this => "Outcome: heads" or "Error_Login: No Such User Found" or "Error_Create: username not unique"
//                try {
//                    System.out.println("File Dir: " + System.getProperty("user.dir") + "\\Photos");
//                    view.getGameplayView().setPicture(new File(System.getProperty("user.dir") + "\\Photos\\" + "tails.png"));
//                    // Would also need to change Jlabel to reflect change of new picture
////            view.getGameplayView().getPictureLabel().setIcon();
//                    view.getGameplayView().setPictureLabel(new ImageIcon(view.getGameplayView().getPicture()));
//                }
//                catch (IOException e)
//                {
//                    System.out.println("Picture not found: " + e.getMessage());
//                }
//            }
            }
        } catch (IOException e) {
            System.out.println("ERROR IO: " + e.getMessage());
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error Class Data Sent From Server : " + e.getMessage());
        }
    }
}
