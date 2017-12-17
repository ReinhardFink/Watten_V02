package watten.logic;

import static watten.logic.Possibility.*;

import watten.Rounds;


public class FarbStichTest extends BasisStichTest {
	
	public FarbStichTest(GameInfo gameInfo, Rounds rounds) {
		super(gameInfo,rounds);
		winnerRankPossibility = IMPOSSIBLE; 
		winnerSuitPossibility = IMPOSSIBLE;
		winningVELIsetsTestToIMPOSSIBLE = true;
	}
	
	public Possibility setTestSpezials() { return setAllColorsInLastStichToImpossible(); }
	
	// wird benötigt um den Fall 4 Farben im Stich z.B. e7, h7, l7, s8, 0 abzudecken
	// eine Farbe muss dann Trumpf sein!!
	private Possibility setAllColorsInLastStichToImpossible() {
		for(int position = 0; position < rounds.last().size(); position++) {
			if(gameInfo.getSuitPossibilityAt(rounds.last().get(position).suit.ordinal()) == SURE) 
				return IMPOSSIBLE; 
			else 
				gameInfo.setSuitAt(rounds.last().get(position).suit.ordinal(),IMPOSSIBLE);
		}
		return POSSIBLE;
	}
}
