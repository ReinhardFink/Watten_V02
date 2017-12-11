package watten.logic;

import static watten.logic.Possibility.*;

import java.util.Observable;

public class PossibilityArray extends Observable implements Cloneable {
	
	private Possibility[] array;
	
	public PossibilityArray(int size) {
		this.array = new Possibility[size];
		for (int i = 0; i < array.length; i++) array[i] = POSSIBLE;
		
	}
	
	public PossibilityArray clone() throws CloneNotSupportedException {
		PossibilityArray tempPossibilityArray = new PossibilityArray(array.length);
		for(int i = 0; i < array.length; i++) tempPossibilityArray.setValueAt(i,array[i]);
		return tempPossibilityArray;
	}
	
	public String toString() {
		StringBuffer tempString = new StringBuffer();
		tempString.append(super.toString());
		tempString.append("\n Values: ");
		for(int i = 0; i < array.length; i++) {
			tempString.append(" v[" + i + "]=" + array[i].possibility);
		}
		return tempString.toString();
	}
	
	public int getLength() { return array.length; }
	
	public void setValueAt(int position, Possibility value) {
		if(value != array[position]) this.setChanged();
		if(value == Possibility.SURE) {
			for(int pos = 0; pos < array.length; pos++) array[pos] = IMPOSSIBLE;
			array[position] = value;
		} else {
			array[position] = value;
			correctIfOnePossibleLeft();
		}
		this.notifyObservers(array.clone());
	}
	
	public Possibility get(int position) {
		return array[position];
	}
	
	private void correctIfOnePossibleLeft() {
		int countPossible = 0;
		int possiblePosition = 0;
		for(int position = 0; position < array.length; position++)
			if(array[position] == POSSIBLE) {
				countPossible++;
				possiblePosition = position;
			}
		if(countPossible == 1) array[possiblePosition] = SURE;
	}
	
	public int getPosOfSURE() {
		int position = array.length - 1;
		while(position >= 0 && array[position] != SURE) 
			position--;
		return position;
	}

	public boolean hasSURE() {
		return getPosOfSURE() != -1;
	}
}
