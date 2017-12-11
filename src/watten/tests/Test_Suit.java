package watten.tests;

import static org.junit.Assert.*;

import static watten.CONSTANTS.*;
import static watten.Suit.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import watten.Suit;

public class Test_Suit {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test() {
		assertTrue(ACORN != BELL);
		assertTrue(ACORN.equals(ACORN));
		assertTrue(Suit.ACORN.ordinal() < Suit.HEART.ordinal());
		
		assertTrue(Suit.ACORN == Suit.values()[0]);
	}
	
	@Test
	public final void test_toString() {
		assertTrue(SUIT_NAME[AT][0].equals(ACORN.toString()));
		assertTrue(SUIT_NAME[AT][1].equals(HEART.toString()));
		assertTrue(SUIT_NAME[AT][2].equals(LEAVE.toString()));
		assertTrue(SUIT_NAME[AT][3].equals(BELL.toString()));
		
		assertFalse(SUIT_NAME[AT][0].equals(BELL.toString()));
		assertFalse(SUIT_NAME[AT][3].equals(ACORN.toString()));
	}


}
