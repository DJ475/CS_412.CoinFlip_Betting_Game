import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.TreeMap;

public class Server {
    private static int port_number = 5000;
    private static ServerSocket ss = null;

    private static UserModel userModelVar;
    private static LeaderBoardModel leaderBoardModelVar;

    private static TreeMap<String,User> usersLoggedIn = new TreeMap<>();

    public static TreeMap<String, User> getUsersLoggedIn() {
        return usersLoggedIn;
    }

    public static void main(String[] args) {
        try
        {
            userModelVar = new UserModel();
            leaderBoardModelVar = new LeaderBoardModel();
            System.out.println("Creating user table");
            userModelVar.createUserTable();
            leaderBoardModelVar.createUserTable();
        }
        catch (SQLException e)
        {
            System.out.println("ERRO SQL: " + e.getMessage());
        }
        System.out.println("Server Active on port: " + port_number);

        try {
            ss = new ServerSocket(port_number);
        } catch (IOException ioe) {
            System.out.println("I/O ERROR: " + ioe.getMessage());
            ioe.printStackTrace();
        }

        if (ss != null) {
            try
            {
                while(true)
                {
                    Socket clientConnectionSoc = ss.accept();
                    Thread th = new Thread(new ClientHandler(clientConnectionSoc,userModelVar));
                    th.start();
                }
            }
            catch (IOException e)
            {
                System.out.println("Error With Connection: " + e.getMessage());
            }
        }
    }
}
