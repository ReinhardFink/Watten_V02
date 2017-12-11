package watten.tests;

import watten.Card;

import static watten.Suit.*;
import static watten.Rank.*;

public class CardDeck {
	
	public Card hA, hK, hO, hU, h10, h9, h8, h7;
	public Card sA, sK, sO, sU, s10, s9, s8, s7, s6;
	public Card eA, eK, eO, eU, e10, e9, e8, e7;
	public Card lA, lK, lO, lU, l10, l9, l8, l7;

	public CardDeck() {
		hA = new Card(HEART, ACE);
		hK = new Card(HEART, KING);
		hO = new Card(HEART, KNIGHT);
		hU = new Card(HEART, JACK);
		h10 = new Card(HEART, TEN);
		h9 = new Card(HEART, NINE);
		h8 = new Card(HEART, EIGHT);
		h7 = new Card(HEART, SEVEN);
		
		sA = new Card(BELL, ACE);
		sK = new Card(BELL, KING);
		sO = new Card(BELL, KNIGHT);
		sU = new Card(BELL, JACK);
		s10 = new Card(BELL, TEN);
		s9 = new Card(BELL, NINE);
		s8 = new Card(BELL, EIGHT);
		s7 = new Card(BELL, SEVEN);
		s6 = new Card(BELL, SIX);

		eA = new Card(ACORN, ACE);
		eK = new Card(ACORN, KING);
		eO = new Card(ACORN, KNIGHT);
		eU = new Card(ACORN, JACK);
		e10 = new Card(ACORN, TEN);
		e9 = new Card(ACORN, NINE);
		e8 = new Card(ACORN, EIGHT);
		e7 = new Card(ACORN, SEVEN);

		lA = new Card(LEAVE, ACE);
		lK = new Card(LEAVE, KING);
		lO = new Card(LEAVE, KNIGHT);
		lU = new Card(LEAVE, JACK);
		l10 = new Card(LEAVE, TEN);
		l9 = new Card(LEAVE, NINE);
		l8 = new Card(LEAVE, EIGHT);
		l7 = new Card(LEAVE, SEVEN);
	}
}
