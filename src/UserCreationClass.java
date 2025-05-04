import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class UserCreationClass {
    public void createUser(InputValidation inputValidationVariable, String[] valuesArray, UserModel userModelVar, User u, ObjectOutputStream objectOutStreamVar) throws SQLException, IOException {
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
                objectOutStreamVar.writeObject(new MessageClass("CREATE_SUCCESS","NEW USER CREATED, YOU MAY NOW LOGIN."));
            }
            catch (SQLException e)
            {
                if(e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]"))
                {
                    objectOutStreamVar.writeObject(new MessageClass("ERROR_CREATE","USERNAME MUST BE UNIQUE"));
                }
                else
                {
                    objectOutStreamVar.writeObject(new MessageClass("ERROR_CREATE",e.getMessage()));
                }
            }
        }
        else
        {
            objectOutStreamVar.writeObject(new MessageClass("ERROR_CREATE","User Input Invalid"));
        }
    }
}
