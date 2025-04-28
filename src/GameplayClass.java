import java.io.PrintWriter;

public class GameplayClass {
    public void playUserGame(InputValidation inputValidationVariable, String[] valuesArray, PrintWriter prwBack)
    {
        System.out.println("Outcome is: " + new CoinFlip().CoinFlipLogic());
        String betAmount = valuesArray[0];
        String guessValue = valuesArray[1];
        inputValidationVariable.setBetAmount(betAmount); // set bet amount given to server
        if(inputValidationVariable.ValidateBetAmount() && guessValue != null) // check validity of input
        {
            System.out.println("Validation For bet Amount says: " + inputValidationVariable.ValidateBetAmount());
            System.out.println("Value 1: " + valuesArray[0] +  " Value 2: " + valuesArray[1]);

            System.out.println("Gameplay Mechanic Activated");
        }
        else
        {
            prwBack.println("Bet Values Invalid: (The Bet Amount Must Be a Positive Integer/Decimal, Must select Heads or Tails to continue");
        }
    }
}
