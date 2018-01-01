package watten.logic;

import static watten.logic.Possibility.*;
import static watten.CONSTANTS.*;

import watten.Rounds;


public class TrumpfStichTest extends BasisStichTest {
 
	public TrumpfStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo,rounds);
		GameInfoMessage.verbose("Entering Test: Trumpfstichtest");
		winnerRankPossibility = IMPOSSIBLE; 
		// s6, e7, h7, s7, 0 wird wegen s7 schlägt s6 nicht als Trumpfstich anerkannt! 
		// Für den Veli wird kein Trumpf gesetzt!
		if(!rounds.last().getWinnerCard().equals(VELI))
			winnerSuitPossibility = SURE;
		else 
			winnerSuitPossibility = POSSIBLE;
	}
	
	public Possibility handleTestSpezials() { 
		if (gameInfo.isGuaterFix() && gameInfo.getGuater().equals(lastWinnerCard))
				return IMPOSSIBLE;
		return POSSIBLE;
	}
}
