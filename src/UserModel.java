import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private Connection conn;

    public void createUserTable() throws SQLException {
        conn = DBConnection.getConnection();
        String createTable = "CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY," +
                "username VARCHAR UNIQUE NOT NULL," +
                "password VARCHAR NOT NULL," +
                "earnings REAL NOT NULL" +
                ");";
        PreparedStatement statementPrep = conn.prepareStatement(createTable);
        statementPrep.execute();
        DBConnection.closeConnection();
    }

    public void insertUserTable(User userOBJ) throws SQLException {
        conn = DBConnection.getConnection();
        String insertString = "INSERT INTO User(username,password,earnings) VALUES(?,?,?);";
        // hashing passwords in java using bcrypt library
        PreparedStatement statement = conn.prepareStatement(insertString);
        String cipherPassword = BCrypt.hashpw(userOBJ.getPassword(),BCrypt.gensalt());
        statement.setString(1,userOBJ.getUsername());
        statement.setString(2,cipherPassword);
        statement.setDouble(3,userOBJ.getEarnings());
        statement.executeUpdate();
        DBConnection.closeConnection();
    }

    public void updateUserTable(double earnings) throws SQLException {
        conn = DBConnection.getConnection();
        DBConnection.closeConnection();
    }

//    public void deleteUserTable()
//    {
//
//    }

    public boolean checkUserAuth(String username, String password) throws SQLException {
        conn = DBConnection.getConnection();
        String selectSpecifc = "SELECT password from User WHERE username = ? LIMIT 1;";
        PreparedStatement stmnt = conn.prepareStatement(selectSpecifc);
        stmnt.setString(1,username);
        ResultSet rs =  stmnt.executeQuery();

        if(rs.next())
        {
            String passwordDB = rs.getString("password");
            System.out.println("Password in db is: " + passwordDB);
            if(BCrypt.checkpw(password, passwordDB))
            {
                return true;
            }
        }
        return false;
    }
}
