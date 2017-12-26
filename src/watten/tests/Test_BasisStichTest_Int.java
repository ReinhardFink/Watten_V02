package watten.tests;

import static watten.Suit.*;
import static watten.Rank.*;
import static watten.logic.Possibility.*;

import watten.Rounds;
import watten.Round;
import watten.old.BasisStichTest_Int;
import watten.logic.GameInfo;
import junit.framework.TestCase;

public class Test_BasisStichTest_Int extends TestCase {

	CardDeck deck;
	Rounds stiche;
	BasisStichTest_Int basisStichTest;
	
	public void setUp() {
		deck = new CardDeck();
		stiche = new Rounds();
	}
	
	public void test_testWinner_FarbStich() {
		GameInfo result = new GameInfo(false);
		Round r0 = new Round(deck.h7,deck.h8,deck.hK,deck.hA,3);
		stiche.add(r0);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.hA,basisStichTest.findWinnerPosInRound(r0));
		
		Round r1 = new Round(deck.hA,deck.h8,deck.hK,deck.hU,0);
		stiche.add(r1);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.hA,basisStichTest.findWinnerPosInRound(r1));
		
		Round r2 = new Round(deck.h10,deck.h8,deck.hK,deck.hU,2);
		stiche.add(r2);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.hK,basisStichTest.findWinnerPosInRound(r2));
	}
	
	public void test_testWinner_LinkerStich() {
		GameInfo result = new GameInfo(false);
		result.setRankAt(EIGHT.ordinal(),SURE) ;
		
		Round r0 = new Round(deck.h7,deck.h8,deck.hK,deck.hA,1);
		stiche.add(r0);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.h8,basisStichTest.findWinnerPosInRound(r0));
		
		Round r1 = new Round(deck.hA,deck.l9,deck.e8,deck.l8,2);
		stiche.add(r1);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.e8,basisStichTest.findWinnerPosInRound(r1));
	}
	
	public void test_testWinner_RechterStichtLinkenOhneGuaten() {
		GameInfo result = new GameInfo(false);
		result.setRankAt(EIGHT.ordinal(),SURE) ;
		result.setSuitAt(BELL.ordinal(),SURE) ;
		
		Round r0 = new Round(deck.h7,deck.h8,deck.hK,deck.hA,1);
		stiche.add(r0);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.h8,basisStichTest.findWinnerPosInRound(r0));
		
		Round r1 = new Round(deck.hA,deck.s9,deck.e8,deck.s8,3);
		stiche.add(r1);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.s8,basisStichTest.findWinnerPosInRound(r1));
		
		Round r2 = new Round(deck.s8,deck.l9,deck.e8,deck.h8,0);
		stiche.add(r2);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.s8,basisStichTest.findWinnerPosInRound(r2));
		
		//System.out.println("------------");
		Round r3 = new Round(deck.s8,deck.hU,deck.e10,deck.s9,0);
		stiche.add(r3);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		//assertEquals(0,basisStichTest.testWinner(3));
		//System.out.println(result.getRechter().number + " " + result.getRechter().color);
		assertEquals(deck.s8,basisStichTest.findWinnerPosInRound(r3));
		
		Round r4 = new Round(deck.s8,deck.hU,deck.e10,deck.sK,0);
		stiche.add(r4);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.s8,basisStichTest.findWinnerPosInRound(r4));
	}
	
	public void test_testWinner_RechterStichtLinkenMitGuaten() {
		GameInfo result = new GameInfo(true);
		result.setRankAt(EIGHT.ordinal(),SURE) ;
		result.setSuitAt(BELL.ordinal(),SURE);
		
		Round r0 = new Round(deck.h7,deck.h8,deck.hK,deck.hA,1);
		stiche.add(r0);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.h8,basisStichTest.findWinnerPosInRound(r0));
		
		Round r1 = new Round(deck.hA,deck.l9,deck.e8,deck.s8,3);
		stiche.add(r1);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.s8,basisStichTest.findWinnerPosInRound(r1));
		
		Round r2 = new Round(deck.s8,deck.l9,deck.e8,deck.h8,0);
		stiche.add(r2);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.s8,basisStichTest.findWinnerPosInRound(r2));
		
		Round r3 = new Round(deck.s9,deck.h8,deck.e8,deck.s8,0);
		stiche.add(r3);
		basisStichTest = new BasisStichTest_Int(result,stiche) { public void defineTest() {}; };
		assertEquals(deck.s9,basisStichTest.findWinnerPosInRound(r3));
	}
}
