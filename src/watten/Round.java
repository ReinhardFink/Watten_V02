package watten;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Round {
	public Card[] cards;
	public int winner;
	
	public Round(Card card1, Card card2, Card card3, Card card4, int winner) {
		cards = new Card[4];
		cards[0] = card1;
		cards[1] = card2;
		cards[2] = card3;
		cards[3] = card4;
		this.winner = winner;
	}
	
	public Card getWinnerCard() {
		return cards[winner];
	}
	
	public boolean hasCard(Card card) {
		int position = 0;
		boolean cardFound = false;
		while(position < cards.length && !cardFound) {
			cardFound = cards[position].equals(card);
			position++;
		}
		return cardFound;
	}

	public boolean hasRank(Rank rank) {
		int position = 0;
		boolean rankFound = false;
		while(position < cards.length && !rankFound) {
			rankFound = cards[position].rank == rank;
			position++;
		}
		return rankFound;
	}
	
	public boolean hasSuit(Suit color) {
		int position = 0;
		boolean colorFound = false;
		while(position < cards.length && !colorFound) {
			colorFound = cards[position].suit == color;
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
		int[] suitMarker = new int[cards.length];
		for (Card card : cards) suitMarker[card.suit.ordinal()] = 1;
		for (int i : suitMarker) numberOfSuits += i;
		return numberOfSuits;
	}
	
	// just for fun :-)
	public List<Card> getCardListWith(Predicate<Card> p) {
		return Arrays.stream(cards).filter(p).collect(Collectors.toList());
	}

	public boolean isWinnerBiggestInSuit() {
		for (Card card : cards)
			if (cards[winner].suit == card.suit && cards[winner].rank.ordinal() < card.rank.ordinal()) return false;
		return true;
	}
		
}
