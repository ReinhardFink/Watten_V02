package watten;

import static watten.logic.Possibility.*;


import watten.CONSTANTS;
import watten.logic.GameInfo;
import watten.logic.GameInfoMessage;
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
			if (this.equals(gameInfo.getGuater())) {
				GameInfoMessage.verbose("Guater " + this + " beates " + card);
				return true;
			}
			// card is GUATER
			if (card.equals(gameInfo.getGuater())) {
				GameInfoMessage.verbose("Guater " + card + " beates " + this);
				return false;
			}
		}
		// from here no GUATER possible
		
		// test RECHTER
		if (gameInfo.isRechterFix()) {
			if(this.equals(gameInfo.getRechter())) {
				GameInfoMessage.verbose("Rechter " + this + " beates " + card);
				return true;
			}
			if(card.equals(gameInfo.getRechter())) {
				GameInfoMessage.verbose("Rechter " + card + " beates " + this);
				return false;
			}
		}
		// from here no RECHTER possible
			
		// test LINKER
		if (gameInfo.isSchlagFix()) {
			// FIRST LINKER beats
			if (this.rank == gameInfo.getSchlag()) {
				GameInfoMessage.verbose("Linker " + this + " beates Linker " + card);
				return true;
			}
			// second LINKER only if NO first LINKEr
			else if (card.rank == gameInfo.getSchlag()) {
				GameInfoMessage.verbose("Linker " + card + " beates " + this);
				return false;
			}
		}
		
		// from here no LINKER possible
		// test TRUMPF
		if (gameInfo.isTrumpfFix()) { 
			// test TRUMPF beats COLOR
			if (this.suit == gameInfo.getTrumpf() && card.suit != gameInfo.getTrumpf()) {
				GameInfoMessage.verbose("Trumpf " + this + " beates Suit " + card);
				return true; 
			}
			if (this.suit != gameInfo.getTrumpf() && card.suit == gameInfo.getTrumpf()) {
				GameInfoMessage.verbose("Trumpf " + card + " beates Suit " + this);
				return false; 
			}
			// TRUMPF beats TRUMPF
			if (this.suit == gameInfo.getTrumpf() && card.suit == gameInfo.getTrumpf()) {
				if (this.rank.ordinal() > card.rank.ordinal()) {
					GameInfoMessage.verbose("Trumpf " + this + " beates Trumpf " + card);
					return true;
				}
				else {
					GameInfoMessage.verbose("Trumpf " + card + " beates Trumpf " + this);
					return false;
				}
			}
			// test VELI
			if (this.suit != gameInfo.getTrumpf() && card.suit != gameInfo.getTrumpf()) {
				if (this.equals(CONSTANTS.VELI)) {
					GameInfoMessage.verbose("Veli " + this + " beates " + card);
					return true;
				}
				if (card.equals(CONSTANTS.VELI)) {
					GameInfoMessage.verbose("Veli " + card + " beates " + this);
					return false;
				}
			}
		}
		// from here no TRUMPF possible
		
		// SUIT vs SUIT
		// first SUIT beats second different SUIT
		if(this.suit.ordinal() != card.suit.ordinal()) {
			GameInfoMessage.verbose("Suit " + this + " beats " + card);
			return true;
		}
		// SUIT beats same SUIT
		if(this.rank.ordinal() >= card.rank.ordinal()) {
			GameInfoMessage.verbose("Suit " + card + " beats " + this);
			return true;
		}
		System.out.println("Error");
		return false;
	}
}
