import java.sql.SQLException;

public class UserCreationClass {
    public void createUser(InputValidation inputValidationVariable, String[] valuesArray, UserModel userModelVar, User u) throws SQLException {
        System.out.println("Creating Account Now");
        inputValidationVariable.setUsernameCreate(valuesArray[0]);
        inputValidationVariable.setPasswordCreate(valuesArray[1]);
        if(inputValidationVariable.ValidateCreateCreds())
        {
            u = new User(valuesArray[0],valuesArray[1], 0.00);

//                                System.out.println("New user is: " + valuesArray[0]);
//                                System.out.println("Password is: " + valuesArray[1]);

            try
            {
                synchronized (Server.class)
                {
                    userModelVar.insertUserTable(u);
                }
            } catch (SQLException e) {
                System.out.println("ERROR When Creating New User: " + e.getMessage());
            }
        }
        else
        {
            System.out.println("User input invalid");
        }
    }
}
