package watten.logic;

import watten.CONSTANTS;

public enum Possibility {
	
	IMPOSSIBLE(-1), 
	POSSIBLE(0), 
	SURE(+1);
	
	private Possibility(int possibility) {
		this.possibility = possibility;
	}
	
	public final int possibility;
	
	public String toString() {
	    return CONSTANTS.POSSIBILITY_NAME[CONSTANTS.LANG][this.ordinal()];
	}
	
	public Possibility opposite() {
		if (this == SURE)
			return IMPOSSIBLE;
		else if (this == IMPOSSIBLE)
			return SURE;
		return POSSIBLE;
	}
}
