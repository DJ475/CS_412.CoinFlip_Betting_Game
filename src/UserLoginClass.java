import java.sql.SQLException;

public class UserLoginClass {
    public void loginUser(InputValidation inputValidationVariable,String[] valuesArray, UserModel usermodelVar) throws SQLException {
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
}

