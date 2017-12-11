package watten.tests;

import watten.logic.Possibility;
import watten.logic.PossibilityArray;
import junit.framework.TestCase;

public class Test_PossibilityArray extends TestCase {
	
	public void test_clone() {
		PossibilityArray pa = new PossibilityArray(10);
		pa.setValueAt(2,Possibility.IMPOSSIBLE);
		pa.setValueAt(5,Possibility.IMPOSSIBLE);
		pa.setValueAt(9,Possibility.IMPOSSIBLE);
		PossibilityArray paClone;
		try {
			paClone = pa.clone();
			// Test ob Klone nach klonen gleich!
			for(int i = 0; i < pa.getLength(); i++)
				assertEquals(paClone.get(i),pa.get(i));
			// Test ob Klone nach Veraenderung verschieden!
			paClone.setValueAt(0,Possibility.IMPOSSIBLE);
			assertEquals(Possibility.POSSIBLE,pa.get(0));
			assertEquals(Possibility.IMPOSSIBLE,paClone.get(0));
		} catch(CloneNotSupportedException e) { 
			System.out.println("PossibilityArray konnte nicht geklont werden!");
		}
	}
	
	public void test_setValueAt_All_0() {
		PossibilityArray pa = new PossibilityArray(4);
		for(int i = 0; i < pa.getLength(); i++)
			assertEquals(Possibility.POSSIBLE,pa.get(i));
	}
	
	public void test_setValueAt_correct_If_Just_1_Possibly_Sure_Left() {
		PossibilityArray pa = new PossibilityArray(4);
		// test if 0 is POSSIBILITY.IMPOSSIBLE
		pa.setValueAt(0,Possibility.IMPOSSIBLE);
		assertEquals(Possibility.IMPOSSIBLE,pa.get(0));
		// test if 1 is POSSIBILITY.IMPOSSIBLE
		pa.setValueAt(1,Possibility.IMPOSSIBLE);
		assertEquals(Possibility.IMPOSSIBLE,pa.get(1));
		// test if 2 is POSSIBILITY.IMPOSSIBLE
		pa.setValueAt(2,Possibility.IMPOSSIBLE);
		assertEquals(Possibility.IMPOSSIBLE,pa.get(2));
		// test if 3 is POSSIBILITY.SURE
		assertEquals(Possibility.SURE,pa.get(3));
	}
	
	public void test_setValueAt_set_1_Sure() {
		PossibilityArray pa = new PossibilityArray(4);
		pa.setValueAt(2,Possibility.SURE);
		assertEquals(Possibility.SURE,pa.get(2));
		assertEquals(Possibility.IMPOSSIBLE,pa.get(0));
		assertEquals(Possibility.IMPOSSIBLE,pa.get(1));
		assertEquals(Possibility.IMPOSSIBLE,pa.get(3));
	}
	
}
