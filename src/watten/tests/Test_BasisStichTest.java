package watten.tests;

import static watten.Suit.*;
import static watten.Rank.*;
import static watten.logic.Possibility.*;

import watten.Rounds;
import watten.Round;
import watten.logic.BasisStichTest;
import watten.logic.GameInfo;
import junit.framework.TestCase;

public class Test_BasisStichTest extends TestCase {

	CardDeck deck;
	Rounds stiche;
	BasisStichTest basisStichTest;
	
	public void setUp() {
		deck = new CardDeck();
		stiche = new Rounds();
	}
	
	public void test_testWinner_FarbStich() {
		GameInfo result = new GameInfo(false);
		stiche.add(new Round(deck.h7,deck.h8,deck.hK,deck.hA,3));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(3,basisStichTest.findWinnerPosInRound(0));
		
		stiche.add(new Round(deck.hA,deck.h8,deck.hK,deck.hU,0));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(0,basisStichTest.findWinnerPosInRound(1));
		
		stiche.add(new Round(deck.h10,deck.h8,deck.hK,deck.hU,2));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(2,basisStichTest.findWinnerPosInRound(2));
	}
	
	public void test_testWinner_LinkerStich() {
		GameInfo result = new GameInfo(false);
		result.setNumberAt(EIGHT.ordinal(),SURE) ;
		stiche.add(new Round(deck.h7,deck.h8,deck.hK,deck.hA,3));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(1,basisStichTest.findWinnerPosInRound(0));
		
		stiche.add(new Round(deck.hA,deck.l9,deck.e8,deck.l8,2));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(2,basisStichTest.findWinnerPosInRound(1));
	}
	
	public void test_testWinner_RechterStichtLinkenOhneGuaten() {
		GameInfo result = new GameInfo(false);
		result.setNumberAt(EIGHT.ordinal(),SURE) ;
		result.setColorAt(BELL.ordinal(),SURE) ;
		
		stiche.add(new Round(deck.h7,deck.h8,deck.hK,deck.hA,3));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(1,basisStichTest.findWinnerPosInRound(0));
		
		stiche.add(new Round(deck.hA,deck.s9,deck.e8,deck.s8,3));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(3,basisStichTest.findWinnerPosInRound(1));
		
		stiche.add(new Round(deck.s8,deck.l9,deck.e8,deck.h8,0));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(0,basisStichTest.findWinnerPosInRound(2));
		
		//System.out.println("------------");
		stiche.add(new Round(deck.s8,deck.hU,deck.e10,deck.s9,0));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		//assertEquals(0,basisStichTest.testWinner(3));
		//System.out.println(result.getRechter().number + " " + result.getRechter().color);
		assertEquals(0,basisStichTest.findWinnerPosInRound(3));
		
		stiche.add(new Round(deck.s8,deck.hU,deck.e10,deck.sK,0));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(0,basisStichTest.findWinnerPosInRound(4));
	}
	
	public void test_testWinner_RechterStichtLinkenMitGuaten() {
		GameInfo result = new GameInfo(true);
		result.setNumberAt(EIGHT.ordinal(),SURE) ;
		result.setColorAt(BELL.ordinal(),SURE);
		
		stiche.add(new Round(deck.h7,deck.h8,deck.hK,deck.hA,3));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(1,basisStichTest.findWinnerPosInRound(0));
		
		stiche.add(new Round(deck.hA,deck.l9,deck.e8,deck.s8,3));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(3,basisStichTest.findWinnerPosInRound(1));
		
		stiche.add(new Round(deck.s8,deck.l9,deck.e8,deck.h8,0));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(0,basisStichTest.findWinnerPosInRound(2));
		
		stiche.add(new Round(deck.s9,deck.h8,deck.e8,deck.s8,0));
		basisStichTest = new BasisStichTest(result,stiche) { public void defineTest() {}; };
		assertEquals(0,basisStichTest.findWinnerPosInRound(3));
	}
}
