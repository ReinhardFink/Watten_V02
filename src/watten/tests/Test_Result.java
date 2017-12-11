package watten.tests;


import watten.Card;
import watten.logic.Possibility;
import watten.logic.GameInfo;
import watten.logic.GameInfoCreator;
import junit.framework.TestCase;

import static watten.Suit.*;
import static watten.Rank.*;

public class Test_Result extends TestCase {
	
	CardDeck d_;
	GameInfoCreator sc;
	
	public void setUp() {
		d_ = new CardDeck();
	}
	
	public void test_clone() {
		try {
			GameInfo result = new GameInfo(false);
			
			result.setNumberAt(KING.ordinal(),Possibility.SURE);
			assertEquals(true,result.isSchlagFix());
			assertEquals(KING,result.getSchlag());
			// klonen
			GameInfo resultClone = result.clone();
			// hat der Clone die Daten mitbekommen?
			assertEquals(true,resultClone.isSchlagFix());
			assertEquals(KING,resultClone.getSchlag());
			// resultClone wird nun verändert!
			resultClone.setNumberAt(KNIGHT.ordinal(),Possibility.SURE);
			// Test, ob result unverändert!
			assertEquals(true,result.isSchlagFix());
			assertEquals(KING,result.getSchlag());
			
			// Test, ob resultClone verändert
			assertEquals(true,resultClone.isSchlagFix());
			assertEquals(KNIGHT,resultClone.getSchlag());
			
		} catch(CloneNotSupportedException e) { 
			System.out.println("PossibilityArray konnte nicht geklont werden!");
		}
	}
	
	public void test_isSchlagFix_getSchlag() {
		GameInfo result = new GameInfo(false);
		result.setNumberAt(KING.ordinal(),Possibility.SURE);
		assertEquals(true,result.isSchlagFix());
		assertEquals(KING,result.getSchlag());
		
		result = new GameInfo(false);
		result.setNumberAt(SIX.ordinal(),Possibility.IMPOSSIBLE);
		result.setNumberAt(SEVEN.ordinal(),Possibility.IMPOSSIBLE);
		assertEquals(false,result.isSchlagFix());
		//assertEquals(-1,result.getSchlag());
		
		result.setNumberAt(SIX.ordinal(),Possibility.SURE);
		assertEquals(true,result.isSchlagFix());
		assertEquals(SIX,result.getSchlag());
		
		result.setNumberAt(SIX.ordinal(),Possibility.IMPOSSIBLE);
		result.setNumberAt(ACE.ordinal(),Possibility.SURE);
		assertEquals(true,result.isSchlagFix());
		assertEquals(ACE,result.getSchlag());
	}
	
	public void test_isTrumpfFix_getTrumpf() {
		GameInfo result = new GameInfo(false);
	
		assertEquals(false,result.isTrumpfFix());
		//assertEquals(-1,result.getTrumpf());
		
		result.setColorAt(ACORN.ordinal(),Possibility.IMPOSSIBLE);
		result.setColorAt(HEART.ordinal(),Possibility.IMPOSSIBLE);
		assertEquals(false,result.isTrumpfFix());
		//assertEquals(-1,result.getTrumpf());
		
		result.setColorAt(ACORN.ordinal(),Possibility.SURE);
		assertEquals(true,result.isTrumpfFix());
		assertEquals(ACORN,result.getTrumpf());
		
		result.setColorAt(BELL.ordinal(),Possibility.SURE);
		assertEquals(true,result.isTrumpfFix());
		assertEquals(BELL,result.getTrumpf());
	}
	
	public void test_isRechterFix() {
		GameInfo result = new GameInfo(false);
		
		assertEquals(false,result.isRechterFix());
		
		result.setColorAt(ACORN.ordinal(),Possibility.SURE);
		assertEquals(false,result.isRechterFix());
		
		result.setNumberAt(ACE.ordinal(),Possibility.SURE);
		assertEquals(true,result.isRechterFix());
	}
	
	public void test_getRechter() {
		GameInfo result = new GameInfo(false);
		
		result.setColorAt(ACORN.ordinal(),Possibility.SURE);
		result.setNumberAt(ACE.ordinal(),Possibility.SURE);
		Card rechter = new Card(ACORN,ACE);
		assertEquals(true,result.getRechter().equals(rechter));
		
		result.setColorAt(BELL.ordinal(),Possibility.SURE);
		result.setNumberAt(SIX.ordinal(),Possibility.SURE);
		rechter = new Card(BELL,SIX);
		assertEquals(true,result.getRechter().equals(rechter));
	}
	
	public void test_getGuater() {
		GameInfo result = new GameInfo(true);
		
		result.setColorAt(ACORN.ordinal(),Possibility.SURE);
		result.setNumberAt(ACE.ordinal(),Possibility.SURE);
		Card guater = new Card(ACORN,SEVEN);
		assertEquals(true,result.getGuater().equals(guater));
		
		result.setColorAt(BELL.ordinal(),Possibility.SURE);
		result.setNumberAt(ACE.ordinal(),Possibility.SURE);
		guater = new Card(BELL,SEVEN);
		assertEquals(true,result.getGuater().equals(guater));
	}
	

}
