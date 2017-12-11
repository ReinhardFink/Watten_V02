package watten.logic;

import watten.CONSTANTS;
import watten.Rounds;
import watten.Round;

public class FarbStichTest_simple extends BasisStichTest_simple {
	
	public FarbStichTest_simple(GameInfo result, Rounds playedRounds) {
		super(result,playedRounds);
	}

	/**
	 * Definition: Farbstich:
	 * win round, in witch no TRUMPF, SCHLAG or GUATER is played.
	 * So, normally round is won by first played card, IF no other card 
	 * with same suit as first card and higher number in round.
	 * 
	 * #01:Define IMPOSSIBLE cases first:
	 * 
	 * #01-01: winner card has different suit than first card!
	 * 
	 * #01-02: winner.suit == firstCardsuit, but winner DOES NOT wins all in own suit!
	 * 
	 * #01-03: TRUMPF is found in round!
	 * 
	 * #01-04: SCHLAG is found in round!
	 * 
	 * #01-045 VELI is found in round!
	 * 
	 * #01-06: 4 suits in round => TRUMPF has to be in round!
	 * 
	 * 
	 * #02:Define POSSIBLE cases second: 
	 * 
	 * #02-01: TRUMPF not know && winner.suit == fistCard.suit && winner wins all in own suit!
	 * 
	 * 
	 * #03:Define SURE cases third:
	 * 
	 * #03-01: TRUMPF know && not TRUMPF in round && winner.suit == fistCard.suit && winner wins all in own suit!
	 */
	@Override
	public Possibility runTest() {
		GameInfoMessage.appendVerboseMessage("TESTING for: Farbstich");
		Round lastRound = playedRounds.last();
		// IMPOSSIBLE cases:
		// Color Hit IMPOSSIBLE: Winner.suit != First_Card.suit!
		if (lastRound.getWinnerCard().suit != lastRound.cards[0].suit) {
			GameInfoMessage.appendVerboseMessage("Color Hit IMPOSSIBLE: Winner.suit != First_Card.suit!");
			return Possibility.IMPOSSIBLE;
		}
		
		// Color Hit IMPOSSIBLE: winner.suit == firstCardsuit, but winner DOES NOT wins all in own suit!  
		if (lastRound.getWinnerCard().suit == lastRound.cards[0].suit && !lastRound.isWinnerBiggestInSuit()) {
			GameInfoMessage.appendVerboseMessage("Color Hit IMPOSSIBLE: winner.suit == firstCardsuit, but winner DOES NOT wins all in own suit!");
			return Possibility.IMPOSSIBLE;
		}
				
		// TRUMPF is found in round!
		if (gameInfo.isTrumpfFix() && lastRound.hasSuit(gameInfo.getTrumpf())) {
			GameInfoMessage.appendVerboseMessage("Color Hit IMPOSSIBLE: TRUMPF is found in round!");
			return Possibility.IMPOSSIBLE;
		}
		
		// SCHLAG is found in round!
		if (gameInfo.isSchlagFix() && lastRound.hasRank(gameInfo.getSchlag())) {
			GameInfoMessage.appendVerboseMessage("Color Hit IMPOSSIBLE: SCHLAG is found in round!");
			return Possibility.IMPOSSIBLE;
		}
		
		// VELI is found in round!
		if (lastRound.hasCard(CONSTANTS.VELI)) {
			GameInfoMessage.appendVerboseMessage("Color Hit IMPOSSIBLE: VELI is found in round!");
			return Possibility.IMPOSSIBLE;
		}
		
		// 4 suits in round => TRUPF has to be in round!
		if (lastRound.has4Suits()) {
			GameInfoMessage.appendVerboseMessage("Color Hit IMPOSSIBLE: 4 suits in round => TRUPF has to be in round!");
			return Possibility.IMPOSSIBLE;
		}
				
		// POSSIBLE cases:
		// TRUMPF not know && winner.suit == fistCard.suit && winner wins all in own suit!
		if (!gameInfo.isTrumpfFix() && 
				lastRound.cards[lastRound.winner].suit == lastRound.cards[0].suit && 
				lastRound.isWinnerBiggestInSuit()) {
			GameInfoMessage.appendVerboseMessage("Color Hit POSSIBLE: TRUMPF not know && winner.suit == fistCard.suit && winner wins all in own suit!");
			return Possibility.POSSIBLE;
		}
		
		// SURE cases:
		// TRUMPF know && not TRUMPF in round && winner.suit == fistCard.suit && winner.number > fistCard.number!
		if (gameInfo.isTrumpfFix() && !lastRound.hasSuit(gameInfo.getTrumpf()) && 
				lastRound.getWinnerCard().rank.ordinal() > lastRound.cards[0].rank.ordinal() &&
				lastRound.isWinnerBiggestInSuit()) {
			GameInfoMessage.appendVerboseMessage("Color Hit SURE: TRUMPF know && not TRUMPF in round && winner.suit == fistCard.suit && winner.number > fistCard.number!");
			return Possibility.SURE;
		}
		else {
			throw new RuntimeException("reached end of test, without finding any case!");
		}
	}
}
