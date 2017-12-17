package watten.logic;

import watten.CONSTANTS;

public class GameInfoMessage {
	
	private static StringBuffer verboseMessage = new StringBuffer();
	
	public static StringBuffer getVerboseMessage() {
		return GameInfoMessage.verboseMessage;
	}

	public static void setVerboseMessage(StringBuffer verboseMessage) {
		GameInfoMessage.verboseMessage = verboseMessage;
	}
	
	public static void appendVerboseMessage(String verboseMessage) {
		GameInfoMessage.verboseMessage.append(verboseMessage);
		GameInfoMessage.verboseMessage.append("\n");
	}
	
	public static void verbose(String message) {
		if (CONSTANTS.VERBOSE) {
			System.out.println(message);
		}
		appendVerboseMessage(message);
	}


}
