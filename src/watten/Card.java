package watten;

import static watten.logic.Possibility.*;


import watten.CONSTANTS;
import watten.logic.GameInfo;
import watten.logic.Possibility;

import static watten.Rank.*;

public class Card {
	
	public Suit suit;
	public Rank rank;
	
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	public Card(Card card) {
		this.suit = card.suit;
		this.rank = card.rank;
	}
	
	public boolean equals(Card card) {
		return (card.suit == this.suit && card.rank == this.rank);
	}
	
	public String toString() {
		return new String(suit + " " + rank);
	}
	
	public Card calcGuaterFromRechter() {
		if(this.rank == SIX) return null;
		Rank newRank = Rank.values()[(this.rank.ordinal() + 1) % Rank.values().length];
		if(newRank == SIX) newRank = SEVEN;
		return new Card(suit, newRank);
	}
	
	public Card calcRechterFromGuater() {
		if(this.rank == SIX) return null;
		Rank newRank = Rank.values()[(this.rank.ordinal() - 1 + Rank.values().length) % (Rank.values().length)];
		if(newRank == SIX) newRank = ACE;
		return new Card(suit, newRank);
	}
	
	public boolean beats(Card card, GameInfo gameInfo) {
		
		// test GUATER
		if(gameInfo.isMitGuatem() && gameInfo.isGuaterFix()) {
			// this is GUATER
			if (this.equals(gameInfo.getGuater())) return true;
			// card is GUATER
			if (card.equals(gameInfo.getGuater())) return false;
		}
		// from here no GUATER possible
		
		// test RECHTER
		if (gameInfo.isRechterFix()) {
			if(this.equals(gameInfo.getRechter())) return true;
			if(card.equals(gameInfo.getRechter())) return false;
		}
		// from here no RECHTER possible
			
		// test LINKER
		if (gameInfo.isSchlagFix()) {
			// FIRST LINKER beats
			if (this.rank == gameInfo.getSchlag()) return true;
			// second LINKER only if NO first LINKEr
			else if (card.rank == gameInfo.getSchlag()) return false;
		}
		
		// from here no LINKER possible
		// test TRUMPF
		if (gameInfo.isTrumpfFix()) { 
			// test TRUMPF beats COLOR
			if (this.suit == gameInfo.getTrumpf() && card.suit != gameInfo.getTrumpf()) return true; 
			if (this.suit != gameInfo.getTrumpf() && card.suit == gameInfo.getTrumpf()) return false; 
			// TRUMPF beats TRUMPF
			if (this.suit == gameInfo.getTrumpf() && card.suit == gameInfo.getTrumpf()) {
				if (this.rank.ordinal() > card.rank.ordinal()) return true;
				else return false;
			}
			// test VELI
			if (this.suit != gameInfo.getTrumpf() && card.suit != gameInfo.getTrumpf()) {
				if (this.equals(CONSTANTS.VELI)) return true;
				if (card.equals(CONSTANTS.VELI)) return false;
			}
		}
		// from here no TRUMPF possible
		
		// SUIT vs SUIT
		// first SUIT beats second different SUIT
		if(this.suit.ordinal() != card.suit.ordinal()) return true;
		// SUIT beats same SUIT
		if(this.rank.ordinal() >= card.rank.ordinal()) return true;
		return false;
	}

	public Possibility getBeatPossibility(Card card, GameInfo gameInfo) {
		
		// test GUATER
		if(gameInfo.isMitGuatem() && gameInfo.isGuaterFix()) {
			// this is GUATER
			if (this.equals(gameInfo.getGuater())) return SURE;
			// card is GUATER
			if (card.equals(gameInfo.getGuater())) return IMPOSSIBLE;
		}
		// from here no GUATER possible
		
		// test RECHTER
		if (gameInfo.isRechterFix()) {
			if(this.equals(gameInfo.getRechter())) return SURE;
			if(card.equals(gameInfo.getRechter())) return IMPOSSIBLE;
		}
		// from here no RECHTER possible
			
		// test LINKER
		if (gameInfo.isSchlagFix()) {
			// FIRST LINKER beats
			if (this.rank == gameInfo.getSchlag()) return SURE;
			// second LINKER only if NO first LINKEr
			else if (card.rank == gameInfo.getSchlag()) return IMPOSSIBLE;
		}
		
		// from here no LINKER possible
		// test TRUMPF
		if (gameInfo.isTrumpfFix()) { 
			// test TRUMPF beats COLOR
			if (this.suit == gameInfo.getTrumpf() && card.suit != gameInfo.getTrumpf()) return SURE; 
			if (this.suit != gameInfo.getTrumpf() && card.suit == gameInfo.getTrumpf()) return IMPOSSIBLE; 
			// TRUMPF beats TRUMPF
			if (this.suit == gameInfo.getTrumpf() && card.suit == gameInfo.getTrumpf()) {
				if (this.rank.ordinal() > card.rank.ordinal()) return SURE;
				else return IMPOSSIBLE;
			}
			if (this.suit != gameInfo.getTrumpf() && card.suit != gameInfo.getTrumpf()) {
				if (this.rank.ordinal() > card.rank.ordinal()) return SURE;
				else return IMPOSSIBLE;
			}
			// test VELI
			if (this.suit != gameInfo.getTrumpf() && card.suit != gameInfo.getTrumpf()) {
				if (this.equals(CONSTANTS.VELI)) return SURE;
				if (card.equals(CONSTANTS.VELI)) return IMPOSSIBLE;
			}
		}
		// from here no TRUMPF possible
		
		// SUIT vs SUIT
		// first SUIT beats second different SUIT
		if(this.suit.ordinal() != card.suit.ordinal()) return SURE;
		// SUIT beats same SUIT
		if(this.rank.ordinal() >= card.rank.ordinal()) return IMPOSSIBLE;
		return IMPOSSIBLE;
	}
}
