/**
 * CellTest.java
 * This class is responsible for testing Cell class.
 * 
 * @author Hamad Ayaz, James Lee
 * @since 2023-08-06
 */

package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import model.Cell;

public class CellTest {

	@Test
	public void testGuessandVal() {
		int expectedOutput;
		int actualOutput;
		String expectedOutput2;
		String actualOutput2;

		Cell cell = new Cell(2);

		cell.setGuess(3);
		cell.setGuess(4);
		cell.setGuess(7);

		expectedOutput = 2;
		actualOutput = cell.getValue();
		assertEquals(expectedOutput, actualOutput);

		cell.removeValue(0);

		expectedOutput = 0;
		actualOutput = cell.getValue();
		assertEquals(expectedOutput, actualOutput);

		expectedOutput2 = "    3 \n" + "4     \n" + "7     ";
		actualOutput2 = cell.guessToString();

		assertEquals(expectedOutput2, actualOutput2);

	}

	@Test
	public void testReset() {
		String expectedOutput;
		String actualOutput;

		Cell cell = new Cell();

		cell.setGuess(1);
		cell.setGuess(2);
		cell.setGuess(3);

		cell.resetCell();
		expectedOutput = "      \n" + "      \n" + "      ";
		actualOutput = cell.guessToString();
		assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void testRemoveGuess() {
		String expectedOutput;
		String actualOutput;
		Cell cell = new Cell();

		cell.setGuess(1);
		cell.setGuess(9);

		expectedOutput = "1     \n" + "      \n" + "    9 ";
		actualOutput = cell.guessToString();

		assertEquals(expectedOutput, actualOutput);

		cell.removeGuess(1);

		expectedOutput = "      \n" + "      \n" + "    9 ";
		actualOutput = cell.guessToString();
		assertEquals(expectedOutput, actualOutput);

		cell.removeGuess(9);

		expectedOutput = "      \n" + "      \n" + "      ";
		actualOutput = cell.guessToString();
		assertEquals(expectedOutput, actualOutput);

	}

	@Test
	public void testHelperMethods() {
		Cell cell = new Cell();

		assertEquals(cell.getValue(), 0);

		assertFalse(cell.isSet());

		cell.setGuess(1);
		cell.setGuess(4);
		cell.setGuess(7);

		assertTrue(cell.getGuess(1));
		assertTrue(cell.getGuess(4));
		assertTrue(cell.getGuess(7));

		assertFalse(cell.getGuess(2));
		assertFalse(cell.getGuess(5));
		assertFalse(cell.getGuess(8));

		cell.setValue(7);
		assertTrue(!cell.isSet());
		assertEquals(cell.getValue(), 7);

		cell.chooseSet(false);
		assertTrue(!cell.isSet());

	}
}