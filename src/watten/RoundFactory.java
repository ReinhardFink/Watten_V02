package watten;

import java.util.StringTokenizer;

import static watten.Suit.*;
import static watten.Rank.*;


public class RoundFactory {

	private static final int NO_WINNER = -1;
	private static final int NO_POSITION = -1;

	private Card[][] createdCards;
	private StringBuffer errorMessage;

	public RoundFactory(StringBuffer errorMessage) {
		this.errorMessage = errorMessage;
		this.createdCards = new Card[Suit.values().length][Rank.values().length];
		createUnusedSixs();
	}

	public Round createStich(String inputString) {
		Card[] playedCardsInRound = new Card[CONSTANTS.CARDS_IN_STICH];
		// check input for double or wrong cards
		int winnerPosition = NO_POSITION;;
		int currentCardNumber = 0;
		StringTokenizer stringTokenizer = new StringTokenizer(inputString, CONSTANTS.QUOTE_STRING);
		while (currentCardNumber < playedCardsInRound.length) {
			playedCardsInRound[currentCardNumber] = createCard(stringTokenizer.nextToken());
			if (playedCardsInRound[currentCardNumber] == null) {
				errorMessage.append(CONSTANTS.ERROR_Could_Not_Create_Stich);
				for (int i = 0; i < currentCardNumber; i++)
					resetCard(playedCardsInRound[i]);
				return null;
			}
			currentCardNumber++;
		}
		// check winner number for right interval
		try {
			winnerPosition = new Integer(stringTokenizer.nextToken()).intValue() - 1;
		} catch (NumberFormatException e) {
			winnerPosition = NO_WINNER;
		}
		if (winnerPosition < 0 || winnerPosition > 3) {
			for (int i = 0; i < playedCardsInRound.length; i++)
				resetCard(playedCardsInRound[i]);
			errorMessage.append(CONSTANTS.ERROR_Wrong_WinnerNumber);
			errorMessage.append(CONSTANTS.ERROR_Could_Not_Create_Stich);
			return null;
		}
		return new Round(playedCardsInRound[0], playedCardsInRound[1], playedCardsInRound[2], playedCardsInRound[3],
				winnerPosition);
	}

	private void createUnusedSixs() {
		createdCards[HEART.ordinal()][SIX.ordinal()] = new Card(HEART,
				SIX);
		createdCards[ACORN.ordinal()][SIX.ordinal()] = new Card(ACORN,
				SIX);
		createdCards[LEAVE.ordinal()][SIX.ordinal()] = new Card(LEAVE,
				SIX);
	}

	public Card createCard(String cardShortCut) {
		if (cardShortCut == null)
			return null;
		int tokenReadCount = 0;
		char[] tokenArray = cardShortCut.toLowerCase().toCharArray();
		Rank number = null;
		Suit color = null;
		while (tokenReadCount < tokenArray.length) {
			char token = tokenArray[tokenReadCount];
			switch (token) {
			// setUp Color
			case 'e':
				color = ACORN;
				break;
			case 'h':
				color = HEART;
				break;
			case 'l':
				color = LEAVE;
				break;
			case 's':
				color = BELL;
				break;
			// setUp Number
			case '0':
				break;
			case '6':
				number = SIX;
				break;
			case '7':
				number = SEVEN;
				break;
			case '8':
				number = EIGHT;
				break;
			case '9':
				number = NINE;
				break;
			case '1':
				number = TEN;
				break;
			case 'u':
				number = JACK;
				break;
			case 'o':
				number = KNIGHT;
				break;
			case 'k':
				number = KING;
				break;
			case 'a':
				number = ACE;
				break;

			default:
				System.out.println("Oje hierher sollten wir nicht kommen");
				break;
			}
			tokenReadCount++;
		}
		if (isCardCreatable(color, number))
			return (createdCards[color.ordinal()][number.ordinal()] = new Card(color, number));
		else {
			errorMessage.append(CONSTANTS.ERROR_Could_Not_Create_Card1);
			errorMessage.append(cardShortCut);
			errorMessage.append(CONSTANTS.ERROR_Could_Not_Create_Card2);
			return null;
		}
	}

	private boolean isCardCreatable(Suit color, Rank number) {
		return color != null && number != null && createdCards[color.ordinal()][number.ordinal()] == null;
	}

	public void resetCard(Card card) {
		createdCards[card.suit.ordinal()][card.rank.ordinal()] = null;
	}

}
