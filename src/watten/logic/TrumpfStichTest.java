package watten.logic;

import static watten.logic.Possibility.*;
import static watten.CONSTANTS.*;

import watten.Rounds;


public class TrumpfStichTest extends BasisStichTest {
 
	public TrumpfStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo,rounds);
		winnerRankPossibility = IMPOSSIBLE; 
		winnerSuitPossibility = POSSIBLE;
		winningVELIsetsTestToIMPOSSIBLE = false;
	}
	
	public Possibility setTestSpezials() {
		// s6, e7, h7, s7, 0 wird wegen s7 sclägt s6 nicht als Trumpfstich anerkannt! 
		// Für den Veli wird kein Trumpf gesetzt!
		if(!rounds.last().getWinnerCard().equals(VELI))
			winnerSuitPossibility = SURE;
		return POSSIBLE;
	}
}
