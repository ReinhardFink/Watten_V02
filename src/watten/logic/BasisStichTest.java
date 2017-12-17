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

import watten.CONSTANTS;
import watten.Card;
import watten.Round;
import watten.Rounds;

public class BasisStichTest {
	
	private final boolean VERBOSE = true;
	
	protected GameInfo gameInfo;
	protected Rounds rounds;
	protected Card lastWinnerCard;
	protected Possibility winnerRankPossibility; 
	protected Possibility winnerSuitPossibility;
	protected boolean winningVELIsetsTestToIMPOSSIBLE;
	
	// Constructor
	public BasisStichTest(GameInfo gameInfo, Rounds rounds) {
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
			if (currentWinner != round.winner) return IMPOSSIBLE;
		}
		return POSSIBLE;
	}

	// runTest the strategy-method
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
	// behandelt alle Tests, wenn der Rechte fix ist.
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
			return POSSIBLE;
		}
	}
	
	private boolean hasNewGameInfoContradictionWithGameInfo() {
		return (gameInfo.getRankPossibilityAt(lastWinnerCard.rank.ordinal()) == winnerRankPossibility.opposite()) 
				|| 
			   ((winnerSuitPossibility != POSSIBLE) 
					   && (gameInfo.getSuitPossibilityAt(lastWinnerCard.suit.ordinal()) == winnerSuitPossibility.opposite()));
	}

	private Possibility testPreviousWinners() {
		for(int stichNumber = 0; stichNumber < rounds.size(); stichNumber++) {
			if(findWinnerPosInRound(stichNumber) != rounds.get(stichNumber).winner) 
				if(!correctWinner(stichNumber)) 
					return IMPOSSIBLE;
		}
		return POSSIBLE;
	}
	
	// private Testmethoden
	// public fürs Testen
	public int findWinnerPosInRound(int stichNumber) {
		int currentWinnerPosition = 0;
		for(int cardNumber = 1; cardNumber < rounds.get(stichNumber).size(); cardNumber++) 
			if(!(rounds.get(stichNumber).get(currentWinnerPosition).beats(rounds.get(stichNumber).get(cardNumber),gameInfo)))
				currentWinnerPosition = cardNumber;
		return currentWinnerPosition;
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
	
	private Possibility correctColor(int stichNumber) {
		if(VERBOSE) System.out.println("entering correctColor in Stichnumber : " + stichNumber);
		Possibility testValueColorUp = POSSIBLE;
		try {
			if(gameInfo.getSuitPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) == POSSIBLE) {
				GameInfo saveResult = gameInfo.clone();
				gameInfo.setSuitAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal(),SURE);
				testValueColorUp = testPreviousWinners();
				gameInfo = saveResult;
				if(VERBOSE) System.out.println("Stich Nr.: " + stichNumber + " corrected Color: " + rounds.get(stichNumber).getWinnerCard().suit.ordinal());
			} 
			else testValueColorUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			if(VERBOSE) System.out.println("Result konnte nicht geklont werden!");
		}
		return testValueColorUp;
	}
	
	private Possibility correctNumber(int stichNumber) {
		if(VERBOSE) System.out.println("entering correctNumber in Stichnumber : " + stichNumber);
		Possibility testValueNumberUp = POSSIBLE;
		try {
			if(gameInfo.getRankPossibilityAt(rounds.getWinnerCardInRound(stichNumber).rank.ordinal()) == POSSIBLE) {
				GameInfo saveResult = gameInfo.clone();
				gameInfo.setRankAt(rounds.getWinnerCardInRound(stichNumber).rank.ordinal(),SURE);
				testValueNumberUp = testPreviousWinners();
				gameInfo = saveResult;
				if(VERBOSE) System.out.println("Stich Nr.: " + stichNumber + " corrected Number: " + rounds.get(stichNumber).getWinnerCard().rank.ordinal());
			}
			else testValueNumberUp = IMPOSSIBLE;
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("Result konnte nicht geklont werden!");
		}
		return testValueNumberUp;
	}
	
	private Possibility correctToGuater(int stichNumber) {
		if(VERBOSE) System.out.println("entering correctToGuater in Stichnumber : " + stichNumber);
		if(!gameInfo.isMitGuatem()) return IMPOSSIBLE;
		Possibility testGuaterUp = POSSIBLE;
		try {
			if(isGuaterUpgradePossible(stichNumber)) {
				GameInfo saveResult = gameInfo.clone();
				gameInfo.setSuitAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal(),SURE);
				gameInfo.setRankAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal(),SURE);
				testGuaterUp = testPreviousWinners();
				gameInfo = saveResult;
				if(VERBOSE) {
					System.out.println("Stich Nr.: " + stichNumber); 
					System.out.println("corrected Color: " + rounds.getWinnerCardInRound(stichNumber).suit.ordinal());
					System.out.println("corrected Number: " + rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal());
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
	
	private boolean isKeyCardWinnerCardForUpgrade(int stichNumber) {
		return ((lastWinnerCard.suit.ordinal() == rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) && 
				   (lastWinnerCard.rank.ordinal() == rounds.getWinnerCardInRound(stichNumber).rank.ordinal()));
	}
	
	private boolean isNotColorAndNumberBlocked(int stichNumber) {
		// in Result: (¬-1 && ¬-1) &&  (0 || 0)
		return ((gameInfo.getSuitPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) != IMPOSSIBLE) &&
				(gameInfo.getRankPossibilityAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal()) != IMPOSSIBLE));
	}
	
	private boolean isColorOrNumberPossible(int stichNumber) {
		return ((gameInfo.getSuitPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) == POSSIBLE) ||
				(gameInfo.getRankPossibilityAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal()) == POSSIBLE));
	}
	
}
