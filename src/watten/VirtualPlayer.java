package watten;

import watten.logic.GameInfo;
import watten.logic.GameInfoCreator;

//import watten.old.StichFactory;
 
public class VirtualPlayer {
	
	private Rounds playedRounds;
	private GameInfo result;
	private RoundFactory stichFactory;
	private GameInfoCreator stichCalculator;
	private String message;
	private StringBuffer errorMessage;

	public VirtualPlayer(boolean mitGuatem) {
		// create ArrayList handling the played rounds
		this.playedRounds = new Rounds();
		// result with possibility arrays for color & number
		this.result = new GameInfo(mitGuatem);
		// factory to create and check inputs
		this.errorMessage = new StringBuffer();
		this.stichFactory = new RoundFactory(errorMessage);
		// start the locic here
		this.stichCalculator = new GameInfoCreator(playedRounds, result);
		this.message = "";
	}
	
	public GameInfo getResult() { return result; }
	
	public String getMessage() { return message.toString(); }
	
	public String getErrorMessage() { return errorMessage.toString(); }
	
	public boolean isAddStichPossible(String stichString) {
		Round stich = stichFactory.createStich(stichString);
		if(stich == null) return false;
		playedRounds.add(stich);
		message = stichCalculator.getMessage();
		return true;
	}
}
