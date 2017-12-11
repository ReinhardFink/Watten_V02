package watten.tests;

import static watten.Suit.*;
import static watten.Rank.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import watten.Round;

public class Test_Round {

	private CardDeck _d;
	private Round r;
	
	@Before
	public final void setUp() {
		_d = new CardDeck();
	}
	
	@Test
	public final void testGetWinnerCard() {
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,0);
		assertEquals(_d.e7,r.getWinnerCard());
		
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,1);
		assertEquals(_d.e8,r.getWinnerCard());
		
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,2);
		assertEquals(_d.e9,r.getWinnerCard());
		
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,3);
		assertEquals(_d.e10,r.getWinnerCard());
	}

	@Test
	public final void testHasCard() {
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,0);
		assertTrue(r.hasCard(_d.e7));
		
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,1);
		assertTrue(r.hasCard(_d.e8));
		
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,2);
		assertFalse(r.hasCard(_d.eU));
		
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,3);
		assertFalse(r.hasCard(_d.h9));

	}

	@Test
	public final void testHasNumber() {
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,1);
		assertTrue(r.hasRank(SEVEN));
		assertTrue(r.hasRank(EIGHT));
		assertFalse(r.hasRank(JACK));
	}

	@Test
	public final void testHasSuit() {
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,1);
		assertTrue(r.hasSuit(ACORN));
		assertFalse(r.hasSuit(BELL));
		assertFalse(r.hasSuit(HEART));
	}

	@Test
	public final void testGetNumberOfSuits() {
		r = new Round(_d.e7,_d.e8,_d.e9,_d.e10,4);
		assertEquals(1,r.getNumberOfSuits());
		
		r = new Round(_d.h7,_d.e8,_d.e9,_d.e10,4);
		assertEquals(2,r.getNumberOfSuits());
		
		r = new Round(_d.h7,_d.e8,_d.e9,_d.h10,4);
		assertEquals(2,r.getNumberOfSuits());
	}

}
