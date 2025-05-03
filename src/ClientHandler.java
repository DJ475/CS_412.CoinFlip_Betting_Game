import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.TreeMap;

public class ClientHandler implements Runnable {
    Socket currentClientSocket;
    UserModel usermodelVar;
    ObjectInputStream objectInputStreamVar = null;
    ObjectOutputStream objectOutStreamVar = null;

    String loggedInUser = "";

    public ClientHandler(Socket clientConnectionSoc, UserModel userModelVar) {
        this.currentClientSocket = clientConnectionSoc;
        this.usermodelVar = userModelVar;

        try
        {
            objectInputStreamVar = new ObjectInputStream(currentClientSocket.getInputStream());
            objectOutStreamVar = new ObjectOutputStream(currentClientSocket.getOutputStream());
        }
        catch (IOException e)
        {
            System.out.println("ERROR getting object: " + e.getMessage());
        }
    }

    public void run() {
        System.out.println("New Client Thread: " + String.valueOf(Thread.currentThread()));

        try {


            while(true) {
                TreeMap<String, String> objectData = (TreeMap<String, String>) objectInputStreamVar.readObject();
                String key = objectData.firstKey();


                String[] valuesArray = objectData.values().toArray(new String[0]);

                InputValidation inputValidationVariable = new InputValidation();

                System.out.println("Action Being Done is now: " + objectData.firstKey());
//                prwBack = new PrintWriter(currentClientSocket.getOutputStream(),true);


                if(valuesArray.length == 2)
                {
                    switch (objectData.firstKey()) // using the treemap, we can check the key and see what operation is being done, such as creating an account or gameplay
                    {
                        case "GAMEPLAY_BET"->
                        {
                            synchronized(usermodelVar)
                            {
                                GameplayClass gc = new GameplayClass();
                                System.out.println("Gameplay user is now: " + loggedInUser);
                                gc.playUserGame(inputValidationVariable,valuesArray, objectOutStreamVar,usermodelVar,loggedInUser);
                            }
                        }
                        case "CREATE_ACCOUNT"->
                        {
                            User u = new User(valuesArray[0],valuesArray[1], 0.00);
                            String username = u.getUsername();

                            synchronized (usermodelVar)
                            {
                                UserCreationClass ucc = new UserCreationClass();
                                ucc.createUser(inputValidationVariable,valuesArray,usermodelVar,u);
                            }
                        }
                        case "LOGIN_USER"->
                        {
                            User u = new User(valuesArray[0],valuesArray[1], 0.00);
                            synchronized (usermodelVar)
                            {
                                UserLoginClass ulc = new UserLoginClass();
                                ulc.loginUser(inputValidationVariable,valuesArray,usermodelVar, u);
                            }
                            this.loggedInUser = u.getUsername();

//                            System.out.println("Logged in user is now: "+ this.loggedInUser);

                        }
                        case "LEADERBOARD"->
                        {
//                            System.out.println("Request Received for Leaderboard");
                            synchronized (usermodelVar)
                            {
                                LeaderboardActionClass lac = new LeaderboardActionClass(currentClientSocket,objectOutStreamVar);
                                lac.leaderboardAction();
                            }
                        }
                    }
                }
                else
                {
                    objectOutStreamVar.writeObject(new MessageClass("ERROR_INPUT_PARSING","Invalid Input Received, Please Try Again"));
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
