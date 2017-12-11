
/*
 * Class to handle possibility array for:
 * selected color <=> schlag: 4 colors
 * selected number <=> trumpf: 8/9 numbers
 */

package watten.logic;

import static watten.Rank.*;

import watten.CONSTANTS;
import watten.Card;
import watten.Rank;
import watten.Suit;


public class GameInfo implements Cloneable {
	
	private boolean mitGuatem; 
	private PossibilityArray schlag;
	private PossibilityArray trumpf;
	
	

	public GameInfo(boolean mitGuatem) {
		this.mitGuatem = mitGuatem;
		schlag = new PossibilityArray(Rank.values().length); 
		trumpf = new PossibilityArray(Suit.values().length);   
	}

	public GameInfo clone() throws CloneNotSupportedException {
		GameInfo tempResult = new GameInfo(mitGuatem);
		tempResult.schlag = schlag.clone();
		tempResult.trumpf = trumpf.clone();
		// we use the same verboseMessage
		return tempResult;
	}

	public String toString() {
		StringBuffer tempString = new StringBuffer();
		tempString.append(CONSTANTS.messageColor);
		tempString.append(":\n");
		tempString.append(trumpf.toString());
		tempString.append("\n");
		tempString.append(CONSTANTS.messageNumber);
		tempString.append(":\n");
		tempString.append(schlag.toString());
		return tempString.toString();
	}

	public PossibilityArray getSchlagPossibilityArray() {
		return schlag;
	}

	public PossibilityArray getTrumpfPossibilityArray() {
		return trumpf;
	}

	public boolean isMitGuatem() {
		return this.mitGuatem;
	}

	public void setNumberAt(int position, Possibility value) {
		schlag.setValueAt(position, value);
	}

	public void setColorAt(int position, Possibility value) {
		trumpf.setValueAt(position, value);
	}

	public Possibility getNumberPossibilityAt(int position) {
		return schlag.get(position);
	}

	public Possibility getColorPossibilityAt(int position) {
		return trumpf.get(position);
	}

	public Rank getSchlag() {
		return Rank.values()[schlag.getPosOfSURE()];
	}

	public Suit getTrumpf() {
		return Suit.values()[trumpf.getPosOfSURE()];
	}

	public boolean isSchlagFix() {
		return schlag.hasSURE();
	}

	public boolean isTrumpfFix() {
		return trumpf.hasSURE();
	}

	public boolean isRechterFix() {
		return (isSchlagFix() && isTrumpfFix());
	}

	public boolean isGuaterFix() {
		if (isSchlagFix() && getSchlag() == SIX)
			return false;
		return isRechterFix();
	}

	// getRechter() and getGater() return null if values not fix
	public Card getRechter() {
		if ((!isSchlagFix()) || !isTrumpfFix())
			return null;
		else
			return new Card(getTrumpf(), getSchlag());
	}

	public Card getGuater() {
		if (getRechter() == null)
			return null;
		else
			return getRechter().calcGuaterFromRechter();
	}
	
}
