package watten.simple;

import static watten.logic.Possibility.*;

import watten.logic.GameInfo;
import watten.logic.GameInfoMessage;
import watten.logic.Possibility;

public class TrumpfStichTest_simple extends BasisStichTest_simple {

	public TrumpfStichTest_simple(GameInfo result, Rounds_simple stiche) {
		super(result, stiche);
	}

	/**
	 * Definition: Trumpfstich:
	 * win round, in witch no SCHLAG or GUATER is played.
	 * 
	 * #01:Define IMPOSSIBLE cases first:
	 * 
	 * #01-01: SCHLAG is winner in round!
	 * 
	 * #01-02: GUATER is winner in round!
	 * 
	 * #01-03: Winner is not biggest number in suit!
	 * 
	 * #01-04: TRUMPF is found in round, but is not winner!
	 * 
	 * 
	 * #02: Define POSSIBLE cases second: 
	 * 
	 * #02-01: TRUMPF not know && winner wins all in own suit!
	 * 
	 * 
	 * #03: Define SURE cases third:
	 * 
	 * #03-0TRUMPF know &&  winner.suit == TRUMPF && winner wins all in own suit!
	 */

	public Possibility runTest() {
		GameInfoMessage.appendVerboseMessage("TESTING for: Trumpfstich");
		Round_simple lastRound = rounds.last();
		// IMPOSSIBLE cases:
		// SCHLAG is winner in round!
		if (gameInfo.isSchlagFix() && gameInfo.getSchlag() == lastRound.getWinnerCard().rank) {
			GameInfoMessage.appendVerboseMessage("Trumpf Hit IMPOSSIBLE: SCHLAG is winner in round!");
			return IMPOSSIBLE;
		}
		
		// GUATER is winner in round!
		if (gameInfo.isMitGuatem() && gameInfo.isGuaterFix() && gameInfo.getGuater().equals(lastRound.getWinnerCard())) {
			GameInfoMessage.appendVerboseMessage("Trumpf Hit IMPOSSIBLE: GUATER is winner in round!");
			return IMPOSSIBLE;
		}
		
		// Winner is not biggest number in suit!
		if (!lastRound.isWinnerBiggestInSuit()) {
			GameInfoMessage.appendVerboseMessage("Trumpf Hit IMPOSSIBLE: Winner is not biggest number in suit!");
			return IMPOSSIBLE;
		}
		
		// TRUMPF is found in round, but is not winner!
		if (gameInfo.isTrumpfFix() && lastRound.getWinnerCard().suit != gameInfo.getTrumpf()) {
			GameInfoMessage.appendVerboseMessage("Trumpf Hit IMPOSSIBLE: TRUMPF is found in round, but is not winner!");
			return IMPOSSIBLE;
		}
		
		// POSSIBLE cases second: 
		// TRUMPF not know && winner wins all in own suit!
		if (!gameInfo.isTrumpfFix() && lastRound.isWinnerBiggestInSuit()) {
			GameInfoMessage.appendVerboseMessage("Trumpf Hit POSSIBLE: TRUMPF not know && winner wins all in own suit!");
			return POSSIBLE;
		}
		
		// SURE cases third:
		// TRUMPF know &&  winner.suit == TRUMPF && winner wins all in own suit!
		if (gameInfo.isTrumpfFix() && lastRound.getWinnerCard().suit == gameInfo.getTrumpf() && 
				lastRound.isWinnerBiggestInSuit()) {
			GameInfoMessage.appendVerboseMessage("Trumpf Hit SURE: TRUMPF know &&  winner.suit == TRUMPF && winner wins all in own suit!");
			return SURE;
		}
		else {
			throw new RuntimeException("reached end of test, without finding any case!");
		}
	}
}
