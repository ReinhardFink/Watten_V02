package watten.logic;

import watten.Rounds;

public class LinkerStichTest extends BasisStichTest {
 
	public LinkerStichTest(GameInfo result, Rounds stiche) {
		super(result,stiche);
	}
	
	public void defineTest() {
		setKeyCard(getRounds().get(getRounds().size() - 1).getWinnerCard());
		setWinnerNewNumberResult(Possibility.SURE);
		setWinnerNewColorResult(Possibility.IMPOSSIBLE);
	}
}
