/**
 * BasisStichTest is the abstract basis class on which all concrete test-classes build on.
 * 
 * In a concrete test class, for example FarbStichTest all special settings defining a "Stich" are made.
 * For a "Farbstich" it is necessary  that rank & suit of the winner card are IMPOSSIBLE for "Schlag" & "Trumpf".
 * 
 * After defining the concrete "Stich" in the derived classes, BasisStichTest checks, 
 * if any contradiction in any played round can be found.
 * This is done, with a cloned gameInfo, where the Possibility-Arrays for rank and suit are set as described bevor.
 * 
 * With this approach it is easier to check earlier played rounds for contradictions.
 * An other approach you can see in FarbStichTest-simple, where all the logic is done 
 * in the derived class. But here you have to deal with the problem, how to check for 
 * contradictions in earlier rounds. 
 * For Example: is the round before a "Farbstich" or a "Trumpfstich".
 * 
 */
package watten.logic;

import static watten.logic.Possibility.*;

import watten.Card;
import watten.Round;
import watten.Rounds;

public class BasisStichTest {
	
	// all the collected information for former rounds
	protected GameInfo gameInfo;
	// the played rounds
	protected Rounds rounds;
	// winner card of the last round
	protected Card lastWinnerCard;
	// possibilities of rank/suit of TEST:
	// e.g. testing for "Rechter" means: rank & suit have to be SURE!
	protected Possibility winnerRankPossibility; 
	protected Possibility winnerSuitPossibility;
	// 
	
	/**
	 * 
	 * @param gameInfo
	 * @param rounds
	 * 
	 */
	public BasisStichTest(GameInfo gameInfo, Rounds rounds) {
		this.rounds = rounds;
		this.winnerRankPossibility = null; 
		this.winnerSuitPossibility = null;
		this.lastWinnerCard = rounds.last().getWinnerCard();
		// ever test works with his own cloned gameInfo
		try {
			this.gameInfo = gameInfo.clone();
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("could not clone: " + gameInfo); 
		}	
	}
	
	protected Possibility handleTestSpezials() { return POSSIBLE; }
	
	// new RUNTEST hoffentlich
	public Possibility runTest_NEW() {
		// Test is IMPOSSIBLE, if rank already SURE or IMPOSSIBLE and we want to set it to opposite,
		// winnerRankPossibility always SURE or IMPOSSIBLE!
		//
		//if (winnerRankPossibility == IMPOSSIBLE && gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == SURE) return IMPOSSIBLE;
		//if (winnerRankPossibility == SURE && gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == IMPOSSIBLE) return IMPOSSIBLE;
		if (gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == winnerRankPossibility.opposite()) 
			return IMPOSSIBLE;
		// Test is IMPOSSIBLE, if suit already SURE or IMPOSSIBLE and we want to set it to opposite,
		if ((winnerSuitPossibility != POSSIBLE) 
			   && (winnerSuitPossibility == gameInfo.getSuitPossibilityAt(lastWinnerCard.suit.ordinal()).opposite()))
			return IMPOSSIBLE;
		
	
		
		// test rounds for contradiction
		for (Round round : rounds) {
			// first card starts as winner
			int currentWinner = 0;
			for (int i = 1; i < round.size(); i++) {
				if (!round.get(currentWinner).beats(round.get(i), gameInfo)) {
					currentWinner = i;
					System.out.println(currentWinner);
				}
			}
			if (!round.get(currentWinner).equals(round.getWinnerCard())) return IMPOSSIBLE;
		}
		return POSSIBLE;
	}

	/**
	 * 
	 * @return
	 * 
	 * Method, where the testing for contradictions is done.
	 * 
	 */
	public Possibility runTest() {
		// test specials like 4 different colors can not be a "Farbstich"
		if(handleTestSpezials() != POSSIBLE) return handleTestSpezials();
		// if "Trumpf" & "Schlag" is known, easy testing if winner.rank & winner.suit fit in test definition
		if(gameInfo.isRechterFix()) return makeTestsWithRechterFix();
		// test if new winnerRankPossibility or winnerSuitPossibility of concrete test conflicts with current possibilities
		if(setNewGameInfo() != POSSIBLE) return setNewGameInfo();
		// test previous winners for contradiction
		if(testWinnerCards() != POSSIBLE) return testWinnerCards();
		
		return POSSIBLE;
	}
	
	// private strategy methods
	/**
	 * 
	 * @return Possibility, if winner card fits definition of test. In this case only SURE or IMPOSSIBLE!
	 * 
	 * if "Rechter" is fix, "Schlag" and "Trumpf" are known.
	 * So we just have to check if the winner of the last round is compatible
	 * with the current test. E.g. for "LinkerStichTest" the 
	 * winnerRankPossibility has to be SURE and
	 * winnerSuitPossibility has to be IMPOSSIBLE.
	 * And the same way for all other tests.
	 * 
	 */
	private Possibility makeTestsWithRechterFix() { 
		if(gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == winnerRankPossibility && 
		   gameInfo.getSuitPossibilityAt(lastWinnerCard.suit.ordinal()) == winnerSuitPossibility) 
				return SURE; 
		else return IMPOSSIBLE;
	}
	
	private Possibility setNewGameInfo() {
		if(hasNewGameInfoContradictionWithGameInfo()) return IMPOSSIBLE;
		else {
			gameInfo.setRankAt(lastWinnerCard.rank.ordinal(),winnerRankPossibility);
			gameInfo.setSuitAt(lastWinnerCard.suit.ordinal(),winnerSuitPossibility);
			return POSSIBLE;
		}
	}
	
	private boolean hasNewGameInfoContradictionWithGameInfo() {
		return (gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == winnerRankPossibility.opposite()) 
				|| 
			   ((winnerSuitPossibility != POSSIBLE) 
					   && (gameInfo.getSuitPossibilityAt(lastWinnerCard.suit.ordinal()) == winnerSuitPossibility.opposite()));
	}
	
	private Possibility testWinnerCards() {
		GameInfoMessage.verbose("Entering Test: Test previous winners");
		for(Round round : rounds) {
			if(findWinnerInRound(round).isNOT(round.getWinnerCard()))
				return isWinnerUpgradable(round);
		}
		return POSSIBLE;
	}
	
	public Card findWinnerInRound(Round round) {
		int currentWinnerPosition = 0;
		for(int cardNumber = 1; cardNumber < round.size(); cardNumber++) 
			if(!round.get(currentWinnerPosition).beats(round.get(cardNumber),gameInfo))
				currentWinnerPosition = cardNumber;
		return round.get(currentWinnerPosition);
	}
	/**
	 * 
	 * @param round as round to test
	 * @return Possibility, of upgrade (suit or rank) of a previous winner
	 * 
	 * a lot of logic happens here!
	 * if a round is tested winnerRankPossibility & winnerSuitPossibility are set to the test specific values.
	 * if(findWinnerInRound(round).isNOT(round.getWinnerCard())) THEN
	 * it has to be found out, if there is an other winner possibility for round.getWinnerCard().
	 * e.g.: 
	 * 1: (e7, hA, l7, e8, 3)
	 * 2: (e9, sK, lU, e10, 3)
	 * if 2: is "Farbstiches" e,s,l can not be "Trümpfe"
	 * => h = Trumpf and contradiction in 1: hA does NOT beat e8
	 * therefore e8 has to be corrected to "Linker" <=> setRankAt(round.getWinnerCard().rank.ordinal(),SURE);
	 * 
	 * 1. check if suit upgrade possible
	 * 2. check if rank upgrade possible
	 * 
	 * 1 & 2:
	 * because testPreviousWinners() is recursive called
	 * in suit & rank upgrade each other is called and therefore
	 * "Rechter" is checked twice 
	 *    
	 * 3. check if upgrade to "Guatem" possible
	 * 
	 */
	private Possibility isWinnerUpgradable(Round round) { 
		GameInfoMessage.verbose("Entering Test: correct winner in round " + round);
		if (isSuitUpgradeable(round) == POSSIBLE) return POSSIBLE;
		if (isRankUpgradeable(round) == POSSIBLE) return POSSIBLE;
		if (gameInfo.isMitGuatem() && isToGuaterUpgradeable(round) == POSSIBLE) return POSSIBLE;
		return IMPOSSIBLE;
	}
	
	/**
	 * 
	 * @ return Possibility, if color upgrade is POSSIBLE
	 * always POSSIBLE or IMPOSSIBLE
	 * Default is POSSIBLE
	 * if suit of winnerCard is POSSIBLE set it to SURE
	 * and previous winner are tested again for contradictions
	 */
	private Possibility isSuitUpgradeable(Round round) {
		GameInfoMessage.verbose("Entering Test: correct winner suit in round " + round);
		Possibility testValueColorUp = POSSIBLE;
		try {
			if(gameInfo.getSuitPossibilityAt(round.getWinnerCard().suit.ordinal()) == POSSIBLE) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setSuitAt(round.getWinnerCard().suit.ordinal(),SURE);
				testValueColorUp = testWinnerCards();
				gameInfo = originalGameInfo;
				GameInfoMessage.verbose("Round: " + round + " corrected Suit: " + round.getWinnerCard().suit);
			} 
			else testValueColorUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			GameInfoMessage.verbose("GameInfo could not be cloned!");
		}
		return testValueColorUp;
	}

	
	/**
	 * 
	 * @ return Possibility, if suit upgrade is POSSIBLE
	 * always POSSIBLE or IMPOSSIBLE
	 * Default is POSSIBLE
	 * if rank of winnerCard is POSSIBLE set it to SURE
	 * and previous winner are tested again for contradictions
	 */
	private Possibility isRankUpgradeable(Round round) {
		GameInfoMessage.verbose("Entering Test: correct winner rank in round " + round);
		Possibility testValueNumberUp = POSSIBLE;
		try {
			if(gameInfo.getRankPossibilityAt(round.getWinnerCard().rank.ordinal()) == POSSIBLE) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setRankAt(round.getWinnerCard().rank.ordinal(),SURE);
				testValueNumberUp = testWinnerCards();
				gameInfo = originalGameInfo;
				GameInfoMessage.verbose("Round: " + round + " corrected Rank: " + round.getWinnerCard().rank);
			}
			else testValueNumberUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("GameInfo could not be cloned!");
		}
		return testValueNumberUp;
	}

	
	/**
	 * 
	 * @ return Possibility, if suit & rank upgrade is POSSIBLE
	 * always POSSIBLE or IMPOSSIBLE
	 * Default is POSSIBLE
	 * if rank of winnerCard is POSSIBLE set it to SURE
	 * and previous winner are tested again for contradictions
	 */
	
	//TODO:
	// Warum if(lastWinnerCard.equals(round.getWinnerCard())) return IMPOSSIBLE;
	// NUR HIER und nicht auch in is..Upgradeable() ???????????
	
	
	private Possibility isToGuaterUpgradeable(Round round) {
		GameInfoMessage.verbose("Entering Test: correct winner to Guater in round " + round);
		// not upgradable, because ????
		if(lastWinnerCard.equals(round.getWinnerCard())) return IMPOSSIBLE;
		Possibility testGuaterUp = POSSIBLE;
		try {
			if(gameInfo.getRankPossibilityAt(round.getWinnerCard().calcRechterFromGuater().rank.ordinal()) != IMPOSSIBLE 
					&&
			   gameInfo.getSuitPossibilityAt(round.getWinnerCard().suit.ordinal()) != IMPOSSIBLE) 
			{
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setSuitAt(round.getWinnerCard().suit.ordinal(),SURE);
				gameInfo.setRankAt(round.getWinnerCard().calcRechterFromGuater().rank.ordinal(),SURE);
				testGuaterUp = testWinnerCards();
				gameInfo = originalGameInfo;
				GameInfoMessage.verbose("Round: " + round + " corrected Suit: " + 
				round.getWinnerCard().suit + " corrected Rank: " + round.getWinnerCard().calcRechterFromGuater().rank);
	
			}
			else testGuaterUp = IMPOSSIBLE;
		}
		catch(CloneNotSupportedException e) {
			System.out.println("GameInfo could not be cloned!");
		}
		return testGuaterUp;
	}
}
