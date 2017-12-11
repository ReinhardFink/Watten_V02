package watten.logic;

import watten.Rounds;

public class GuaterStichTest extends BasisStichTest {
 
	public GuaterStichTest(GameInfo result, Rounds stiche) {
		super(result,stiche);
	}
	
	public void defineTest() {
		setKeyCard(getRounds().get(getRounds().size() - 1).getWinnerCard().calcRechterFromGuater());
		setWinnerNewNumberResult(Possibility.SURE);
		setWinnerNewColorResult(Possibility.SURE);
	}
	
	protected Possibility setTestSpezials() { 
		if(getResult().isMitGuatem()) return Possibility.POSSIBLE;
		else return Possibility.IMPOSSIBLE;
	}
}
