package watten.logic;

import static watten.logic.Possibility.*;

import watten.CONSTANTS;
import watten.Card;
import watten.Rounds;

public abstract class BasisStichTest {
	
	private final boolean VERBOSE = true;
	
	protected GameInfo gameInfo;
	protected Rounds rounds;
	protected Card keyCard;
	protected Possibility winnerNewNumberResult; 
	protected Possibility winnerNewColorResult;
	
	// Constructor
	public BasisStichTest(GameInfo result, Rounds playedRounds) {
		try {
			this.gameInfo = result.clone();
		} 
		catch(CloneNotSupportedException e) {
			System.out.println("could not clone: " + result); 
		}	
		this.rounds = playedRounds;
		this.winnerNewNumberResult = null; 
		this.winnerNewColorResult = null;

	}
	// Setters and Getters
	public GameInfo getResult() { return gameInfo; }
	
	public Rounds getRounds() { return rounds; }
	
	public void setKeyCard(Card keyCard) { this.keyCard = keyCard; }
	
	public void setWinnerNewNumberResult(Possibility winnerNewNumberResult) {
		this.winnerNewNumberResult = winnerNewNumberResult;
	}
		
	public void setWinnerNewColorResult(Possibility winnerNewColorResult) { 
		this.winnerNewColorResult = winnerNewColorResult;
	}
	
	// runTest the strategy-method
	public Possibility runTest() {
		//GameInfoMessage.appendVerboseMessage("TESTING for: Basisstich");
		if(blockVeli()) return IMPOSSIBLE;
		defineTest();
		if(gameInfo.isRechterFix()) return makeTestsWithRechterFix();
		if(setUpResult() != POSSIBLE) return setUpResult();
		if(setTestSpezials() != POSSIBLE) return setTestSpezials();
		if(testPreviousWinners() != POSSIBLE) return testPreviousWinners();
		return POSSIBLE;
	}
	
	// Strategy methods, must be overwritten in subclasses
	protected boolean blockVeli() { 
		if(rounds.getWinnerCardInRound(rounds.size() - 1).equals(CONSTANTS.VELI)) return true;
		else return false;
	}
	
	/*
	 * set Keycard and 
	 * setWinnerNewNumberResult( ...) and
	 * setWinnerNewColorResult( ...);
	 */
	public abstract void defineTest();
	
	protected Possibility setTestSpezials() { return POSSIBLE; }
	
	// private Strategiemethoden
	// behandelt alle Tests, wenn der Rechte fix ist.
	private Possibility makeTestsWithRechterFix() { 
		if(isGuaterInTrumpfStichTest()) return IMPOSSIBLE; // Spezialfall ausschliessen
		if(winnerCardFitsStichTest()) return SURE; // alle anderen Fälle
		else return IMPOSSIBLE;
	}
	
	private boolean isGuaterInTrumpfStichTest() {
		return winnerNewNumberResult == IMPOSSIBLE && 
			   winnerNewColorResult == SURE && 
			   gameInfo.isGuaterFix() &&
			   gameInfo.getGuater().equals(keyCard);
	}
	private boolean winnerCardFitsStichTest() { // Farbe, Trump
		return ((gameInfo.getNumberPossibilityAt(keyCard.rank.ordinal()) == winnerNewNumberResult) && 
		        (gameInfo.getColorPossibilityAt(keyCard.suit.ordinal()) == winnerNewColorResult));
	}
	
	private Possibility setUpResult() {
		if(hasStichConfictWithResult()) return IMPOSSIBLE;
		else {
			gameInfo.setNumberAt(keyCard.rank.ordinal(),winnerNewNumberResult);
			gameInfo.setColorAt(keyCard.suit.ordinal(),winnerNewColorResult);
			return POSSIBLE;
		}
	}
	
	private boolean hasStichConfictWithResult() {
		gameInfo.getColorPossibilityAt(keyCard.suit.ordinal());
		winnerNewColorResult.opposite();
		return (gameInfo.getNumberPossibilityAt(keyCard.rank.ordinal()) == winnerNewNumberResult.opposite()) 
				|| 
			   ((winnerNewColorResult != POSSIBLE) 
					   && (gameInfo.getColorPossibilityAt(keyCard.suit.ordinal()) == winnerNewColorResult.opposite()));
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
		for(int cardNumber = 1; cardNumber < rounds.get(stichNumber).cards.length; cardNumber++) 
			if(!(rounds.get(stichNumber).cards[currentWinnerPosition].beats(rounds.get(stichNumber).cards[cardNumber],gameInfo)))
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
			if(gameInfo.getColorPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) == POSSIBLE) {
				GameInfo saveResult = gameInfo.clone();
				gameInfo.setColorAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal(),SURE);
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
			if(gameInfo.getNumberPossibilityAt(rounds.getWinnerCardInRound(stichNumber).rank.ordinal()) == POSSIBLE) {
				GameInfo saveResult = gameInfo.clone();
				gameInfo.setNumberAt(rounds.getWinnerCardInRound(stichNumber).rank.ordinal(),SURE);
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
				gameInfo.setColorAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal(),SURE);
				gameInfo.setNumberAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal(),SURE);
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
		return ((keyCard.suit.ordinal() == rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) && 
				   (keyCard.rank.ordinal() == rounds.getWinnerCardInRound(stichNumber).rank.ordinal()));
	}
	
	private boolean isNotColorAndNumberBlocked(int stichNumber) {
		// in Result: (¬-1 && ¬-1) &&  (0 || 0)
		return ((gameInfo.getColorPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) != IMPOSSIBLE) &&
				(gameInfo.getNumberPossibilityAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal()) != IMPOSSIBLE));
	}
	
	private boolean isColorOrNumberPossible(int stichNumber) {
		return ((gameInfo.getColorPossibilityAt(rounds.getWinnerCardInRound(stichNumber).suit.ordinal()) == POSSIBLE) ||
				(gameInfo.getNumberPossibilityAt(rounds.getWinnerCardInRound(stichNumber).calcRechterFromGuater().rank.ordinal()) == POSSIBLE));
	}
	
}
