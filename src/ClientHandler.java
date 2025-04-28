import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.TreeMap;
import org.mindrot.jbcrypt.BCrypt;

public class ClientHandler implements Runnable {
    Socket currentClientSocket;
    UserModel usermodelVar;
    ObjectInputStream objectSent = null;

    public ClientHandler(Socket clientConnectionSoc, UserModel userModelVar) {
        this.currentClientSocket = clientConnectionSoc;
        this.usermodelVar = userModelVar;
    }

    public void run() {
        System.out.println("New Client Thread: " + String.valueOf(Thread.currentThread()));

        PrintWriter prwBack = null;

        try {
            objectSent = new ObjectInputStream(currentClientSocket.getInputStream());
            while(true) {
                TreeMap<String, String> objectData = (TreeMap<String, String>) objectSent.readObject();
                String key = objectData.firstKey();
                String[] valuesArray = objectData.values().toArray(new String[0]);

                InputValidation inputValidationVariable = new InputValidation();

                System.out.println("Action Being Done is now: " + objectData.firstKey());
                prwBack = new PrintWriter(currentClientSocket.getOutputStream(),true);

                User u;

                if(valuesArray.length == 2)
                {
                    switch (objectData.firstKey()) // using the treemap, we can check the key and see what operation is being done, such as creating an account or gameplay
                    {
                        case "GAMEPLAY_BET"->
                        {
                            {
                                System.out.println("Outcome is: " + new CoinFlip().CoinFlipLogic());
                                String betAmount = valuesArray[0];
                                String guessValue = valuesArray[1];
                                inputValidationVariable.setBetAmount(betAmount); // set bet amount given to server
                                if(inputValidationVariable.ValidateBetAmount() && guessValue != null) // check validity of input
                                {
                                    System.out.println("Validation For bet Amount says: " + inputValidationVariable.ValidateBetAmount());
                                    System.out.println("Value 1: " + valuesArray[0] +  " Value 2: " + valuesArray[1]);

                                    System.out.println("Object is now: " + objectData.toString());
                                    System.out.println("Gameplay Mechanic Activated");
                                }
                                else
                                {
                                    prwBack.println("Bet Values Invalid: (The Bet Amount Must Be a Positive Integer/Decimal, Must select Heads or Tails to continue");
                                }
                            }

                        }
                        case "CREATE_ACCOUNT"->
                        {
                            System.out.println("Creating Account Now");
                            inputValidationVariable.setUsernameCreate(valuesArray[0]);
                            inputValidationVariable.setPasswordCreate(valuesArray[1]);
                            if(inputValidationVariable.ValidateCreateCreds())
                            {
                                u = new User(valuesArray[0],valuesArray[1], 0.00);

//                                System.out.println("New user is: " + valuesArray[0]);
//                                System.out.println("Password is: " + valuesArray[1]);
                                usermodelVar.insertUserTable(u);
                            }
                            else
                            {
                                System.out.println("User input invalid");
                            }
                        }
                        case "LOGIN_USER"->
                        {
                            inputValidationVariable.setUsernameLogin(valuesArray[0]);
                            inputValidationVariable.setPasswordLogin(valuesArray[1]);
                            if(inputValidationVariable.ValidateLoginCreds())
                            {
                                System.out.println("User login is now: " + valuesArray[0]);
                                System.out.println("Password is: " + valuesArray[1]);
                                // Check if bcrypt comes back with true or false for correct and incorrect data entered
                                System.out.println("Authentication Result: " + usermodelVar.checkUserAuth(valuesArray[0],valuesArray[1]));
                            }
                            else
                            {
                                System.out.println("User input invalid");
                            }
                        }
                        case "LEADERBOARD"->
                        {
                            System.out.println("Implementing Leader Board Logic");
                        }
                    }
                }
                else
                {
                    prwBack.println("Invalid Input Received, Please Try Again");
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error Handling Connection: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error SQL: "+ e.getMessage());
//            prwBack.println("Error In Creating User, Please make sure Usernames are unique,Contain Alphabetic Characters and Numbers, Symbols are also optional");
        }
    }
}
