import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private static Connection conn;

    public UserModel() throws SQLException {
            conn = DBConnection.getConnection();
    }

    public void createUserTable() throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY," +
                "username VARCHAR UNIQUE NOT NULL," +
                "password VARCHAR NOT NULL," +
                "earnings REAL NOT NULL" +
                ");";
        PreparedStatement statementPrep = conn.prepareStatement(createTable);
        statementPrep.execute();
    }

    public void insertUserTable(User userOBJ) throws SQLException {
        String insertString = "INSERT INTO User(username,password,earnings) VALUES(?,?,?);";
        // hashing passwords in java using bcrypt library
        PreparedStatement statement = conn.prepareStatement(insertString);
        String cipherPassword = BCrypt.hashpw(userOBJ.getPassword(),BCrypt.gensalt());
        statement.setString(1,userOBJ.getUsername());
        statement.setString(2,cipherPassword);
        statement.setDouble(3,userOBJ.getEarnings());
        statement.executeUpdate();
    }

    public void updateUserTable(String username,double earnings) throws SQLException {
        String selectUserEarnings = "SELECT earnings FROM User WHERE username = ? LIMIT 1;";
        PreparedStatement stmntSelectEarnings = conn.prepareStatement(selectUserEarnings);
        stmntSelectEarnings.setString(1,username);
        ResultSet rsEarningsUser = stmntSelectEarnings.executeQuery();
        if(rsEarningsUser.next())
        {
            // get earnings for user
            double earningsDB = rsEarningsUser.getDouble("earnings");
            // update thier earnings in database with bet amount(this can be positive or negative, depending on if they guessed correctly)
            double updatedEarnings = earningsDB+earnings;
            String updateString = "UPDATE User SET earnings = ? WHERE username = ?;";
            PreparedStatement stmntUpdate = conn.prepareStatement(updateString);
            stmntUpdate.setDouble(1,updatedEarnings);
            stmntUpdate.setString(2,username);
            stmntUpdate.executeUpdate();
        }

    }

    public String selectUserEarnings(String username) throws SQLException {
        String selectUserEarnings = "SELECT earnings FROM User WHERE username = ? LIMIT 1;";
        PreparedStatement stmntSelect = conn.prepareStatement(selectUserEarnings);
        System.out.println("Username passed int function is now: " + username);
        stmntSelect.setString(1,username);
        ResultSet rsSelect = stmntSelect.executeQuery();
        if(rsSelect.next())
        {
            Double Earnings = rsSelect.getDouble("earnings");
            return String.valueOf(Earnings);
        }
        else
        {
            return null;
        }
    }

    public boolean checkUserAuth(String username, String password) throws SQLException {
        String selectSpecifc = "SELECT password from User WHERE username = ? LIMIT 1;";
        PreparedStatement stmnt = conn.prepareStatement(selectSpecifc);
        stmnt.setString(1,username);
        ResultSet rs =  stmnt.executeQuery();

        if(rs.next())
        {
            String passwordDB = rs.getString("password");
//            System.out.println("Password in db is: " + passwordDB);
            if(BCrypt.checkpw(password, passwordDB))
            {
                return true;
            }
        }
        return false;
    }
}
