import java.util.Random;

public class CoinFlip
{
    public String CoinFlipLogic()
    {
        int randomNum;
        
        Random rand = new Random();

        //either a 0 or a 1
        randomNum = rand.nextInt(2);

        if(randomNum == 0)
        {
            return "heads";
        }
        else if(randomNum == 1)
        {
            return "tails";
        }

        return "coinflip funciton failure";

    }
}
