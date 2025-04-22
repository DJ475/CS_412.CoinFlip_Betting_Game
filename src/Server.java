import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int port_number = 5000;
    private static ServerSocket ss = null;

    public static void main(String[] args) {
        System.out.println("Server Active on port: " + port_number);

        try {
            ss = new ServerSocket(port_number);
        } catch (IOException ioe) {
            System.out.println("I/O ERROR: " + ioe.getMessage());
            ioe.printStackTrace();
        }

        if (ss != null) {
            try {
                while(true) {
                    Socket clientConnectionSoc = ss.accept();
                    Thread th = new Thread(new ClientHandler(clientConnectionSoc));
                    th.start();
                }
            } catch (IOException e) {
                System.out.println("Error With Connection: " + e.getMessage());
            }
        }
    }
}
