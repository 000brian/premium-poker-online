import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeckAndDealerTest {

    @Test
    void dealerConstructorTest() {
        Dealer dealer = new Dealer();

        assertEquals(52, dealer.theDeck.size());
        assertEquals(0, dealer.dealersHand.size());
    }

    @Test
    void deckConstructorTest() {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
    }

    @Test
    void dealerDealTest() {
        Dealer dealer = new Dealer();
        int deckSizeBefore = dealer.theDeck.size();
        ArrayList<Card> hand = dealer.dealHand();
        int deckSizeAfter = dealer.theDeck.size();

        assertEquals(3, hand.size());
        assertEquals(52, deckSizeBefore);
        assertEquals(49, deckSizeAfter);
    }

    @Test
    void deckSuitsTest(){
        Deck deck = new Deck();
        int hearts = 0;
        int diamonds = 0;
        int spades = 0;
        int clubs = 0;

        for(Card card : deck){
            switch(card.getSuit()){
                case 'H':
                    hearts++;

                    break;
                case 'D':
                    diamonds++;

                    break;
                case 'S':
                    spades++;

                    break;
                case 'C':
                    clubs++;

                    break;
            }
        }

        assertEquals(13, hearts);
        assertEquals(13, diamonds);
        assertEquals(13, spades);
        assertEquals(13, clubs);
    }
    
    @Test
    void deckValuesTest(){
        Deck deck = new Deck();
        int[] values = new int[13];

        for(Card card : deck){
            values[card.getValue() - 2]++;
        }

        for(int i = 0; i < values.length; i++){
            assertEquals(4, values[i]);
        }
    }

    @Test
    void deckNewDeckTest(){
        Deck deck = new Deck();
        deck.remove(0);
        deck.newDeck();

        assertEquals(52, deck.size());
    }   
    
    @Test
    void deckShuffleTest(){
        Deck deck = new Deck();
        int[] values = new int[52];

        for(int i = 0; i < 52; i++){
            values[i] = deck.get(i).getValue();
        }

        deck.shuffle();

        boolean allSame = true;
        for(int i = 0; i < 52; i++){
            if (values[i] != deck.get(i).getValue()){
                allSame = false;
                break;
            }
        }

        if(allSame){
            fail("Deck was not shuffled");
        }
    } 

    @Test
    void dealerGetHandTest(){
        Dealer dealer = new Dealer();
        ArrayList<Card> hand = new ArrayList<Card>();
        
        hand.add(new Card('S', 5));
        hand.add(new Card('H', 5));
        hand.add(new Card('D', 5));
        dealer.setHand(hand);

        assertEquals(hand, dealer.getHand());
    }

    @Test
    void dealerCheckDeckTest(){
        Dealer dealer = new Dealer();

        dealer.checkDeck();

        assertEquals(52, dealer.theDeck.size());
    }


    @Test
    void dealerCheckDeckTest2(){
        Dealer dealer = new Dealer();
        
        for(int i = 0; i < 25; i++){
            dealer.theDeck.remove(0);
        }

        dealer.checkDeck();

        assertEquals(52, dealer.theDeck.size());
    }

}

