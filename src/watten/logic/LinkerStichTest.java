package watten.logic;

import static watten.logic.Possibility.*;

import watten.Rounds;


public class LinkerStichTest extends BasisStichTest {
 
	public LinkerStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo,rounds);
		winnerRankPossibility = SURE; 
		winnerSuitPossibility = IMPOSSIBLE;
		winningVELIsetsTestToIMPOSSIBLE = true;
	}
}
