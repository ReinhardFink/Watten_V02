package watten.logic;

import static watten.logic.Possibility.*;

import watten.Rounds;

public class FarbStichTest extends BasisStichTest {
	
	public FarbStichTest(GameInfo result, Rounds playedRounds) {
		super(result,playedRounds);
	}
	
	public void defineTest() {
		setKeyCard(getRounds().last().getWinnerCard());
		setWinnerNewNumberResult(IMPOSSIBLE);
		setWinnerNewColorResult(IMPOSSIBLE);
	}
	
	public Possibility setTestSpezials() { return setAllColorsInLastStichToImpossible(); }
	
	// wird benötigt um den Fall 4 Farben im Stich z.B. e7, h7, l7, s8, 0 abzudecken
	// eine Farbe muss dann Trumpf sein!!
	private Possibility setAllColorsInLastStichToImpossible() {
		for(int position = 0; position < getRounds().get(getRounds().size()-1).cards.length; position++) {
			if(getResult().getColorPossibilityAt(getRounds().last().cards[position].suit.ordinal()) == SURE) 
				return IMPOSSIBLE; 
			else 
				getResult().setColorAt(getRounds().last().cards[position].suit.ordinal(),IMPOSSIBLE);
		}
		return POSSIBLE;
	}
}
