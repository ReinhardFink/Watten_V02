package watten;

public enum Suit {
	ACORN, HEART, LEAVE, BELL;
	
	public String toString() {
	    return CONSTANTS.SUIT_NAME[CONSTANTS.LANG][this.ordinal()];
	}
}
