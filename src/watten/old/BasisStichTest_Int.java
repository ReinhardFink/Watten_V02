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
package watten.old;

import static watten.logic.Possibility.*;

import watten.CONSTANTS;
import watten.Card;
import watten.Round;
import watten.Rounds;
import watten.logic.GameInfo;
import watten.logic.Possibility;

public class BasisStichTest_Int {
	
	private final boolean VERBOSE = true;
	
	protected GameInfo gameInfo;
	protected Rounds rounds;
	protected Card lastWinnerCard;
	protected Possibility winnerRankPossibility; 
	protected Possibility winnerSuitPossibility;
	protected boolean winningVELIsetsTestToIMPOSSIBLE;
	
	/**
	 * 
	 * @param gameInfo
	 * @param rounds
	 * 
	 */
	public BasisStichTest_Int(GameInfo gameInfo, Rounds rounds) {
		this.rounds = rounds;
		this.winnerRankPossibility = null; 
		this.winnerSuitPossibility = null;
		this.lastWinnerCard = rounds.last().getWinnerCard();
		try {
			this.gameInfo = gameInfo.clone();
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("could not clone: " + gameInfo); 
		}	

	}
	
	/**
	 * 
	 * @return
	 * 
	 * Set special test properties.
	 * 
	 */
	protected Possibility setTestSpezials() { return POSSIBLE; }
	
	// new RUNTEST hoffentlich
	public Possibility runTest1() {
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
		//GameInfoMessage.appendVerboseMessage("TESTING for: Basisstich");
		if(winningVELIsetsTestToIMPOSSIBLE && rounds.last().getWinnerCard().equals(CONSTANTS.VELI)) return IMPOSSIBLE;
		//if(winningVELIsetsTestToIMPOSSIBLE && lastWinnerCard.equals(CONSTANTS.VELI)) return IMPOSSIBLE;

		if(setTestSpezials() != POSSIBLE) return setTestSpezials();
		
		if(gameInfo.isRechterFix()) return makeTestsWithRechterFix();
		
		if(setNewGameInfo() != POSSIBLE) return setNewGameInfo();
		
		if(testPreviousWinners() != POSSIBLE) return testPreviousWinners();
		
		return POSSIBLE;
	}
	
	// private Strategiemethoden
	/**
	 * 
	 * @return
	 * 
	 * all tests, when "Rechter" is already known.
	 */
	private Possibility makeTestsWithRechterFix() { 
		if(isGuaterInTrumpfStichTest()) return IMPOSSIBLE; // Spezialfall ausschliessen
		if(winnerCardFitsStichTest()) return SURE; // alle anderen Fälle
		else return IMPOSSIBLE;
	}
	
	private boolean isGuaterInTrumpfStichTest() {
		return winnerRankPossibility == IMPOSSIBLE && 
			   winnerSuitPossibility == SURE && 
			   gameInfo.isGuaterFix() &&
			   gameInfo.getGuater().equals(lastWinnerCard);
	}
	private boolean winnerCardFitsStichTest() { // Farbe, Trump
		return ((gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == winnerRankPossibility) && 
		        (gameInfo.getSuitPossibilityAt(lastWinnerCard.suit.ordinal()) == winnerSuitPossibility));
	}
	
	private Possibility setNewGameInfo() {
		if(hasNewGameInfoContradictionWithGameInfo()) return IMPOSSIBLE;
		else {
			gameInfo.setRankAt(lastWinnerCard.rank.ordinal(),winnerRankPossibility);
			gameInfo.setSuitAt(lastWinnerCard.suit.ordinal(),winnerSuitPossibility);
			return POSSIBLE;// behandelt alle Tests, wenn der Rechte fix ist.
		}
	}
	
	private boolean hasNewGameInfoContradictionWithGameInfo() {
		return (gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == winnerRankPossibility.opposite()) 
				|| 
			   ((winnerSuitPossibility != POSSIBLE) 
					   && (gameInfo.getSuitPossibilityAt(lastWinnerCard.suit.ordinal()) == winnerSuitPossibility.opposite()));
	}

	/**
	 * 
	 * @return
	 * 
	 * Method, where previous winners are tested for contradictions
	 */
	//TODO:
	// change iterator int stichNumber ton round : rounds
	private Possibility testPreviousWinnersOLD() {
		for(int stichNumber = 0; stichNumber < rounds.size(); stichNumber++) {
			if(!findWinnerPosInRound(stichNumber).equals(rounds.get(stichNumber).getWinnerCard()))
				if(!correctWinner(stichNumber)) 
					return IMPOSSIBLE;
		}
		return POSSIBLE;
	}
	
	private Possibility testPreviousWinners() {
		for(Round round : rounds) {
			if(!findWinnerPosInRound(round).equals(round.getWinnerCard())) 
				if(!correctWinner(round)) 
					return IMPOSSIBLE;
		}
		return POSSIBLE;
	}
	
	/**
	 * 
	 * @param roundNumber
	 * @return
	 */
	public Card findWinnerPosInRound(int roundNumber) {
		int currentWinnerPosition = 0;
		for(int cardNumber = 1; cardNumber < rounds.get(roundNumber).size(); cardNumber++) 
			if(!(rounds.get(roundNumber).get(currentWinnerPosition).beats(rounds.get(roundNumber).get(cardNumber),gameInfo)))
				currentWinnerPosition = cardNumber;
		return rounds.get(roundNumber).get(currentWinnerPosition);
	}
	
	public Card findWinnerPosInRound(Round round) {
		int currentWinnerPosition = 0;
		for(int cardNumber = 1; cardNumber < round.size(); cardNumber++) 
			if(!round.get(currentWinnerPosition).beats(round.get(cardNumber),gameInfo))
				currentWinnerPosition = cardNumber;
		return round.get(currentWinnerPosition);
	}
	
	private boolean correctWinner(int stichNumber) { 
		if((correctColor(stichNumber) == IMPOSSIBLE) &&
		   (correctNumber(stichNumber) == IMPOSSIBLE) &&
		   (correctToGuater(stichNumber) == IMPOSSIBLE))
		{
			if(VERBOSE) System.out.println("Stich Nr.: " + stichNumber + " weder Trumpf noch Schlag könnten erhöht werden!");
			return false;
		}
		return true;
	}
	
	private boolean correctWinner(Round round) { 
		if((correctColor(round) == IMPOSSIBLE) &&
		   (correctNumber(round) == IMPOSSIBLE) &&
		   (correctToGuater(round) == IMPOSSIBLE))
		{
			if(VERBOSE) System.out.println("Stich Nr.: " + round + " weder Trumpf noch Schlag könnten erhöht werden!");
			return false;
		}
		return true;
	}
	
	/**
	 * if suit of winnerCard is POSSIBLE set it to SURE
	 * and previous winner are tested again for contradictions
	 */
	private Possibility correctColor(int round) {
		if(VERBOSE) System.out.println("entering correctColor in Stichnumber : " + round);
		Possibility testValueColorUp = POSSIBLE;
		try {
			if(gameInfo.getSuitPossibilityAt(rounds.getWinnerCardInRound(round).suit.ordinal()) == POSSIBLE) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setSuitAt(rounds.getWinnerCardInRound(round).suit.ordinal(),SURE);
				testValueColorUp = testPreviousWinners();
				gameInfo = originalGameInfo;
				if(VERBOSE) 
					System.out.println("Stich Nr.: " + round + " corrected Color: " + rounds.get(round).getWinnerCard().suit.ordinal());
			} 
			else testValueColorUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			if(VERBOSE) System.out.println("Result konnte nicht geklont werden!");
		}
		return testValueColorUp;
	}
	
	private Possibility correctColor(Round round) {
		if(VERBOSE) System.out.println("entering correctColor in Stichnumber : " + round);
		Possibility testValueColorUp = POSSIBLE;
		try {
			if(gameInfo.getSuitPossibilityAt(round.getWinnerCard().suit.ordinal()) == POSSIBLE) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setSuitAt(round.getWinnerCard().suit.ordinal(),SURE);
				testValueColorUp = testPreviousWinners();
				gameInfo = originalGameInfo;
				if(VERBOSE) 
					System.out.println("Stich Nr.: " + round + " corrected Color: " + round.getWinnerCard().suit.ordinal());
			} 
			else testValueColorUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			if(VERBOSE) System.out.println("Result konnte nicht geklont werden!");
		}
		return testValueColorUp;
	}

	
	/**
	 * if rank of winnerCard is POSSIBLE set it to SURE
	 * and previous winner are tested again for contradictions
	 */
	private Possibility correctNumber(int round) {
		if(VERBOSE) System.out.println("entering correctNumber in Stichnumber : " + round);
		Possibility testValueNumberUp = POSSIBLE;
		try {
			if(gameInfo.getRankPossibilityAt(rounds.getWinnerCardInRound(round).rank.ordinal()) == POSSIBLE) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setRankAt(rounds.getWinnerCardInRound(round).rank.ordinal(),SURE);
				testValueNumberUp = testPreviousWinners();
				gameInfo = originalGameInfo;
				if(VERBOSE) 
					System.out.println("Stich Nr.: " + round + " corrected Number: " + rounds.get(round).getWinnerCard().rank.ordinal());
			}
			else testValueNumberUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("Result konnte nicht geklont werden!");
		}
		return testValueNumberUp;
	}
	
	private Possibility correctNumber(Round round) {
		if(VERBOSE) System.out.println("entering correctNumber in Stichnumber : " + round);
		Possibility testValueNumberUp = POSSIBLE;
		try {
			if(gameInfo.getRankPossibilityAt(round.getWinnerCard().rank.ordinal()) == POSSIBLE) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setRankAt(round.getWinnerCard().rank.ordinal(),SURE);
				testValueNumberUp = testPreviousWinners();
				gameInfo = originalGameInfo;
				if(VERBOSE) 
					System.out.println("Stich Nr.: " + round + " corrected Number: " + round.getWinnerCard().rank.ordinal());
			}
			else testValueNumberUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("Result konnte nicht geklont werden!");
		}
		return testValueNumberUp;
	}

	
	/**
	 * if rank of winnerCard is POSSIBLE set it to SURE
	 * and previous winner are tested again for contradictions
	 */
	private Possibility correctToGuater(int round) {
		if(VERBOSE) System.out.println("entering correctToGuater in Stichnumber : " + round);
		if(!gameInfo.isMitGuatem()) return IMPOSSIBLE;
		Possibility testGuaterUp = POSSIBLE;
		try {
			if(isGuaterUpgradePossible(round)) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setSuitAt(rounds.getWinnerCardInRound(round).suit.ordinal(),SURE);
				gameInfo.setRankAt(rounds.getWinnerCardInRound(round).calcRechterFromGuater().rank.ordinal(),SURE);
				testGuaterUp = testPreviousWinners();
				gameInfo = originalGameInfo;
				if(VERBOSE) {
					System.out.println("Stich Nr.: " + round); 
					System.out.println("corrected Color: " + rounds.getWinnerCardInRound(round).suit.ordinal());
					System.out.println("corrected Number: " + rounds.getWinnerCardInRound(round).calcRechterFromGuater().rank.ordinal());
				}
			}
			else testGuaterUp = IMPOSSIBLE;
		}
		catch(CloneNotSupportedException e) {
			System.out.println("Result konnte nicht geklont werden!");
		}
		return testGuaterUp;
	}
	
	private Possibility correctToGuater(Round round) {
		if(VERBOSE) System.out.println("entering correctToGuater in Stichnumber : " + round);
		if(!gameInfo.isMitGuatem()) return IMPOSSIBLE;
		Possibility testGuaterUp = POSSIBLE;
		try {
			if(isGuaterUpgradePossible(round)) {
				GameInfo originalGameInfo = gameInfo.clone();
				gameInfo.setSuitAt(round.getWinnerCard().suit.ordinal(),SURE);
				gameInfo.setRankAt(round.getWinnerCard().calcRechterFromGuater().rank.ordinal(),SURE);
				testGuaterUp = testPreviousWinners();
				gameInfo = originalGameInfo;
				if(VERBOSE) {
					System.out.println("Stich Nr.: " + round); 
					System.out.println("corrected Color: " + round.getWinnerCard().suit.ordinal());
					System.out.println("corrected Number: " + round.getWinnerCard().calcRechterFromGuater().rank.ordinal());
				}
			}
			else testGuaterUp = IMPOSSIBLE;
		}
		catch(CloneNotSupportedException e) {
			System.out.println("Result konnte nicht geklont werden!");
		}
		return testGuaterUp;
	}

	
	private boolean isGuaterUpgradePossible(int stichNumber) {
		// keyCard selbst darf nicht verändert werden, da sie ja den Testfall definiert!
		if(isKeyCardWinnerCardForUpgrade(stichNumber)) return false;
		if(isNotColorAndNumberBlocked(stichNumber) && isColorOrNumberPossible(stichNumber))
			return true;
		else return false;
	}
	
	private boolean isGuaterUpgradePossible(Round round) {
		// keyCard selbst darf nicht verändert werden, da sie ja den Testfall definiert!
		if(isKeyCardWinnerCardForUpgrade(round)) return false;
		if(isNotColorAndNumberBlocked(round) && isColorOrNumberPossible(round))
			return true;
		else return false;
	}

	
	private boolean isKeyCardWinnerCardForUpgrade(int stichNumber) {
		return ((lastWinnerCard.suit.ordinal() == rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) && 
				   (lastWinnerCard.rank.ordinal() == rounds.getWinnerCardInRound(stichNumber).rank.ordinal()));
	}
	
	private boolean isKeyCardWinnerCardForUpgrade(Round round) {
		return ((lastWinnerCard.suit.ordinal() == round.getWinnerCard().suit.ordinal()) && 
				   (lastWinnerCard.rank.ordinal() == round.getWinnerCard().rank.ordinal()));
	}

	
	private boolean isNotColorAndNumberBlocked(int stichNumber) {
		// in Result: (¬-1 && ¬-1) &&  (0 || 0)
		return ((gameInfo.getSuitPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) != IMPOSSIBLE) &&
				(gameInfo.getRankPossibilityAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal()) != IMPOSSIBLE));
	}
	
	private boolean isNotColorAndNumberBlocked(Round round) {
		// in Result: (¬-1 && ¬-1) &&  (0 || 0)
		return ((gameInfo.getSuitPossibilityAt(round.getWinnerCard().suit.ordinal()) != IMPOSSIBLE) &&
				(gameInfo.getRankPossibilityAt(round.getWinnerCard().calcRechterFromGuater().rank.ordinal()) != IMPOSSIBLE));
	}

	
	private boolean isColorOrNumberPossible(int stichNumber) {
		return ((gameInfo.getSuitPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) == POSSIBLE) ||
				(gameInfo.getRankPossibilityAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal()) == POSSIBLE));
	}
	
	private boolean isColorOrNumberPossible(Round round) {
		return ((gameInfo.getSuitPossibilityAt(round.getWinnerCard().suit.ordinal()) == POSSIBLE) ||
				(gameInfo.getRankPossibilityAt(round.getWinnerCard().calcRechterFromGuater().rank.ordinal()) == POSSIBLE));
	}

	
}
