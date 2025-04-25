import java.sql.Connection;
import java.sql.SQLException;

public class UserModel {
    private Connection conn;

    public void createUserTable() throws SQLException {
        conn = DBConnection.getConnection();
        String createTable = "CREATE TABLE IF NOT EXISTS User (" +
                "id INT PRIMARY KEY NOT NULL," +
                "username VARCHAR UNIQUE NOT NULL," +
                "password VARCHAR NOT NULL," +
                ");";
        DBConnection.closeConnection();
    }

    public void insertUserTable(String username, String password, double earnings) throws SQLException {
        conn = DBConnection.getConnection();
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
}
