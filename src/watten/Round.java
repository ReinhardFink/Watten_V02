package watten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Round extends ArrayList<Card> {
	//public Card[] cards;
	public int winner;
	
	public Round(Card card1, Card card2, Card card3, Card card4, int winner) {
		//cards = new Card[4];
		//cards[0] = card1;
		//cards[1] = card2;
		//cards[2] = card3;
		//cards[3] = card4;
		this.add(card1);
		this.add(card2);
		this.add(card3);
		this.add(card4);
		this.winner = winner;
	}
	
	public Card getWinnerCard() {
		return this.get(winner);
	}
	
	public boolean hasCard(Card card) {
		int position = 0;
		boolean cardFound = false;
		while(position < size() && !cardFound) {
			cardFound = this.get(position).equals(card);
			position++;
		}
		return cardFound;
	}

	public boolean hasRank(Rank rank) {
		int position = 0;
		boolean rankFound = false;
		while(position < size() && !rankFound) {
			rankFound = this.get(position).rank == rank;
			position++;
		}
		return rankFound;
	}
	
	public boolean hasSuit(Suit color) {
		int position = 0;
		boolean colorFound = false;
		while(position < size() && !colorFound) {
			colorFound = this.get(position).suit == color;
			position++;
		}
		return colorFound;
	}
	
	public boolean has4Suits() {
		return getNumberOfSuits() == Suit.values().length;
	}
	
	//public for testing
	public int getNumberOfSuits() {
		int numberOfSuits = 0;
		int[] suitMarker = new int[this.size()];
		for (Card card : this) suitMarker[card.suit.ordinal()] = 1;
		for (int i : suitMarker) numberOfSuits += i;
		return numberOfSuits;
	}
	
	// just for fun :-)
	public List<Card> getCardListWith(Predicate<Card> p) {
		return this.stream().filter(p).collect(Collectors.toList());
	}

	public boolean isWinnerBiggestInSuit() {
		for (Card card : this)
			if (this.get(winner).suit == card.suit && this.get(winner).rank.ordinal() < card.rank.ordinal()) return false;
		return true;
	}
		
}
