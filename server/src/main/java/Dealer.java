import java.util.ArrayList;

public class Dealer {
    Deck theDeck;
    ArrayList<Card> dealersHand;

    /*Constructor*/
    Dealer() {
        theDeck = new Deck();
        dealersHand = new ArrayList<Card>();
    }

    public void checkDeck(){
        if(theDeck.size() <= 34){
            theDeck.newDeck();
        }
    }

    public ArrayList<Card> dealHand(){
        ArrayList<Card> hand = new ArrayList<Card>();
        for(int i = 0; i < 3; i++)
        {
            hand.add(theDeck.get(0));
            theDeck.remove(0);
        }
        return hand;
    }

    public ArrayList<Card> getHand() {
        return dealersHand;
    }

    public void setHand(ArrayList<Card> hand) {
        dealersHand = hand;
    }


}
