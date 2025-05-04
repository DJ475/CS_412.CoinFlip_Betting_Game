import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

public class DiceGameplayClass {
    public void playUserGame(InputValidation inputValidationVariable, String[] valuesArray, ObjectOutputStream objectOutputStream, UserModel usermodelVar, String loggedInUser) throws SQLException, IOException {
        if(Server.getUsersLoggedIn().containsKey(loggedInUser))
        {
//            System.out.println("Outcome is: " + new CoinFlip().CoinFlipLogic());
            String betAmount = valuesArray[0];
            String guessValue = valuesArray[1];
            inputValidationVariable.setBetAmount(betAmount); // set bet amount given to server
            if(inputValidationVariable.ValidateBetAmount() && guessValue != null) // check validity of input
            {
                System.out.println("Validation For bet Amount says: " + inputValidationVariable.ValidateBetAmount());
                System.out.println("Value 1: " + valuesArray[0] +  " Value 2: " + valuesArray[1]);
                System.out.println("Gameplay Mechanic Activated");
                Diceroll dr = new Diceroll();
                String resultRoll = dr.DicerollLogic();
                if(resultRoll.equals(guessValue))
                {
                    objectOutputStream.writeObject(new MessageClass("OUTCOME_ROLL","Guessed Correctly, Its " + resultRoll));
                    synchronized (usermodelVar)
                    {
                        usermodelVar.updateUserTable(loggedInUser, Double.parseDouble(betAmount));
                    }
                }
                else
                {
                    objectOutputStream.writeObject("Guess Incorrect");
                    objectOutputStream.writeObject(new MessageClass("OUTCOME_ROLL","Guess Incorrect, It Was " + resultRoll));
                    synchronized (usermodelVar)
                    {
                        double doubleValue = Double.parseDouble(betAmount);
                        usermodelVar.updateUserTable(loggedInUser, -doubleValue);
                    }
                }

                objectOutputStream.writeObject(new MessageClass("EARNINGS_DICE",usermodelVar.selectUserEarnings(loggedInUser)));
            }
            else
            {
                objectOutputStream.writeObject(new MessageClass("ERROR_BET_DICE","Bet Values Invalid: (The Bet Amount Must Be a Positive Integer/Decimal, Must select Heads or Tails to continue"));
            }
        }
        else
        {

            objectOutputStream.writeObject(new MessageClass("ERROR_LOGIN","User Not Logged in"));
        }
    }
}
