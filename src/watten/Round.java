package watten;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	
	private boolean hasCard(Card card) {
		int position = 0;
		boolean cardFound = false;
		while(position < size() && !cardFound) {
			cardFound = this.get(position).equals(card);
			position++;
		}
		return cardFound;
	}

	private boolean hasRank(Rank rank) {
		int position = 0;
		boolean rankFound = false;
		while(position < size() && !rankFound) {
			rankFound = this.get(position).rank == rank;
			position++;
		}
		return rankFound;
	}
	
	private boolean hasSuit(Suit color) {
		int position = 0;
		boolean colorFound = false;
		while(position < size() && !colorFound) {
			colorFound = this.get(position).suit == color;
			position++;
		}
		return colorFound;
	}
	
	private boolean has4Suits() {
		return getNumberOfSuits() == Suit.values().length;
	}
	
	//public for testing
	private int getNumberOfSuits() {
		int numberOfSuits = 0;
		int[] suitMarker = new int[this.size()];
		for (Card card : this) suitMarker[card.suit.ordinal()] = 1;
		for (int i : suitMarker) numberOfSuits += i;
		return numberOfSuits;
	}
	
	// just for fun :-)
	private List<Card> getCardListWith(Predicate<Card> p) {
		return this.stream().filter(p).collect(Collectors.toList());
	}

	private boolean isWinnerBiggestInSuit() {
		for (Card card : this)
			if (this.get(winner).suit == card.suit && this.get(winner).rank.ordinal() < card.rank.ordinal()) return false;
		return true;
	}
		
}
