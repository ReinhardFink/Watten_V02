package watten.simple;

import java.util.ArrayList;

import watten.Card;

@SuppressWarnings("serial")
public class Rounds_simple extends ArrayList<Round_simple> {
	
	public Round_simple last() {
		return this.get(size() - 1);
	}
	
	public Card getWinnerCardInRound(int i) {
		return get(i).getWinnerCard();
	}
}
