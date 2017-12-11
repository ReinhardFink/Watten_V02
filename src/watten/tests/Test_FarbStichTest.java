package watten.tests;

import static watten.Suit.*;
import static watten.Rank.*;
import static watten.logic.Possibility.*;

import watten.Rounds;
import watten.Round;
import watten.logic.FarbStichTest;
import watten.logic.GameInfo;
import watten.logic.GameInfoCreator;
import watten.logic.GameInfoMessage;
import junit.framework.TestCase;

public class Test_FarbStichTest extends TestCase {

	CardDeck deck; 
	Rounds stiche;
	GameInfoCreator sc;

	public void setUp() {
		deck = new CardDeck();
		stiche = new Rounds();
	}

	public void test_runTest_erster_Stich_Farbstich() {
		GameInfo result = new GameInfo(false);
		stiche.add(new Round(deck.e7, deck.h7, deck.l7, deck.e9, 3));
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		System.out.println(GameInfoMessage.getVerboseMessage());
	
		result = new GameInfo(false);
		stiche.add(new Round(deck.eA, deck.h7, deck.l7, deck.e9, 0));
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		System.out.println(GameInfoMessage.getVerboseMessage());
		
		result = new GameInfo(false);
		stiche.add(new Round(deck.e10, deck.eA, deck.l7, deck.eU, 3));
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		System.out.println(GameInfoMessage.getVerboseMessage());
		
	}

	public void test_runTest_one_Color_is_Trumpf() {
		GameInfo result = new GameInfo(false);

		stiche.add(new Round(deck.e7, deck.h7, deck.l7, deck.l8, 0));
		result.setColorAt(ACORN.ordinal(), SURE);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());

		result.setColorAt(HEART.ordinal(), SURE);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());

		result.setColorAt(LEAVE.ordinal(), SURE);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());

		stiche.clear();
		stiche.add(new Round(deck.e7, deck.h7, deck.s7, deck.s8, 3));
		result.setColorAt(BELL.ordinal(), SURE);
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
	}

	public void test_runTest_4_Colors() {
		GameInfo result = new GameInfo(false);
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.h7, deck.l7, deck.s8, 0));
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
	}

	public void test_runTest_correct() {
		GameInfo result = new GameInfo(false);
		stiche.clear();
		stiche.add(new Round(deck.h7, deck.hA, deck.l7, deck.e8, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
	}

	public void test_runTest_serien_runTests() {
		System.out.println("serien_Tests Nr. 1");
		GameInfo result = new GameInfo(true);
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.hA, deck.l7, deck.e8, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		// hier folgt, dass im Falle eines Farbstiches e,s,l keine Trümpfe sein
		// können
		// => h = Trumpf und Widerspruch hA sticht nicht e8
		// e8 muss dann auf linken korrigiert werden!
		stiche.add(new Round(deck.e9, deck.sK, deck.lU, deck.e10, 3));
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		System.out.println("serien_Tests Nr. 2");
		result = new GameInfo(true);
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.hA, deck.l7, deck.e8, 1));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e9, deck.sK, deck.lU, deck.e10, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		System.out.println("serien_Test()()s Nr. 3");
		result = new GameInfo(true);
		// Idee Rechter: sU
		stiche.clear();
		stiche.add(new Round(deck.e7, deck.sU, deck.sA, deck.sO, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println("Stich Nr. 1");
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e9, deck.sK, deck.lU, deck.e10, 2));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println("Stich Nr. 2");
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		System.out.println();
		System.out.println("serien_runTests Nr. 4");
		System.out.println("##########################################################");
		result = new GameInfo(true);
		// Idee Rechter: sU
		stiche.clear();
		
		stiche.add(new Round(deck.e7, deck.sO, deck.lA, deck.sK, 1));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println("Stich Nr.: 0 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e9, deck.sA, deck.sU, deck.e10, 2));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println("Stich Nr.: 1 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.e8, deck.lK, deck.lU, deck.l8, 2));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println("Stich Nr.: 2 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		
		stiche.add(new Round(deck.s6, deck.hK, deck.l7, deck.s10, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println("Stich Nr.: 3 -----------------------");
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
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
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(SURE, new FarbStichTest(result, stiche).runTest());
		// Rechter sticht
		stiche.add(new Round(deck.e10, deck.eA, deck.hK, deck.hO, 2));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		// Trumpf sticht
		stiche.add(new Round(deck.s6, deck.hU, deck.lO, deck.s7, 1));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		// Linker sticht
		stiche.add(new Round(deck.lA, deck.lK, deck.l10, deck.eU, 1));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
	}

}
