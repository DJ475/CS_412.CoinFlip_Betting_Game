import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListener implements Runnable{
    Socket listeningSocket;
    View view;

    public ClientListener(Socket connectionListen, View view) {
        this.listeningSocket = connectionListen;
        this.view = view;
    }

    @Override
    public void run() {
        while (true)
        {
            String serverResponse = null;
            try {
                // listening for server response
                InputStreamReader isrBack = new InputStreamReader(listeningSocket.getInputStream());
                BufferedReader brMsgBack = new BufferedReader(isrBack);
                serverResponse = brMsgBack.readLine();

                System.out.println("Server response is: " + serverResponse);

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

            } catch (IOException e) {
                System.out.println();
            }
        }
    }
}
