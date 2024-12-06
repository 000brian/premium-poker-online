import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ThreeCardLogicTest
{
	@Test
	void straightFlushTest() {

		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 5));
		hand.add(new Card('S', 6));
		hand.add(new Card('S', 7));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int eval = threeCardLogic.evalHand(hand);

		assertEquals(1, eval);
	}

	@Test
	void threeOfAKindTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 5));
		hand.add(new Card('H', 5));
		hand.add(new Card('D', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int eval = threeCardLogic.evalHand(hand);

		assertEquals(2, eval);
	}

	@Test
	void straightTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 7));
		hand.add(new Card('H', 6));
		hand.add(new Card('D', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int eval = threeCardLogic.evalHand(hand);

		assertEquals(3, eval);
	}

	@Test
	void flushTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 8));
		hand.add(new Card('S', 6));
		hand.add(new Card('S', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int eval = threeCardLogic.evalHand(hand);

		assertEquals(4, eval);
	}

	@Test
	void pairTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 8));
		hand.add(new Card('H', 8));
		hand.add(new Card('S', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int eval = threeCardLogic.evalHand(hand);

		assertEquals(5, eval);
	}

	@Test
	void nothingTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 8));
		hand.add(new Card('H', 10));
		hand.add(new Card('S', 2));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int eval = threeCardLogic.evalHand(hand);

		assertEquals(0, eval);
	}

	@Test
	void straightFlushPayoutTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 5));
		hand.add(new Card('S', 6));
		hand.add(new Card('S', 7));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int payout = threeCardLogic.evalPPWinnings(hand, 20);

		assertEquals(payout, 20*41);
	}

	@Test
	void threeOfAKindPayoutTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 5));
		hand.add(new Card('H', 5));
		hand.add(new Card('D', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int payout = threeCardLogic.evalPPWinnings(hand, 20);

		assertEquals(payout, 20*31);
	}

	@Test
	void straightPayoutTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 7));
		hand.add(new Card('H', 6));
		hand.add(new Card('D', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int payout = threeCardLogic.evalPPWinnings(hand, 20);

		assertEquals(payout, 7*20);
	}

	@Test
	void flushPayoutTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 8));
		hand.add(new Card('S', 6));
		hand.add(new Card('S', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int payout = threeCardLogic.evalPPWinnings(hand, 20);

		assertEquals(payout, 4*20);
	}

	@Test
	void pairPayoutTest()
	{
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card('S', 8));
		hand.add(new Card('H', 8));
		hand.add(new Card('S', 5));
		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int payout = threeCardLogic.evalPPWinnings(hand, 20);

		assertEquals(payout, 2*20);
	}

	@Test 
	void compareHandsStraightFlushVsThreeOfAKindTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 10));
		dealer.add(new Card('S', 9));
		dealer.add(new Card('S', 8));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('H', 5));
		player.add(new Card('D', 5));
		player.add(new Card('C', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(1, result);
	}

	@Test
	void compareHandsThreeOfAKindVsStraightTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 5));
		dealer.add(new Card('H', 5));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 7));
		player.add(new Card('H', 6));
		player.add(new Card('D', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(1, result);
	}

	@Test
	void compareHandsStraightVsFlushTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 7));
		dealer.add(new Card('H', 6));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 8));
		player.add(new Card('S', 6));
		player.add(new Card('S', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(1, result);
	}

	@Test
	void compareHandsFlushVsPairTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 8));
		dealer.add(new Card('H', 8));
		dealer.add(new Card('S', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 8));
		player.add(new Card('S', 6));
		player.add(new Card('S', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(2, result);
	}

	@Test
	void compareHandsEqualStraightFlushesTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 5));
		dealer.add(new Card('S', 6));
		dealer.add(new Card('S', 7));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('H', 5));
		player.add(new Card('H', 6));
		player.add(new Card('H', 7));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(0, result);
	}

	@Test
	void compareHandsEqualThreeOfAKindsTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 5));
		dealer.add(new Card('H', 5));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 5));
		player.add(new Card('H', 5));
		player.add(new Card('D', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(0, result);
	}

	@Test
	void compareHandsEqualStraightsTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 7));
		dealer.add(new Card('H', 6));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 7));
		player.add(new Card('H', 6));
		player.add(new Card('D', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(0, result);
	}

	@Test
	void compareHandsEqualFlushesTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 8));
		dealer.add(new Card('S', 6));
		dealer.add(new Card('S', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 8));
		player.add(new Card('S', 6));
		player.add(new Card('S', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(0, result);
	}

	@Test
	void compareHandsEqualPairsTest()
	{
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 8));
		dealer.add(new Card('H', 8));
		dealer.add(new Card('S', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 8));
		player.add(new Card('H', 8));
		player.add(new Card('S', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(0, result);
	}

	@Test
	void compareHandsDealerWinsWithSpecialTest(){
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 5));
		dealer.add(new Card('H', 5));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('H', 2));
		player.add(new Card('H', 3));
		player.add(new Card('S', 8));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(1, result);
	}

	@Test
	void compareHandsPlayerWinsWithSpecialTest(){
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('D', 6));
		dealer.add(new Card('H', 2));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('H', 8));
		player.add(new Card('H', 7));
		player.add(new Card('S', 6));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(player, dealer);

		assertEquals(1, result);
	}

	@Test 
	void compareHandsDealerWinsNoSpecialTest(){
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 9));
		dealer.add(new Card('H', 3));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 2));
		player.add(new Card('H', 3));
		player.add(new Card('S', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(dealer, player);

		assertEquals(1, result);
	}

	@Test 
	void compareHandsPlayerWinsNoSpecialTest(){
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 2));
		dealer.add(new Card('H', 3));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('S', 9));
		player.add(new Card('H', 3));
		player.add(new Card('S', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(player, dealer);

		assertEquals(1, result);
	}

	@Test
	void compareHandsEqualNoSpecialTest(){
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card('S', 2));
		dealer.add(new Card('H', 3));
		dealer.add(new Card('D', 5));

		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card('D', 2));
		player.add(new Card('S', 3));
		player.add(new Card('H', 5));

		ThreeCardLogic threeCardLogic = new ThreeCardLogic();
		int result = threeCardLogic.compareHands(player, dealer);

		assertEquals(0, result);
	}
}
