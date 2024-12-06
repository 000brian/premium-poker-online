import java.io.Serializable;
import java.util.Arrays;

public class Card implements Serializable
{
    int value;
    char suit;
    boolean faceUp = true;

    /*Constructor*/
    Card(char suit, int value) {
        if(value < 2 || value > 14) {
            throw new IllegalArgumentException("Invalid value");
        }

        this.suit = suit;
        this.value = value;
    }

    /*Getters*/
    public int getValue() {
        return value;
    }

    public char getSuit() {
        return suit;
    }

    public String asString() {
        String cardValue = "";
        switch(value) {
            case 11:
                cardValue = "J";
                break;
            case 12:
                cardValue = "Q";
                break;
            case 13:
                cardValue = "K";
                break;
            case 14:
                cardValue = "A";
                break;
            default:
                cardValue = Integer.toString(value);
        }

        return cardValue + " of " + suit;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void flip() {
        faceUp = !faceUp;
    }

    public String toString()
    {
        String hidden = "FD";
        if (faceUp) {
            hidden = "FU";
        }
        return this.asString() + " " + hidden;
    }
}
