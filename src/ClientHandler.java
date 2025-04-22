import com.sun.source.tree.Tree;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class ClientHandler implements Runnable {
    Socket currentClientSocket;
    ObjectInputStream objectSent = null;

    public ClientHandler(Socket clientConnectionSoc) {
        this.currentClientSocket = clientConnectionSoc;
    }

    public void run() {
        System.out.println("New Client Thread: " + String.valueOf(Thread.currentThread()));

        try {
            objectSent = new ObjectInputStream(currentClientSocket.getInputStream());
            while(true) {
                TreeMap<String, String> objectData = (TreeMap<String, String>) objectSent.readObject();
                String key = objectData.firstKey();
                String[] valuesArray = objectData.values().toArray(new String[0]);

                InputValidation inputValidationVariable = new InputValidation();



                PrintWriter prwBack = new PrintWriter(currentClientSocket.getOutputStream(),true);

                switch (objectData.firstKey()) // using the treemap, we can check the key and see what operation is being done, such as creating an account or gameplay
                {
                    case "GAMEPLAY_BET"->
                    {
                        if(valuesArray.length == 2)
                        {

                            String betAmount = valuesArray[0];
                            String guessValue = valuesArray[1];
                            inputValidationVariable.setBetAmount(betAmount); // set bet amount given to server
                            if(inputValidationVariable.ValidateBetAmount()) // check validity of input
                            {
                                System.out.println("Validation For bet Amount says: " + inputValidationVariable.ValidateBetAmount());
                                System.out.println("Value 1: " + valuesArray[0] +  " Value 2: " + valuesArray[1]);

                                System.out.println("Object is now: " + objectData.toString());
                                System.out.println("Gameplay Mechanic Activated");
                            }
                            else
                            {
                                prwBack.println("Bet Amount Invalid: (The Amount Must Be a Positive Integer/Decimal");
                            }
                        }
                        else
                        {
                            prwBack.println("Invalid Input Received, Please Try Again");
                        }

                    }
                    case "CREATE_ACCOUNT"->
                    {
                        System.out.println("Creating Account Now");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error Handling Connection: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }
    }
}
