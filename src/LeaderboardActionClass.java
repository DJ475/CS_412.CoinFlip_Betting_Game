import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeaderboardActionClass {
    private Socket clientSocketPassed;
    private LeaderBoardModel leaderBoardModel;
    private ObjectOutputStream objSendLeaders;

    public LeaderboardActionClass(Socket currentClientSocket, ObjectOutputStream objectOutStreamVar) throws SQLException, IOException {
        this.clientSocketPassed = currentClientSocket;
        leaderBoardModel = new LeaderBoardModel();
        objSendLeaders = objectOutStreamVar;
    }

    public void leaderboardAction()
    {
        System.out.println("Leaderboard Logic Here");

        try
        {
            synchronized (leaderBoardModel)
            {
                ArrayList<LeaderBoard> arrayList = leaderBoardModel.selectUserLeaderboardEarnings();

// Here the idea was to clear the leaderboard table, then insert and for the leaderboard with the updated information from the user table
// However, problems with sqlite_busy errors made it so that we only use the users table but restrict access to it by limiting queries to 3 results, selecting username and earnings only(no password)
// and using prepared statements to prevent SQLi's.
                
//                synchronized (leaderBoardModel)
//                {
//                    leaderBoardModel.refreshLeaderData(); // delete 3 leaders and insert again
//                }
                
//                for (LeaderBoard leaders : arrayList)
//                {
//                    System.out.println("Array Now Has: " + leaders.getUsername() + ":" + leaders.getEarnings());
//
//                    try
//                    {
//                        synchronized (leaderBoardModel)
//                        {
//                            leaderBoardModel.insertUserLeaderboard(leaders.getUsername(),leaders.getEarnings(),leaders.getId());
//                        }
//                    }  catch
//                    (SQLException e)
//                    {
//                        System.out.println("ERROR When Inserting New Leader: " + e.getMessage());
//                    }
//                }

                // sending back arraylist of users
                objSendLeaders.writeObject(arrayList);
            }
        }
        catch (NullPointerException e)
        {
            System.out.println("Error " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
