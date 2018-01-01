package watten.logic;

import static watten.logic.Possibility.*;

import watten.Rounds;


public class RechterStichTest extends BasisStichTest {
 
	public RechterStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo,rounds);
		GameInfoMessage.verbose("Entering Test: Rechterstichtest");
		winnerRankPossibility = SURE; 
		winnerSuitPossibility = SURE;
	}
}
