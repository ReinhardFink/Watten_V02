package watten.tests;

import static watten.Suit.*;
import static watten.Rank.*;
import static watten.logic.Possibility.*;


import watten.Rounds;
import watten.Round;
import watten.logic.RechterStichTest;
import watten.logic.TrumpfStichTest;
import watten.logic.FarbStichTest;
import watten.logic.GameInfo;
import watten.logic.GameInfoCreator;
import watten.logic.LinkerStichTest;
import junit.framework.TestCase;

public class Test_RechterStichTest extends TestCase {

	CardDeck deck;
	Rounds stiche;
	GameInfoCreator sc;

	public void setUp() {
		deck = new CardDeck();
		stiche = new Rounds();
	}

	public void test_runTest_erster_Stich_Rechterstich() {
		GameInfo result = new GameInfo(false);

		stiche.add(new Round(deck.e7, deck.h7, deck.l7, deck.e9, 3));
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.h7, deck.l7, deck.e9, 0));
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());

		stiche.clear();
		result = new GameInfo(false);
		stiche.add(new Round(deck.e7, deck.h7, deck.l7, deck.e9, 1));
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.clear();
		result = new GameInfo(false);
		stiche.add(new Round(deck.e7, deck.e9, deck.l7, deck.e10, 1));
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.clear();
		result = new GameInfo(true);
		stiche.add(new Round(deck.e7, deck.e9, deck.l7, deck.e10, 1));
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		// möglicher Guater e10 wäre dann im Stich
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
	}
	
	public void test_runTest_serien_runTests() {
		System.out.println();
		System.out.println("serien_Tests Nr. 1");
		System.out.println("##########################################################");
		GameInfo result = new GameInfo(true);
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.hA, deck.l7, deck.e8, 3));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		stiche.add(new Round(deck.e9, deck.sK, deck.lU, deck.e10, 3));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		System.out.println();
		System.out.println("serien_Tests Nr. 2");
		System.out.println("##########################################################");
		result = new GameInfo(true);
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.hA, deck.l7, deck.e8, 1));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e9, deck.sK, deck.lU, deck.e10, 3));
		sc = new GameInfoCreator(stiche, result);
		System.out.println(result.toString());
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		// ab hier ist das Programm bereits besser als der Programmierer (benötigte Debugger)!
		// wenn e=Trumpf muss hA im ersten Stich linker sein => e7 im erstenStich Guater!
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		System.out.println();
		System.out.println("serien_Tests Nr. 3");
		System.out.println("##########################################################");
		result = new GameInfo(true);
		// Idee Rechter: sU
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.sU, deck.sA, deck.sO, 3));
		sc = new GameInfoCreator(stiche, result);
		System.out.println("Stich Nr. 1");
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e9, deck.sK, deck.lU, deck.e10, 2));
		sc = new GameInfoCreator(stiche, result);
		System.out.println("Stich Nr. 2");
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		System.out.println();
		System.out.println("serien_runTests Nr. 4");
		System.out.println("##########################################################");
		result = new GameInfo(true);
		// Idee Rechter: sU
		stiche.clear();
		
		stiche.add(new Round(deck.e7, deck.sO, deck.lA, deck.sK, 1));
		sc = new GameInfoCreator(stiche, result);
		System.out.println("Stich Nr.: 0 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e9, deck.sA, deck.sU, deck.e10, 2));
		sc = new GameInfoCreator(stiche, result);
		System.out.println("Stich Nr.: 1 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e8, deck.lK, deck.lU, deck.l8, 2));
		sc = new GameInfoCreator(stiche, result);
		System.out.println("Stich Nr.: 2 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.s6, deck.hK, deck.l7, deck.s10, 3));
		sc = new GameInfoCreator(stiche, result);
		System.out.println("Stich Nr.: 3 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
	
	}
	
	public void test_runTest_Rechter_is_Fix() {
		System.out.println("Rechter_is_Fix_Tests Nr. 1");
		// Rechter: hK
		GameInfo result = new GameInfo(true);
		result.setNumberAt(KING.ordinal(),SURE);
		result.setColorAt(HEART.ordinal(),SURE);
		stiche.clear();
		// reiner Farbstich
		stiche.add(new Round(deck.e7, deck.lA, deck.l7, deck.e8, 3));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(SURE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		// Rechter sticht
		stiche.add(new Round(deck.e10, deck.eA, deck.hK, deck.hO, 2));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(SURE, new RechterStichTest(result, stiche).runTest());
		// Trumpf sticht
		stiche.add(new Round(deck.s6, deck.hU, deck.lO, deck.s7, 1));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(SURE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		// Linker sticht
		stiche.add(new Round(deck.sA, deck.lK, deck.l10, deck.eU, 1));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(SURE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		// Guater sticht
		stiche.add(new Round(deck.e9, deck.sU, deck.hA, deck.h10, 2));
		sc = new GameInfoCreator(stiche, result);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
	}
}
