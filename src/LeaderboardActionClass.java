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
