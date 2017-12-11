package watten.logic;

import watten.Rounds;

public abstract class BasisStichTest_simple {
		
	protected GameInfo gameInfo;
	protected Rounds playedRounds;
	protected Possibility winnerNewNumberResult; 
	protected Possibility winnerNewColorResult;
	
	// Constructor
	public BasisStichTest_simple(GameInfo gameInfo, Rounds playedRounds) {
		try {
			this.gameInfo = gameInfo.clone();
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("could not clone: " + gameInfo); 
		}	
		this.playedRounds = playedRounds;
	}
		
	public abstract Possibility runTest();
	
	/*
	public boolean hasConflictInPreviousRounds(GameInfo gameInfo) {
		for(Round round : playedRounds) {
			for (Card card : round.cards) {
				// FALSCH !!!!!!!!!!!!!!!!!!!!!!!
				if (!round.getWinnerCard().equals(card) && !round.getWinnerCard().beats(card, gameInfo)) {
					GameInfoMessage.appendVerboseMessage("INCONSISTENCY: round.getWinnerCard() " + round.getWinnerCard() + " does NOT beat " + card);
					return true;
				}
			}
		}
		return false;
	}
	*/
}
