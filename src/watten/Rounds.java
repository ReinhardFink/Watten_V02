package watten;

import java.util.ArrayList;

import watten.Round;

@SuppressWarnings("serial")
public class Rounds extends ArrayList<Round> {
	
	public Round last() {
		return this.get(size() - 1);
	}
	
	public Card getWinnerCardInRound(int i) {
		return get(i).getWinnerCard();
	}
}
