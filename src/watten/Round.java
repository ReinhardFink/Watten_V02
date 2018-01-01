package watten;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Round extends ArrayList<Card> {
	private int winner;
	
	public Round(Card card1, Card card2, Card card3, Card card4, int winner) {
		this.add(card1);
		this.add(card2);
		this.add(card3);
		this.add(card4);
		this.winner = winner;
	}
	
	public Card getWinnerCard() {
		return this.get(winner);
	}	
}
