package watten;

public enum Rank {
	SIX, SEVEN, EIGHT, NINE, TEN, JACK, KNIGHT, KING, ACE;
	
	public String toString() {
	    return CONSTANTS.RANK_NAME[CONSTANTS.LANG][this.ordinal()];
	}

}
