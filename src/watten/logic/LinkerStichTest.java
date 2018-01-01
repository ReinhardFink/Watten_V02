package watten.logic;

import static watten.CONSTANTS.VELI;
import static watten.logic.Possibility.*;

import watten.Rounds;


public class LinkerStichTest extends BasisStichTest {
 
	public LinkerStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo,rounds);
		GameInfoMessage.verbose("Entering Test: Linkerstichtest");
		winnerRankPossibility = SURE; 
		winnerSuitPossibility = IMPOSSIBLE;
	}
	
	protected Possibility handleTestSpezials() { 
		if (rounds.last().getWinnerCard().equals(VELI)) return IMPOSSIBLE;
		else return POSSIBLE;
	}
}
