package watten.tests;

import static watten.Suit.*;
import static watten.Rank.*;
import static watten.logic.Possibility.*;

import watten.Card;
import watten.logic.GameInfo;
import junit.framework.TestCase;

public class Test_CardRelation extends TestCase {
	
	CardDeck deck;

	public void setUp() {
		deck = new CardDeck();
	}


	public void test_beats() {
		GameInfo result = new GameInfo(true);
		// GUATER: LEAF-JACK
		// RECHETR LEAF-TEN
		result.setRankAt(TEN.ordinal(),SURE);
		result.setSuitAt(LEAVE.ordinal(),SURE);
		Card guater = new Card(deck.lU);
		Card rechter = new Card(deck.l10);
		Card linker1 = new Card(deck.e10);
		Card linker2 = new Card(deck.h10);
		Card linker3 = new Card(deck.s10);
		Card _e7 = new Card(deck.e7);
		@SuppressWarnings("unused")
		Card _hU = new Card(deck.hU);
		Card _hK = new Card(deck.hK);
		Card _hA = new Card(deck.hA);
		Card _lO = new Card(deck.lO);
		Card _lK = new Card(deck.lK);
		Card veli = new Card(deck.s6);
		@SuppressWarnings("unused")
		Card _s9 = new Card(deck.s9);
		Card _sU = new Card(deck.sU);
		Card _sK = new Card(deck.sK);
		@SuppressWarnings("unused")
		Card _sA = new Card(deck.sA);
		// Tests für Guater, Rechter(l10), Linker untereinander
		assertEquals(true,guater.beats(rechter,result)); 
		assertEquals(false,rechter.beats(guater,result));
		assertEquals(true,guater.beats(linker1,result)); 
		assertEquals(false,linker1.beats(guater,result));
		assertEquals(true,guater.beats(linker2,result)); 
		assertEquals(false,linker2.beats(guater,result));
		assertEquals(true,guater.beats(linker3,result)); 
		assertEquals(false,linker3.beats(guater,result));
		assertEquals(true,rechter.beats(linker1,result)); 
		assertEquals(false,linker1.beats(rechter,result));
		assertEquals(true,rechter.beats(linker2,result)); 
		assertEquals(false,linker2.beats(rechter,result));
		assertEquals(true,rechter.beats(linker3,result)); 
		assertEquals(false,linker3.beats(rechter,result));
		assertEquals(true,linker1.beats(linker2,result));
		assertEquals(true,linker1.beats(linker3,result));
		assertEquals(true,linker2.beats(linker1,result));
		assertEquals(true,linker2.beats(linker3,result));
		assertEquals(true,linker3.beats(linker1,result));
		assertEquals(true,linker3.beats(linker2,result));
		assertEquals(true,linker2.beats(veli,result));
		assertEquals(false,veli.beats(linker2,result));
		assertEquals(true,linker3.beats(veli,result));
		assertEquals(false,veli.beats(linker3,result));
		// Tests für Guater, Rechter(l10), Linker auf Trumpf
		assertEquals(true,guater.beats(_lO,result));
		assertEquals(false,_lO.beats(guater,result));
		assertEquals(true,rechter.beats(_lO,result)); 
		assertEquals(false,_lO.beats(rechter,result));
		assertEquals(true,linker3.beats(_lO,result)); 
		assertEquals(false,_lO.beats(linker3,result));
		// Tests für Guater, Rechter(l10), Linker auf Farbe
		assertEquals(true,guater.beats(_hK,result));
		assertEquals(false,_hK.beats(guater,result));
		assertEquals(true,rechter.beats(_hK,result)); 
		assertEquals(false,_hK.beats(rechter,result));
		assertEquals(true,linker3.beats(_hK,result)); 
		assertEquals(false,_hK.beats(linker3,result));
		// Tests Trumpf auf Trumpf
		assertEquals(true,_lK.beats(_lO,result));
		assertEquals(false,_lO.beats(_lK,result));
		assertEquals(true,rechter.beats(_hK,result)); 
		assertEquals(false,_hK.beats(rechter,result));
		assertEquals(true,linker3.beats(_hK,result)); 
		assertEquals(false,_hK.beats(linker3,result));
		assertEquals(true,_lK.beats(veli,result));
		assertEquals(false,veli.beats(_lK,result));
		// Trumpf auf Farbe
		assertEquals(true,veli.beats(_hK,result));
		assertEquals(false,_hK.beats(veli,result));
		assertEquals(true,_lK.beats(_hK,result)); 
		assertEquals(false,_hK.beats(_lK,result));
		assertEquals(true,_lO.beats(_hK,result)); 
		assertEquals(false,_hK.beats(_lO,result));
		// Farbe auf verschiedene Farbe
		assertEquals(true,_e7.beats(_hA,result));
		assertEquals(true,_hA.beats(_e7,result));
		// Farbe auf Farbe
		assertEquals(true,_sK.beats(_sU,result));
		assertEquals(false,_sU.beats(_sK,result));
		assertEquals(true,_sK.beats(_hK,result)); 
		assertEquals(true,_hK.beats(_sK,result));
		
	}
}
