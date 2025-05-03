import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

// https://stackoverflow.com/questions/13895867/why-does-writeobject-throw-java-io-notserializableexception-and-how-do-i-fix-it
// When getting an error non-serializable object. We had done some research and seen that the Arraylist
// having the type of Leaderboard was the issue because it itself isn't a serializable object.
// Serializable objects are ways to encapsulate multiple pieces of data and send their bytes through a stream without corruption;
// In our case, we were trying to send through an array of Leaderboard objects inside that were not implementing the serializable interface
// causing serialization exceptions.
public class LeaderBoard implements Serializable
{
    private String username;
    private double earnings;


    public LeaderBoard(String username, double earnings, int id) {
        this.username = username;
        this.earnings = earnings;
    }

    @Override
    public String toString() {
        return String.format("%s : %f",username,earnings);
    }
}
