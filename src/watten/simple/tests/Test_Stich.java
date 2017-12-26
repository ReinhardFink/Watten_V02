package watten.simple.tests;

import static watten.Suit.*;
import static watten.Rank.*;
import watten.simple.Round_simple;
import watten.tests.CardDeck;
import junit.framework.TestCase;

public class Test_Stich extends TestCase {
	CardDeck deck;
	
	public void setUp() {
		deck = new CardDeck();
	}
	
	public void test_findCard() {
		Round_simple stich = new Round_simple(deck.e7,deck.e8,deck.e9,deck.e10,1);
		assertEquals(true, stich.hasCard(deck.e7));
		assertEquals(false, stich.hasCard(deck.h7));
		assertEquals(true, stich.hasCard(deck.e10));
	}
	
	public void test_findNumber() {
		Round_simple stich = new Round_simple(deck.e7,deck.e8,deck.e9,deck.e10,1);
		assertEquals(true, stich.hasRank(SEVEN));
		assertEquals(true, stich.hasRank(EIGHT));
		assertEquals(true, stich.hasRank(NINE));
		assertEquals(true, stich.hasRank(TEN));
		
		stich = new Round_simple(deck.h7,deck.e7,deck.s8,deck.l8,1);
		assertEquals(true, stich.hasRank(SEVEN));
		assertEquals(true, stich.hasRank(EIGHT));
	}
	
	public void test_findColor() {
		Round_simple stich = new Round_simple(deck.e7,deck.e8,deck.e9,deck.e10,1);
		assertEquals(true, stich.hasSuit(ACORN));
		assertEquals(false, stich.hasSuit(LEAVE));
		assertEquals(false, stich.hasSuit(HEART));
		
		stich = new Round_simple(deck.h7,deck.e8,deck.s9,deck.l10,1);
		assertEquals(true, stich.hasSuit(ACORN));
		assertEquals(true, stich.hasSuit(LEAVE));
		assertEquals(true, stich.hasSuit(HEART));
		assertEquals(true, stich.hasSuit(BELL));
	}
	/*
	public void test_isWinnerColorBeated() {
		Stich stich = new Stich(e7,e8,e9,e10,1);
		assertEquals(true, stich.isWinnerColorBeated());
		
		stich = new Stich(e7,e8,e9,e10,3);
		assertEquals(false, stich.isWinnerColorBeated());
		
		stich = new Stich(e7,e8,e9,h10,2);
		assertEquals(false, stich.isWinnerColorBeated());
		
		stich = new Stich(e7,h8,h9,h10,0);
		assertEquals(false, stich.isWinnerColorBeated());
	}
	*/
}
