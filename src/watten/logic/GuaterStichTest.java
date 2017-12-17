package watten.logic;

import static watten.logic.Possibility.*;

import watten.Rounds;

public class GuaterStichTest extends BasisStichTest {
 
	public GuaterStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo, rounds);
		winnerRankPossibility = SURE; 
		winnerSuitPossibility = SURE;
		winningVELIsetsTestToIMPOSSIBLE = true;
		lastWinnerCard = rounds.last().getWinnerCard().calcRechterFromGuater();
	}
	
	protected Possibility setTestSpezials() { 
		if(gameInfo.isMitGuatem()) return Possibility.POSSIBLE;
		else return Possibility.IMPOSSIBLE;
	}
}
