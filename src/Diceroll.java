import java.util.Random;

public class Diceroll
{
    public String DicerollLogic()
    {
        int randomNum;

        Random rand = new Random();

        //1-6
        randomNum = rand.nextInt(1, 7);

        if(randomNum == 1)
        {
            return "one";
        }
        else if(randomNum == 2)
        {
            return "two";
        }
        else if(randomNum == 3)
        {
            return "three";
        }
        else if(randomNum == 4)
        {
            return "four";
        }
        else if(randomNum == 5)
        {
            return "five";
        }
        else if(randomNum == 6)
        {
            return "six";
        }

        return "diceroll function failure";
    }
}
