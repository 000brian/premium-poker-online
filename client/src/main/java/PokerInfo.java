import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable
{
    Player player;
    ArrayList<Card> dealersHand;
    int gameStage;
    /*
   -1 : hasn't started
    0 : player needs to make play + ante
    1 : dealer needs to deal cards
    2 : player makes play wager or folds
    3 : show cards and evaluate bets
    */

    PokerInfo()
    {
        player = new Player(); // will be updated by both
        dealersHand = new ArrayList<Card>(); // should be set by server
        int gameStage = 0;
    }
    public String toString()
    {
        return "ante: " + player.getAnteBet() + " play: " + player.getPairPlusBet();
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public ArrayList<Card> getDealersHand()
    {
        return dealersHand;
    }

    public void setDealersHand(ArrayList<Card> dealersHand)
    {
        this.dealersHand = dealersHand;
    }

    public int getGameStage()
    {
        return gameStage;
    }

    public void setGameStage(int gameStage)
    {
        this.gameStage = gameStage;
    }
}
