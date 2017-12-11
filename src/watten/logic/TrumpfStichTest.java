package watten.logic;

import static watten.logic.Possibility.*;
import static watten.CONSTANTS.*;

import watten.Rounds;


public class TrumpfStichTest extends BasisStichTest {
 
	public TrumpfStichTest(GameInfo result, Rounds playedRounds) {
		super(result,playedRounds);
	}
	
	protected boolean blockVeli() { return false; }
	
	public void defineTest() {
		setKeyCard(getRounds().last().getWinnerCard());
		setWinnerNewNumberResult(IMPOSSIBLE);
		setWinnerNewColorResult(POSSIBLE);
		// s6, e7, h7, s7, 0 wird wegen s7 sclägt s6 nicht als Trumpfstich anerkannt! 
		// Für den Veli wird kein Trumpf gesetzt!
		if(!getRounds().last().getWinnerCard().equals(VELI))
			setWinnerNewColorResult(SURE);
	}
}
