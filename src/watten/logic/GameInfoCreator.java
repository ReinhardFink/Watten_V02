package watten.logic;

import static watten.Rank.*;
import static watten.logic.Possibility.*;

import java.util.Observable;
import java.util.Observer;

import watten.CONSTANTS;
import watten.Card;
import watten.Rounds;
import watten.Round;


public class GameInfoCreator implements Observer {

	private Rounds playedRounds;
	private GameInfo gameInfo;

	private Possibility farbStichPossibility;
	private Possibility trumpfStichPossibility;
	private Possibility linkerStichPosssibility;
	private Possibility rechterStichPossibility;
	private Possibility guaterStichPossibility;
	private boolean resultChanged;

	public GameInfoCreator(Rounds stiche, GameInfo gameInfo) {
		this.playedRounds = stiche;
		this.gameInfo = gameInfo;
		// set resultChanged = true, if PossibilityArrays are changed
		this.gameInfo.getSchlagPossibilityArray().addObserver(this);
		this.gameInfo.getTrumpfPossibilityArray().addObserver(this);
	}

	public void update(Observable observable, Object arg) {
		resultChanged = true;
	}

	// for testing
	public Possibility isFarbStich() {
		return farbStichPossibility;
	}
	// public void setFarbStich(int farbStich) { this.farbStich = farbStich; }

	public Possibility isTrumpfStich() {
		return trumpfStichPossibility;
	}
	// public void setTrumpfStich(int trumpfStich) { this.trumpfStich = trumpfStich;
	// }

	public Possibility isLinkerStich() {
		return linkerStichPosssibility;
	}
	// public void setLinkerStich(int linkerStich) { this.linkerStich = linkerStich;
	// }

	public Possibility isRechterStich() {
		return rechterStichPossibility;
	}
	// public void setRechterStich(int rechterStich) { this.rechterStich =
	// rechterStich; }

	public Possibility isGuaterStich() {
		return guaterStichPossibility;
	}
	// public void setGuaterStich(int guaterStich) { this.guaterStich = guaterStich;
	// }

	/*
	 * produces THE output message with the information collected in
	 * calculateNewResult()
	 */
	public String getMessage() {
		calculateNewGameInfo();
		StringBuffer message = new StringBuffer();
		message.append(CONSTANTS.messageHeader);
		message.append(playedRounds.size());
		message.append("\n");
		message.append(CONSTANTS.messageFarbStich);
		message.append(farbStichPossibility.toString() + "\n");
		message.append(CONSTANTS.messageTrumpfStich);
		message.append(trumpfStichPossibility.toString() + "\n");
		message.append(CONSTANTS.messageLinkerStich);
		message.append(linkerStichPosssibility.toString() + "\n");
		message.append(CONSTANTS.messageRechterStich);
		message.append(rechterStichPossibility.toString() + "\n");
		if (gameInfo.isMitGuatem()) {
			message.append(CONSTANTS.messageGuaterStich);
			message.append(guaterStichPossibility.toString() + "\n");
		}
		message.append(CONSTANTS.messageFooter);
		return message.toString();
	}

	/*
	 * the LOGIC starts here :-)
	 */
	private void calculateNewGameInfo() {
		setImpossibleNumbersToIMPOSSIBLE();
		do {
			resultChanged = false;
			runTest();
			runRules();
			runExcept4Rules();
		} while (resultChanged);

	}

	// public for testing
	public void setImpossibleNumbersToIMPOSSIBLE() {
		blockImpossibleNumbersInCurrentStich();
		if (gameInfo.isMitGuatem())
			repairWrongFromGuatemBlocked();
	}

	private void blockImpossibleNumbersInCurrentStich() {
		Round lastRound = playedRounds.last();
		for (Card toTestCard : lastRound) {
			if (toTestCard.rank == lastRound.getWinnerCard().rank)
				continue;
			if (gameInfo.isMitGuatem() 
					&& lastRound.getWinnerCard().rank != SIX
					&& toTestCard.rank == lastRound.getWinnerCard().calcRechterFromGuater().rank)
				continue;
			// block if number IMPOSSIBLE for Schlag
			gameInfo.setRankAt(toTestCard.rank.ordinal(), IMPOSSIBLE);
		}
	}

	private void repairWrongFromGuatemBlocked() {
		for (Round stich : playedRounds)
			// winner made "Farbstich"
			if ((gameInfo.getRankPossibilityAt(stich.getWinnerCard().rank.ordinal()) == IMPOSSIBLE)
					&& (gameInfo.getSuitPossibilityAt(stich.getWinnerCard().suit.ordinal()) == IMPOSSIBLE))
				for (Card card : stich)
					gameInfo.setRankAt(card.rank.ordinal(), IMPOSSIBLE);
	}

	private void runTest() {
		farbStichPossibility = new FarbStichTest(gameInfo, playedRounds).runTest();
		trumpfStichPossibility = new TrumpfStichTest(gameInfo, playedRounds).runTest();
		linkerStichPosssibility = new LinkerStichTest(gameInfo, playedRounds).runTest();
		rechterStichPossibility = new RechterStichTest(gameInfo, playedRounds).runTest();
		guaterStichPossibility = new GuaterStichTest(gameInfo, playedRounds).runTest();
	}

	private void runRules() {
		if (isTrumpfImpossible())
			lastWinnerIsNoTrumpf();
		if (isSchlagOrGuaterImpossible())
			lastWinnerIsNoSchlag();
	}

	private boolean isTrumpfImpossible() {
		return trumpfStichPossibility == IMPOSSIBLE && rechterStichPossibility == IMPOSSIBLE
				&& guaterStichPossibility == IMPOSSIBLE;
	}

	private boolean isSchlagOrGuaterImpossible() {
		return linkerStichPosssibility == IMPOSSIBLE && rechterStichPossibility == IMPOSSIBLE
				&& guaterStichPossibility == IMPOSSIBLE;
	}

	private void lastWinnerIsNoTrumpf() {
		gameInfo.setSuitAt(playedRounds.last().getWinnerCard().suit.ordinal(), IMPOSSIBLE);
	}

	private void lastWinnerIsNoSchlag() {
		gameInfo.setRankAt(playedRounds.last().getWinnerCard().rank.ordinal(), IMPOSSIBLE);
	}

	private void runExcept4Rules() {
		if (justGuaterStichPossible())
			lastWinnerIsGuater();
		if (justRechterStichPossible())
			lastWinnerIsRechter();
		if (justLinkerStichPossible())
			lastWinnerIsLinker();
		if (justTrumpfStichPossible())
			lastWinnerIsTrumpf();
		if (justFarbStichPossible())
			lastWinnerIsFarbe();
	}

	// logical Tests
	private boolean justGuaterStichPossible() {
		return (farbStichPossibility == IMPOSSIBLE && trumpfStichPossibility == IMPOSSIBLE
				&& linkerStichPosssibility == IMPOSSIBLE
				&& rechterStichPossibility == IMPOSSIBLE);
	}

	private boolean justRechterStichPossible() {
		return (farbStichPossibility == IMPOSSIBLE && trumpfStichPossibility == IMPOSSIBLE
				&& linkerStichPosssibility == IMPOSSIBLE
				&& guaterStichPossibility == IMPOSSIBLE);
	}

	private boolean justLinkerStichPossible() {
		return (farbStichPossibility == IMPOSSIBLE && trumpfStichPossibility == IMPOSSIBLE
				&& rechterStichPossibility == IMPOSSIBLE
				&& guaterStichPossibility == IMPOSSIBLE);
	}

	private boolean justTrumpfStichPossible() {
		return (farbStichPossibility == IMPOSSIBLE && linkerStichPosssibility == IMPOSSIBLE
				&& rechterStichPossibility == IMPOSSIBLE
				&& guaterStichPossibility == IMPOSSIBLE);
	}

	private boolean justFarbStichPossible() {
		return (trumpfStichPossibility == IMPOSSIBLE && linkerStichPosssibility == IMPOSSIBLE
				&& rechterStichPossibility == IMPOSSIBLE
				&& guaterStichPossibility == IMPOSSIBLE);
	}

	// set Values in Result
	private void lastWinnerIsGuater() {
		gameInfo.setRankAt(playedRounds.last().getWinnerCard().calcRechterFromGuater().rank.ordinal(),
				SURE);
		gameInfo.setSuitAt(playedRounds.last().getWinnerCard().suit.ordinal(), SURE);
	}

	private void lastWinnerIsRechter() {
		gameInfo.setRankAt(playedRounds.last().getWinnerCard().rank.ordinal(), SURE);
		gameInfo.setSuitAt(playedRounds.last().getWinnerCard().suit.ordinal(), SURE);
	}

	private void lastWinnerIsLinker() {
		gameInfo.setRankAt(playedRounds.last().getWinnerCard().rank.ordinal(), SURE);
		gameInfo.setSuitAt(playedRounds.last().getWinnerCard().suit.ordinal(),IMPOSSIBLE);
	}

	private void lastWinnerIsTrumpf() {
		gameInfo.setRankAt(playedRounds.last().getWinnerCard().rank.ordinal(), IMPOSSIBLE);
		gameInfo.setSuitAt(playedRounds.last().getWinnerCard().suit.ordinal(), SURE);
	}

	private void lastWinnerIsFarbe() {
		gameInfo.setRankAt(playedRounds.last().getWinnerCard().rank.ordinal(), IMPOSSIBLE);
		gameInfo.setSuitAt(playedRounds.last().getWinnerCard().suit.ordinal(), IMPOSSIBLE);
	}
}
