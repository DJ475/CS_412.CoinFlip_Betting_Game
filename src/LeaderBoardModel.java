import java.sql.*;
import java.util.ArrayList;

public class LeaderBoardModel {
    private Connection conn;
    private ArrayList<LeaderBoard> arrayList = new ArrayList<>();

    public Connection getConn() {
        return conn;
    }

    public LeaderBoardModel() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public void createUserTable() throws SQLException {
        PreparedStatement stmtFK = conn.prepareStatement("PRAGMA foreign_keys = ON;");
        stmtFK.execute();

        String createTable = "CREATE TABLE IF NOT EXISTS Leaderboard (" +
                "leaderBoardId INTEGER PRIMARY KEY," +
                "username VARCHAR UNIQUE NOT NULL," +
                "earnings REAL NOT NULL," +
                "userId INTEGER NOT NULL," +
                "FOREIGN KEY (userId) REFERENCES User(id)" +
                ");";
        PreparedStatement statementPrep = conn.prepareStatement(createTable);
        statementPrep.execute();
    }

    public ArrayList<LeaderBoard> selectUserLeaderboardEarnings() throws SQLException {
        String selectUserEarnings = "SELECT id, username, earnings FROM User ORDER BY earnings DESC LIMIT 3;";
        PreparedStatement stmntSelect = conn.prepareStatement(selectUserEarnings);
        ResultSet rsSelect = stmntSelect.executeQuery();

        while (rsSelect.next()) {
            int id = rsSelect.getInt("id");
            String User = rsSelect.getString("username");
            float Earnings = (float) rsSelect.getDouble("earnings");

//            String add = (User + " $" + Earnings);
            LeaderBoard leaderBoardUser = new LeaderBoard(User, Earnings, id);
            arrayList.add(leaderBoardUser);
        }


        System.out.println("Array list Leaders in model: " + arrayList);

        rsSelect.close();
        stmntSelect.close();
//        conn.close();
        return arrayList;
    }

    public void refreshLeaderData() throws SQLException {
        String refreshString = "DELETE FROM Leaderboard;";
        Statement stmntRefresh = conn.createStatement();
        stmntRefresh.executeUpdate(refreshString);
    }

    public void insertUserLeaderboard(String username, double earnings, int id) throws SQLException {
//        String insertStringLeaders = "INSERT INTO LeaderBoard (username,earnings,userId)" +
//                "VALUES" +
//                "(?,?,?);";
        String insertStringLeaders = "INSERT INTO REPLACE LeaderBoard (username,earnings,userId)" +
                "VALUES" +
                "(?,?,?);";

        PreparedStatement stmntInsertLeaders = conn.prepareStatement(insertStringLeaders);

        stmntInsertLeaders.setString(1, username);
        stmntInsertLeaders.setDouble(2, earnings);
        stmntInsertLeaders.setInt(3, id);
        stmntInsertLeaders.executeUpdate();
    }


    public ArrayList<LeaderBoard> selectTopLeaderboardEarnings() throws SQLException {
        String selectUserEarnings = "SELECT * FROM Leaderboard LIMIT 3;";
        PreparedStatement stmntSelect = conn.prepareStatement(selectUserEarnings);
        ResultSet rsSelect = stmntSelect.executeQuery();

        ArrayList<LeaderBoard> leaderBoardArrayList = new ArrayList<>();

        while (rsSelect.next())
        {
            String usernameLeader = rsSelect.getString("username");
            double earningsLeader = rsSelect.getDouble("earnings");
            int idLeader = rsSelect.getInt("userId");


            LeaderBoard leaderOBJ = new LeaderBoard(usernameLeader,earningsLeader,idLeader);

            leaderBoardArrayList.add(leaderOBJ);
        }

        return leaderBoardArrayList;
    }
}
