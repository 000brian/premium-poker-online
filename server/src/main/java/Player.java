import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{
    int anteBet;
    int playBet;
    int pairPlusBet;
    int balance;
    boolean folded;
    ArrayList<Card> hand;
    boolean anteLocked;

    /*Constructor*/
    Player()
    {
        this.anteBet = 0;
        this.playBet = 0;
        this.pairPlusBet = 0;
        this.balance = 0;
        this.folded = false;
        this.hand = new ArrayList<Card>();
        this.anteLocked = false;
    }

    /*Getters and Setters*/
    public int getAnteBet()
    {
        return anteBet;
    }

    public void setAnteBet(int anteBet)
    {
        if (!anteLocked)
        {
            this.anteBet = anteBet;
        }
    }

    public boolean isAnteLocked()
    {
        return anteLocked;
    }

    public void setAnteLocked(boolean anteLocked)
    {
        this.anteLocked = anteLocked;
    }

    public int getPlayBet()
    {
        return playBet;
    }

    public void setPlayBet(int playBet)
    {
        this.playBet = playBet;
    }

    public int getPairPlusBet()
    {
        return pairPlusBet;
    }

    public void setPairPlusBet(int pairPlusBet)
    {
        this.pairPlusBet = pairPlusBet;
    }


    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public boolean isFolded()
    {
        return folded;
    }

    public void setFolded(boolean folded)
    {
        this.folded = folded;
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public void setHand(ArrayList<Card> hand)
    {
        this.hand = hand;
    }

    public void resetBets()
    {
        this.anteBet = 0;
        this.playBet = 0;
        this.pairPlusBet = 0;
        this.folded = false;
        this.anteLocked = false;
    }



}
