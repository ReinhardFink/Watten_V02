package watten.tests;

import junit.framework.TestCase;

public class Test_Card extends TestCase {

	private CardDeck deck;

	public void setUp() {
		deck = new CardDeck();
	}

	public void test_calcGuaterFromRechter() {
		assertEquals(true, deck.eA.calcGuaterFromRechter().equals(deck.e7));
		assertEquals(true, deck.hA.calcGuaterFromRechter().equals(deck.h7));
		assertEquals(true, deck.lA.calcGuaterFromRechter().equals(deck.l7));
		assertEquals(true, deck.sA.calcGuaterFromRechter().equals(deck.s7));
		assertEquals(true, deck.s6.calcGuaterFromRechter().equals(deck.s6));
	}
	
	public void test_calcRechterFromGuater() {
		assertEquals(true, deck.e7.calcRechterFromGuater().equals(deck.eA));
		assertEquals(true, deck.h7.calcRechterFromGuater().equals(deck.hA));
		assertEquals(true, deck.l7.calcRechterFromGuater().equals(deck.lA));
		assertEquals(true, deck.s7.calcRechterFromGuater().equals(deck.sA));
		assertEquals(true, deck.h9.calcRechterFromGuater().equals(deck.h8));
	}
}
