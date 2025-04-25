import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URI = "jdbc:sqlite:coinflip.db";
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(URI);
        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }
}
