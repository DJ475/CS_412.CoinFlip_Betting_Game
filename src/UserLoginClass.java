import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class UserLoginClass {
    public void loginUser(InputValidation inputValidationVariable, String[] valuesArray, UserModel usermodelVar, User u, ObjectOutputStream objectOutStreamVar) throws SQLException, IOException {
        inputValidationVariable.setUsernameLogin(valuesArray[0]);
        inputValidationVariable.setPasswordLogin(valuesArray[1]);
        if(inputValidationVariable.ValidateLoginCreds())
        {
            System.out.println("User login is now: " + valuesArray[0]);
            System.out.println("Password is: " + valuesArray[1]);
            // Check if bcrypt comes back with true or false for correct and incorrect data entered
            if(usermodelVar.checkUserAuth(valuesArray[0],valuesArray[1]))
            {
                System.out.println("Authentication Passed");
                objectOutStreamVar.writeObject(new MessageClass("LOGIN_SUCCESS", valuesArray[0]));
                // authentication passed then add user to logged in user tree map
                synchronized (Server.getUsersLoggedIn())
                {
                    Server.getUsersLoggedIn().put(u.getUsername(),u);
                    System.out.println("Server Logged in users " + Server.getUsersLoggedIn());
                }
                String earningString = usermodelVar.selectUserEarnings(valuesArray[0]);
                objectOutStreamVar.writeObject(new MessageClass("EARNINGS_COIN",earningString));
                objectOutStreamVar.writeObject(new MessageClass("EARNINGS_DICE",earningString));
            }
            else
            {
                System.out.println("Authentication Failed");
                objectOutStreamVar.writeObject(new MessageClass("ERROR_LOGIN","Authentication Failed, Please Retry Login"));

            }
//            System.out.println("Authentication Result: " + usermodelVar.checkUserAuth(valuesArray[0],valuesArray[1]));
        }
        else
        {
            System.out.println("User input invalid");
            objectOutStreamVar.writeObject(new MessageClass("ERROR_LOGIN","USER INPUT INVALID"));
        }
    }
}
