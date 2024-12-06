import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class ThreeCardLogic implements Serializable
{
    public static int evalHand(ArrayList<Card> hand)
    {
        // sorts cards in descending order
        hand.sort((a, b) ->
        {
            return a.getValue() < b.getValue() ? 1 : -1;
        });

        // if hand is all the same value
        if (hand.get(0).getValue() == hand.get(1).getValue() && hand.get(1).getValue() == hand.get(2).getValue())
        {
            System.out.println("three of a kind");
            return 2; // three of a kind
        }

        // if hand is descending by one
        if (hand.get(0).getValue() == (hand.get(1).getValue() + 1) && (hand.get(1).getValue() + 1) == (hand.get(2).getValue() + 2))
        {
            // and if all the suits are the same
            if (hand.get(0).getSuit() == hand.get(1).getSuit() && hand.get(0).getSuit() == hand.get(2).getSuit())
            {
                System.out.println("straight flush");
                return 1; // straight flush
            } else
            {
                System.out.println("straight");
                return 3; // straight
            }
        }
        // if all the suits are the same
        if (hand.get(0).getSuit() == hand.get(1).getSuit() && hand.get(0).getSuit() == hand.get(2).getSuit())
        {
            System.out.println("flush");
            return 4; // flush
        }

        // if there is a pair
        if (hand.get(0).getValue() == hand.get(1).getValue() || hand.get(1).getValue() == hand.get(2).getValue())
        {
            System.out.println("pair");
            return 5; // pair
        }

        return 0;
    }

    ;

    public static int evalPPWinnings(ArrayList<Card> hand, int bet)
    {
        int eval = evalHand(hand);
        HashMap<Integer, Integer> payouts = new HashMap<>();

        payouts.put(5, 1);  // pair
        payouts.put(4, 3);  // flush
        payouts.put(3, 6);  // straight
        payouts.put(2, 30); // three of a kind
        payouts.put(1, 40); // straight flush

        if (eval == 0) return 0; // LOSER

        return bet + payouts.get(eval) * bet;
    }

    ;

    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player)
    {
        int dealerPlay = evalHand(dealer);
        int playerPlay = evalHand(player);

        // so that worst possible hand is lowest number
        if (playerPlay == 0) playerPlay = 7;
        if (dealerPlay == 0) dealerPlay = 7;

        if (playerPlay == dealerPlay)
        {
            dealer.sort((a, b) ->
            {
                return a.getValue() < b.getValue() ? 1 : -1;
            });
            player.sort((a, b) ->
            {
                return a.getValue() < b.getValue() ? 1 : -1;
            });

            // checking first card to see which is better
            if (dealer.get(0).getValue() > player.get(0).getValue())
            {
                return 1;
            } else if (dealer.get(0).getValue() < player.get(0).getValue())
            {
                return 2;
            }

            // if they were the same, check second card to see which is better
            if (dealer.get(1).getValue() > player.get(1).getValue())
            {
                return 1;
            } else if (dealer.get(1).getValue() < player.get(1).getValue())
            {
                return 2;
            }

            // if they were the same, check third card to see which is better
            if (dealer.get(2).getValue() > player.get(2).getValue())
            {
                return 1;
            } else if (dealer.get(2).getValue() < player.get(2).getValue())
            {
                return 2;
            }

            //in case of a tie
            return 0;
        }
        //dealer wins
        if (dealerPlay < playerPlay) return 1;

        //player wins
        return 2;
    }

    ;

}
