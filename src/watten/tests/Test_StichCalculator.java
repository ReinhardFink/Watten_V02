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
import watten.logic.GuaterStichTest;
import watten.logic.LinkerStichTest;
import junit.framework.TestCase;

public class Test_StichCalculator extends TestCase {

	CardDeck d_;
	Rounds stiche;
	GameInfoCreator sc;
	
	public void setUp() {
		d_ = new CardDeck();	
		stiche = new Rounds();

	}
	
	public void test_calcSchlag_without_Guater() {
		
		GameInfo result = new GameInfo(false);
		stiche.clear();
		// Rechter eK
		stiche.add(new Round(d_.hK,d_.sU,d_.sA,d_.h9,0));	//6~,7~,8~,9-,10~,U-,O~,K~,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(false,result.isSchlagFix());
		stiche.add(new Round(d_.lO,d_.s6,d_.e8,d_.e10,3));	//6-,7~,8-,9-,10~,U-,O-,K~,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(false,result.isSchlagFix());
		stiche.add(new Round(d_.l7,d_.s10,d_.eA,d_.eK,3));	//6-,7-,8-,9-,10-,U-,O-,K~,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(true,result.isSchlagFix());
		
		result = new GameInfo(false);
		stiche.clear();
		// Rechter eK
		stiche.add(new Round(d_.hO,d_.sU,d_.sA,d_.h9,0));	//6~,7~,8~,9-,10~,U-,O~,K~,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		result = new GameInfo(false);
		stiche.clear();
		// Rechter lA
		stiche.add(new Round(d_.eU,d_.sO,d_.e7,d_.eK,3));	//6~,7-,8~,9~,10~,U-,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));	
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
	}
	
	public void test_calcSchlag_with_Guater_SingleTests() {
		GameInfo result = new GameInfo(true);
		stiche.clear();
		// Rechter eK
		stiche.add(new Round(d_.eO,d_.sU,d_.sA,d_.h9,0));	//6~,7~,8~,9-,10~,U~,O~,K~,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		//---------------------------------------
		result = new GameInfo(true);
		stiche.clear();
		// Guater l7 Rechter lA
		stiche.add(new Round(d_.l7,d_.s6,d_.e8,d_.e10,0));	//6-,7~,8-,9~,10-,U~,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		//---------------------------------------
		result = new GameInfo(true);
		stiche.clear();
		// Guater l7 Rechter lA
		stiche.add(new Round(d_.l7,d_.s6,d_.e8,d_.eA,0));	//6-,7~,8-,9~,10~,U~,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		//---------------------------------------
		result = new GameInfo(true);
		result.setSuitAt(BELL.ordinal(),POSSIBLE);
		stiche.clear();
		//  Rechter s7
		stiche.add(new Round(d_.s7,d_.s6,d_.e8,d_.eA,0));	//6-,7~,8-,9~,10~,U~,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		//---------------------------------------
		result = new GameInfo(true);
		result.setSuitAt(BELL.ordinal(),POSSIBLE);
		stiche.clear();
		// Rechter s7
		stiche.add(new Round(d_.s7,d_.h7,d_.e8,d_.eA,0));	//6-,7~,8-,9~,10~,U~,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		
		stiche.clear();
		stiche.add(new Round(d_.s7,d_.s8,d_.s9,d_.s10,0));	//6~,7~,8-,9-,10-,U~,O~,K~,A~
		result = new GameInfo(true);
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
	}
	
	public void test_calcSchlag_with_Guater_SerienTests() {
		GameInfo result = new GameInfo(true);
		stiche.clear();
		// Rechter eK
		stiche.add(new Round(d_.eO,d_.sU,d_.sA,d_.h9,0));	//6~,7~,8~,9-,10~,U~,O~,K~,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.lO,d_.s6,d_.e8,d_.e10,3));	//6-,7~,8-,9-,10~,U~,O-,K~,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		stiche.add(new Round(d_.l7,d_.s10,d_.eU,d_.eK,3));	//6-,7-,8-,9-,10-,U-,O-,K+,A-
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(SURE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(true,result.isSchlagFix());
		//---------------------------------------
		// neues Spiel
		result = new GameInfo(true);
		stiche.clear();
		// Guater l7 Rechter lA
		stiche.add(new Round(d_.l7,d_.sU,d_.hA,d_.h9,0));	//6~,7~,8~,9-,10~,U-,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		// As bleibt durch 7 geschützt
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.h8,d_.s6,d_.eO,d_.e9,1));	//6~,7~,8-,9-,10~,U-,O-,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.s8,d_.s10,d_.sA,d_.lK,2));	//6~,7~,8-,9-,10-,U-,O-,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.lO,d_.l10,d_.eO,d_.eK,0));	//6~,7~,8-,9-,10-,U-,O-,K-,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.eA,d_.h10,d_.e7,d_.eK,0));	//6~,7-,8-,9-,10-,U-,O-,K-,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		//---------------------------------------
		// neues Spiel
		result = new GameInfo(true);
		stiche.clear();
		// Guater l7 Rechter lA
		stiche.add(new Round(d_.eU,d_.sO,d_.e7,d_.eK,3));	//6~,7-,8~,9~,10~,U-,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		// Ober wird von König geschützt		
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.lK,d_.s6,d_.eA,d_.h8,2));	//6-,7-,8-,9~,10~,U-,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		// Ober wird von König beschützt, der wiederum von As beschützt wird!
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		// König wird von As beschütz
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.s9,d_.h10,d_.hK,d_.l7,3));	//6-,7-,8-,9-,10-,U-,O-,K-,A+
		result.setSuitAt(LEAVE.ordinal(),SURE);
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(SURE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(true,result.isSchlagFix());
		
		//---------------------------------------
		// neues Spiel
		result = new GameInfo(true);
		stiche.clear();
		// Guater lK Rechter lO
		stiche.add(new Round(d_.eK,d_.lO,d_.e7,d_.lK,3));	//6~,7-,8~,9~,10~,U~,O~,K~,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));	
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.hK,d_.lU,d_.e8,d_.s10,1));	//6~,7-,8-,9~,10~,U~,O~,K-,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));	
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		//---------------------------------------
		// neues Spiel
		result = new GameInfo(true);
		stiche.clear();
		// Guater lK Rechter lO
		stiche.add(new Round(d_.e7,d_.s10,d_.e8,d_.sK,2));	//6~,7~,8~,9~,10-,U~,O~,K-,A~
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));	
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		stiche.add(new Round(d_.l8,d_.lU,d_.h9,d_.lO,3));	//6~,7~,8-,9-,10-,U~,O~,K-,A~
		//result.setColorAt(CONSTANTS.LAUB,SURE);
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));	
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
		//selbe Auswerung wie oben, aber Laub als Schlag fix; 
		//7 muss nun wegen erstem 8 schlägt 7 herausfliegen
		result.setSuitAt(LEAVE.ordinal(),SURE);
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE,result.getRankPossibilityAt(SIX.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(SEVEN.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(EIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(NINE.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(TEN.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(JACK.ordinal()));	
		assertEquals(POSSIBLE,result.getRankPossibilityAt(KNIGHT.ordinal()));
		assertEquals(IMPOSSIBLE,result.getRankPossibilityAt(KING.ordinal()));
		assertEquals(POSSIBLE,result.getRankPossibilityAt(ACE.ordinal()));
		assertEquals(false,result.isSchlagFix());
		
	}
	
	public void test_runTest_1() {
		System.out.println();
		System.out.println("serien_Tests Nr. 1");
		System.out.println("##########################################################");
		System.out.println();
		System.out.println("Stich Nr. 1");
		System.out.println();
		Rounds stiche = new Rounds();
		GameInfo result =  new GameInfo(true);
		GameInfoCreator stichCalculator = new GameInfoCreator(stiche,result);
		stiche.add(new Round(d_.e7, d_.hA, d_.l7, d_.e8, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(POSSIBLE, stichCalculator.isFarbStich());
		assertEquals(POSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(POSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(POSSIBLE, stichCalculator.isRechterStich());
		assertEquals(POSSIBLE, stichCalculator.isGuaterStich());
		System.out.println();
		System.out.println("Stich Nr. 2");
		System.out.println();
		stiche.add(new Round(d_.e9, d_.sK, d_.lU, d_.e10, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		// hier folgt, dass im Falle eines Farbstiches e,s,l keine Trümpfe sein
		// können
		// => h = Trumpf und Widerspruch hA sticht nicht e8
		// e8 muss dann auf linken korrigiert werden!
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(POSSIBLE, stichCalculator.isFarbStich());
		assertEquals(POSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(POSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(POSSIBLE, stichCalculator.isRechterStich());
		assertEquals(POSSIBLE, stichCalculator.isGuaterStich());
	}
	
	public void test_runTest_2() {
		System.out.println();
		System.out.println("Serien Test Nr. 2");
		System.out.println("##########################################################");
		System.out.println();
		System.out.println("Stich Nr. 1");
		System.out.println();
		Rounds stiche = new Rounds();
		GameInfo result =  new GameInfo(true);
		GameInfoCreator stichCalculator = new GameInfoCreator(stiche,result);
		stiche.add(new Round(d_.e7, d_.hA, d_.l7, d_.e8, 1));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(IMPOSSIBLE, stichCalculator.isFarbStich());
		assertEquals(POSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(POSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(POSSIBLE, stichCalculator.isRechterStich());
		assertEquals(POSSIBLE, stichCalculator.isGuaterStich());
		System.out.println();
		System.out.println("Stich Nr. 2");
		System.out.println();
		stiche.add(new Round(d_.e9, d_.sK, d_.lU, d_.e10, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		//System.out.println(result.toString());
		assertEquals(POSSIBLE, new FarbStichTest(result, stiche).runTest());
		// ab hier ist das Programm bereits besser als der Programmierer (benötigte Debugger)!
		// wenn e=Trumpf muss hA im ersten Stich linker sein => e7 im erstenStich Guater!
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(POSSIBLE, stichCalculator.isFarbStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(POSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isRechterStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isGuaterStich());
	}
	
	public void test_runTest_3() {
		System.out.println();
		System.out.println("Serien Test Nr. 3");
		System.out.println("##########################################################");
		System.out.println();
		Rounds stiche = new Rounds();
		GameInfo result =  new GameInfo(true);
		GameInfoCreator stichCalculator = new GameInfoCreator(stiche,result);
		// Idee Rechter: sU
		System.out.println();
		System.out.println("Stich Nr. 1");
		System.out.println();
		stiche.add(new Round(d_.e7, d_.sU, d_.sA, d_.sO, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(IMPOSSIBLE, stichCalculator.isFarbStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(POSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(POSSIBLE, stichCalculator.isRechterStich());
		assertEquals(POSSIBLE, stichCalculator.isGuaterStich());
		System.out.println();
		System.out.println("Stich Nr. 2");
		System.out.println();
		stiche.add(new Round(d_.e9, d_.sK, d_.lU, d_.e10, 2));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(IMPOSSIBLE, stichCalculator.isFarbStich());
		assertEquals(POSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(POSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isRechterStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isGuaterStich());
	}
	
	public void test_runTest_4() {
		System.out.println();
		System.out.println("Serien Test Nr. 4");
		System.out.println("##########################################################");
		System.out.println();
		Rounds stiche = new Rounds();
		GameInfo result =  new GameInfo(true);
		GameInfoCreator stichCalculator = new GameInfoCreator(stiche,result);
		// Idee Rechter: sU
	
		System.out.println();
		System.out.println("Stich Nr. 1");
		System.out.println();
		stiche.add(new Round(d_.e7, d_.sO, d_.lA, d_.sK, 1));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		//System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(IMPOSSIBLE, stichCalculator.isFarbStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(POSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isRechterStich());
		assertEquals(POSSIBLE, stichCalculator.isGuaterStich());
		System.out.println();
		System.out.println("Stich Nr. 2");
		System.out.println();
		stiche.add(new Round(d_.e9, d_.sA, d_.sU, d_.e10, 2));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(POSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		// ab hier Änderung weil Result ist nun fix!!
		//stichCalculator.calculateNewResult();
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, stichCalculator.isFarbStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(SURE, stichCalculator.isRechterStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isGuaterStich());
		System.out.println();
		System.out.println("Stich Nr. 3");
		System.out.println();
		stiche.add(new Round(d_.e8, d_.lK, d_.lU, d_.l8, 2));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(SURE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(IMPOSSIBLE, stichCalculator.isFarbStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isTrumpfStich());
		assertEquals(SURE, stichCalculator.isLinkerStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isRechterStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isGuaterStich());
		System.out.println();
		System.out.println("Stich Nr. 4");
		System.out.println();
		stiche.add(new Round(d_.s6, d_.hK, d_.l7, d_.s10, 3));
		sc = new GameInfoCreator(stiche, result);
		sc.setImpossibleNumbersToIMPOSSIBLE();
		System.out.println(result.toString());
		assertEquals(IMPOSSIBLE, new FarbStichTest(result, stiche).runTest());
		assertEquals(SURE, new TrumpfStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new LinkerStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new RechterStichTest(result, stiche).runTest());
		assertEquals(IMPOSSIBLE, new GuaterStichTest(result, stiche).runTest());
		System.out.println(stichCalculator.getMessage());
		//stichCalculator.calculateNewResult();
		assertEquals(IMPOSSIBLE, stichCalculator.isFarbStich());
		assertEquals(SURE, stichCalculator.isTrumpfStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isLinkerStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isRechterStich());
		assertEquals(IMPOSSIBLE, stichCalculator.isGuaterStich());

	}
	
	
}
