import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class GameplayClass {
    public void playUserGame(InputValidation inputValidationVariable, String[] valuesArray, ObjectOutputStream objWrBack, UserModel usermodelVar, String loggedInUser) throws SQLException, IOException {

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
                CoinFlip cf = new CoinFlip();
                String resultFlip = cf.CoinFlipLogic();
                if(resultFlip.equals(guessValue))
                {
                    objWrBack.writeObject(new MessageClass("OUTCOME_COIN","Guessed Correctly, Its " + resultFlip));
                    synchronized (usermodelVar)
                    {
                        usermodelVar.updateUserTable(loggedInUser, Integer.parseInt(betAmount));
                    }
                }
                else
                {
                    objWrBack.writeObject(new MessageClass("OUTCOME_COIN","Guess Incorrect, It Was " + resultFlip));
                    synchronized (usermodelVar)
                    {
                        int intEarningsValue = Integer.parseInt(betAmount);
                        usermodelVar.updateUserTable(loggedInUser, -intEarningsValue);
                    }
                }

                objWrBack.writeObject(new MessageClass("EARNINGS_COIN",usermodelVar.selectUserEarnings(loggedInUser)));
            }
            else
            {
                objWrBack.writeObject(new MessageClass("ERROR_BET_COIN:","Bet Values Invalid: (The Bet Amount Must Be a Positive Integer/Decimal, Must select Heads or Tails to continue"));
            }
        }
        else
        {
            objWrBack.writeObject(new MessageClass("ERROR_LOGIN: ","User Not Logged in"));
        }
    }
}
