package watten.logic;

import static watten.CONSTANTS.VELI;
import static watten.logic.Possibility.*;

import watten.Rounds;

public class GuaterStichTest extends BasisStichTest {
 
	public GuaterStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo, rounds);
		GameInfoMessage.verbose("Entering Test: Guaterstichtest");
		winnerRankPossibility = SURE; 
		winnerSuitPossibility = SURE;
		lastWinnerCard = rounds.last().getWinnerCard().calcRechterFromGuater();
	}
	
	protected Possibility handleTestSpezials() { 
		if (rounds.last().getWinnerCard().equals(VELI)) return IMPOSSIBLE;
		else if(gameInfo.isMitGuatem()) return POSSIBLE;
		else return IMPOSSIBLE;
	}
}
