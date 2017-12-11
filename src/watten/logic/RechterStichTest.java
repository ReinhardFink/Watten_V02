package watten.logic;

import watten.Rounds;

public class RechterStichTest extends BasisStichTest {
 
	public RechterStichTest(GameInfo result, Rounds stiche) {
		super(result,stiche);
	}
	
	protected boolean blockVeli() { 
		return false;
	}
	
	public void defineTest() {
		setKeyCard(getRounds().get(getRounds().size() - 1).getWinnerCard());
		setWinnerNewNumberResult(Possibility.SURE);
		setWinnerNewColorResult(Possibility.SURE);
	}
}
