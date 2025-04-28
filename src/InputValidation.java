public class InputValidation {
    private String usernameCreate;
    private String passwordCreate;

    private String usernameLogin;
    private String passwordLogin;

    private String betAmount;

    public void setUsernameCreate(String usernameCreate) {
        // Source: https://www.jrebel.com/blog/java-regular-expressions-cheat-sheet
        // Source to look for letters and numbers in strings as well as not allow spaces between usernames
        if (usernameCreate.matches("[a-zA-z\\d_*&^%$#@!?<>~:;/,.|+-]+") && !usernameCreate.matches("\\s+") && !usernameCreate.isBlank())
        {
            this.usernameCreate = usernameCreate;
        }
        else
        {
            // send back to a client saying invalid input
            System.out.println("Username for Account Creation Is Invalid");
            this.usernameCreate = null;
        }
    }

    public void setPasswordCreate(String passwordCreate) {
        // checks for only white spaces
        // checks to make sure string is not blank
        if(!passwordCreate.matches("\\s+") && !passwordCreate.isBlank())
        {
            this.passwordCreate = passwordCreate;
        }
        else
        {
            System.out.println("Password is invalid");
            this.passwordCreate = null;
        }
    }

    public void setUsernameLogin(String usernameLogin) {
        if (usernameLogin.matches("[a-zA-Z\\d_*&^%$#@!?<>~:;/,.|+-]+") && !usernameLogin.matches("\\s+") && !usernameLogin.isBlank())
        {
            this.usernameLogin = usernameLogin;
        }
        else
        {
            System.out.println("Username for Login is Invalid");
            this.usernameLogin = null;
        }
        System.out.println("Value of username is: " + usernameLogin);
    }

    public void setPasswordLogin(String passwordLogin) {
        if(passwordLogin != null && !passwordLogin.isBlank() && !passwordLogin.matches("\\s+"))
        {
            this.passwordLogin = passwordLogin;
        }
        else
        {
            System.out.println("Password is invalid");
            this.passwordLogin = null;
        }
    }

    public void setBetAmount(String betAmount) {
        // Source: https://www.demo2s.com/g/java/how-to-use-regex-pattern-to-match-positive-and-negative-number-values-in-a-string-in-java.html
        // Using this source we were able to use it to only allow positive Integers/Decimals numbers for the bet amount field
        if(betAmount.matches("\\d+\\.?\\d*") && !betAmount.isBlank())
        {
            this.betAmount = betAmount;
        }
        else
        {
            System.out.println("Bet Amount Invalid");
            this.betAmount = null;
        }
    }

    boolean ValidateCreateCreds()
    {
        if(this.usernameCreate != null && this.passwordCreate != null )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    boolean ValidateLoginCreds()
    {
        if(this.usernameLogin != null && this.passwordLogin != null )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    boolean ValidateBetAmount()
    {
        if(this.betAmount != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String CleanDataServer(String dataClean)
    {
        return dataClean.replaceAll("[\\[\\]]","");
    }

    // Source: https://www.geeksforgeeks.org/how-to-remove-all-white-spaces-from-a-string-in-java/#
    // Using this source we were able to strip all whitespace from strings to prevent issues with parsing and keep usernames consistent for authentication
    String stripWhiteSpaces(String stringCheckValid)
    {
        return stringCheckValid.strip().replaceAll("\\s","");
    }
}
