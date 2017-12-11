package watten.tests;

import watten.Rounds;
import watten.Round;
import watten.logic.Possibility;
import watten.logic.RechterStichTest;
import watten.logic.TrumpfStichTest;
import watten.logic.FarbStichTest;
import watten.logic.GameInfo;
import watten.logic.GuaterStichTest;
import watten.logic.LinkerStichTest;
import junit.framework.TestCase;

public class Test_VeliStichTest extends TestCase {

	CardDeck deck;
	Rounds stiche;

	public void setUp() {
		deck = new CardDeck();
		stiche = new Rounds();
	}

	public void test_VeliStich() {
		GameInfo result = new GameInfo(true);

		stiche.add(new Round(deck.s6, deck.h7, deck.l7, deck.e9, 0));
		assertEquals(Possibility.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(Possibility.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(Possibility.IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());
		
		stiche.clear();
		result = new GameInfo(true);
		stiche.add(new Round(deck.s6, deck.h7, deck.h8, deck.e9, 0));
		assertEquals(Possibility.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(Possibility.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(Possibility.IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());

		stiche.clear();
		result = new GameInfo(true);
		stiche.add(new Round(deck.s6, deck.sA, deck.l7, deck.e9, 0));
		assertEquals(Possibility.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		//assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(Possibility.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(Possibility.IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());
	}
	
	public void test_nichtVeliStich() {
		GameInfo result = new GameInfo(false);

		stiche.add(new Round(deck.s6, deck.h7, deck.l7, deck.e9, 1));
		assertEquals(Possibility.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		//assertEquals(POSSIBILITY.POSSIBLE, new GuaterStichTest(result, stiche).runTest());
		
		stiche.clear();
		result = new GameInfo(false);
		stiche.add(new Round(deck.s6, deck.s7, deck.h8, deck.e9, 1));
		assertEquals(Possibility.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(Possibility.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		//assertEquals(POSSIBILITY.POSSIBLE, new GuaterStichTest(result, stiche).runTest());
	}
		/*
		stiche.clear();
		result = new Result(false);
		stiche.add(new Stich(e7, e9, l7, e10, 1));
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.clear();
		result = new Result(true);
		stiche.add(new Stich(e7, e9, l7, e10, 1));
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		// möglicher Guater e10 wäre dann im Stich
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
	}
	
	public void test_runTest_serien_runTests() {
		System.out.println();
		System.out.println("serien_Tests Nr. 1");
		System.out.println("##########################################################");
		Result result = new Result(true);
		stiche.clear();
		stiche.add(new Stich(e7, hA, l7, e8, 3));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.POSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		stiche.add(new Stich(e9, sK, lU, e10, 3));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.POSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		System.out.println();
		System.out.println("serien_Tests Nr. 2");
		System.out.println("##########################################################");
		result = new Result(true);
		stiche.clear();
		stiche.add(new Stich(e7, hA, l7, e8, 1));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Stich(e9, sK, lU, e10, 3));
		result.calcSchlag(stiche);
		System.out.println(result.toString());
		assertEquals(POSSIBILITY.POSSIBLE, new FarbStichTest(result, stiche).runTest());
		// ab hier ist das Programm bereits besser als der Programmierer (benötigte Debugger)!
		// wenn e=Trumpf muss hA im ersten Stich linker sein => e7 im erstenStich Guater!
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		System.out.println();
		System.out.println("serien_Tests Nr. 3");
		System.out.println("##########################################################");
		result = new Result(true);
		// Idee Rechter: sU
		stiche.clear();
		stiche.add(new Stich(e7, sU, sA, sO, 3));
		result.calcSchlag(stiche);
		System.out.println("Stich Nr. 1");
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Stich(e9, sK, lU, e10, 2));
		result.calcSchlag(stiche);
		System.out.println("Stich Nr. 2");
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		System.out.println();
		System.out.println("serien_runTests Nr. 4");
		System.out.println("##########################################################");
		result = new Result(true);
		// Idee Rechter: sU
		stiche.clear();
		
		stiche.add(new Stich(e7, sO, lA, sK, 1));
		result.calcSchlag(stiche);
		System.out.println("Stich Nr.: 0 -----------------------");
		System.out.println(result.toString());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Stich(e9, sA, sU, e10, 2));
		result.calcSchlag(stiche);
		System.out.println("Stich Nr.: 1 -----------------------");
		System.out.println(result.toString());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Stich(e8, lK, lU, l8, 2));
		result.calcSchlag(stiche);
		System.out.println("Stich Nr.: 2 -----------------------");
		System.out.println(result.toString());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
		stiche.add(new Stich(s6, hK, l7, s10, 3));
		result.calcSchlag(stiche);
		System.out.println("Stich Nr.: 3 -----------------------");
		System.out.println(result.toString());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
	
	}
	
	public void test_runTest_Rechter_is_Fix() {
		System.out.println("Rechter_is_Fix_Tests Nr. 1");
		// Rechter: hK
		Result result = new Result(true);
		result.setNumberAt(CONSTANTS.KOENIG,CONSTANTS.SURE);
		result.setColorAt(CONSTANTS.HERZ,CONSTANTS.SURE);
		stiche.clear();
		// reiner Farbstich
		stiche.add(new Stich(e7, lA, l7, e8, 3));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.SURE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		// Rechter sticht
		stiche.add(new Stich(e10, eA, hK, hO, 2));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.SURE, new RechterStichTest(result, stiche).runTest());
		// Trumpf sticht
		stiche.add(new Stich(s6, hU, lO, s7, 1));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.SURE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		// Linker sticht
		stiche.add(new Stich(sA, lK, l10, eU, 1));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.SURE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		// Guater sticht
		stiche.add(new Stich(e9, sU, hA, h10, 2));
		result.calcSchlag(stiche);
		assertEquals(POSSIBILITY.IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBILITY.IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		
	}*/
}
