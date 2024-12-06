import java.io.Serializable;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class Deck extends ArrayList<Card> implements Serializable
{
    /*Constructor, creates a new deck and then randomly sorts deck*/
    Deck(){
        char suits[] = {'H', 'D', 'S', 'C'};

        for(char suit : suits) {
            for(int i = 2; i <= 14; i++) {
                add(new Card(suit, i));
            }
        }

        this.shuffle();
    }

    public void newDeck(){
        this.clear();

        char suits[] = {'H', 'D', 'S', 'C'};

        for(char suit : suits) {
            for(int i = 2; i <= 14; i++) {
                add(new Card(suit, i));
            }
        }

        this.shuffle();
    }

    public void shuffle(){
        Collections.shuffle(this, new Random());
    }
}
