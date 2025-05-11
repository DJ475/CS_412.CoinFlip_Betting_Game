import java.io.IOException;
import java.io.ObjectOutputStream;
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
                        try {
                            usermodelVar.updateUserTable(loggedInUser, Integer.parseInt(betAmount));
                        } catch (SQLException e) {
                            objectOutputStream.writeObject(new MessageClass("ERROR_SQL_DICE",e.getMessage()));
                        } catch (NumberFormatException e) {
                            objectOutputStream.writeObject(new MessageClass("ERROR_INPUT_DICE","INPUT VALIDATION FAILED"));
                        }
                    }
                }
                else
                {
                    objectOutputStream.writeObject("Guess Incorrect");
                    objectOutputStream.writeObject(new MessageClass("OUTCOME_ROLL","Guess Incorrect, It Was " + resultRoll));
                    synchronized (usermodelVar)
                    {
                        try
                        {
                            int intEarningsValue = Integer.parseInt(betAmount);
                            if(intEarningsValue > 999999999)
                            {
                                objectOutputStream.writeObject(new MessageClass("ERROR_INPUT_DICE","INPUT NEEDS to be less than 999999999"));
                            }
                            else
                            {
                                usermodelVar.updateUserTable(loggedInUser, -intEarningsValue);
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            objectOutputStream.writeObject(new MessageClass("ERROR_INPUT_DICE","INPUT IS INVALID(MUST Be <= 999999999)"));
                        }
                        catch (SQLException e)
                        {
                            objectOutputStream.writeObject(new MessageClass("ERROR_SQL_DICE",e.getMessage()));
                        }
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
