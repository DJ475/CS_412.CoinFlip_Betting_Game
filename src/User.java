import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String username;
    private String password;
    private double earnings;


    public User(String username, String password, double earnings) {
        this.username = username;
        this.password = password;
        this.earnings = earnings;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getEarnings() {
        return earnings;
    }

    public String hashPwFunction(String password)
    {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public void updateEarningsUser(double betAmount) {
        this.earnings += betAmount;
    }
}
