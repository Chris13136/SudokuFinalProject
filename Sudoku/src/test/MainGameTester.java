/**
 * MainGameTester.java
 * This class is responsible for testing MainGame
 * 
 * @author Hamad Ayaz
 * @since 2023-08-06
 */

package test;

import model.MainGame;
import model.Cell;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainGameTester {

	@Test
	public void testResetGuess() {
		MainGame game = new MainGame();
		// Set a guess value
		int guessValue = 5;
		game.setCellGuess(0, 0, guessValue);

		// Reset the guess
		game.resetGuess(0, 0);

		// Verify the guess has been reset
		assertEquals(false, game.gameboard[0][0].getGuess(5));
	}

	@Test
	public void testGetValue() {
		MainGame game = new MainGame();
		// Iterate through the board, find a cell that is not set
		outerLoop: for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!game.isSet(i, j)) {
					int expectedValue = 5; // or any value you choose
					game.setCellValue(i, j, expectedValue);
					assertEquals(expectedValue, game.getValue(i, j));
					break outerLoop;
				}
			}
		}
	}

	@Test
	public void testInitialization() {
		MainGame game = new MainGame();

		assertNotNull(game.gameboard);
		assertTrue(game.errorCheck());
	}

	@Test
	public void testResetCell() {
		MainGame game = new MainGame();
		game.resetBoard();
		game.setCellValue(1, 1, 3);
		assertEquals(3, game.gameboard[1][1].getValue());
		game.resetCell(1, 1);
		assertEquals(0, game.gameboard[1][1].getValue());
	}

	@Test
	public void testSetCellValue() {
		MainGame game = new MainGame();
		game.resetBoard();
		assertTrue(game.setCellValue(1, 1, 5));
		assertEquals(5, game.gameboard[1][1].getValue());
		game.gameboard[1][1].chooseSet(true);
		assertFalse(game.setCellValue(1, 1, 3));
	}

	@Test
	public void testResetBoard() {
		MainGame game = new MainGame();
		game.setCellValue(1, 1, 3);
		game.resetBoard();
		assertEquals(0, game.gameboard[1][1].getValue());
	}

	@Test
	public void testEmptyBoard() {
		MainGame game = new MainGame();
		game.resetBoard();
		String expectedString = "0 0 0 | 0 0 0 | 0 0 0 \n" + "0 0 0 | 0 0 0 | 0 0 0 \n" + "0 0 0 | 0 0 0 | 0 0 0 \n"
				+ "------+-------+------\n" + "0 0 0 | 0 0 0 | 0 0 0 \n" + "0 0 0 | 0 0 0 | 0 0 0 \n"
				+ "0 0 0 | 0 0 0 | 0 0 0 \n" + "------+-------+------\n" + "0 0 0 | 0 0 0 | 0 0 0 \n"
				+ "0 0 0 | 0 0 0 | 0 0 0 \n" + "0 0 0 | 0 0 0 | 0 0 0 \n";
		assertEquals(expectedString, game.toString());
		assertEquals(expectedString, game.toString());
	}

	@Test
	public void testCheckWin() {
		MainGame game = new MainGame();
		game.resetBoard();
		assertFalse(game.checkWin()); // Assuming that resetting board means no cells are set and hence not a win.
	}

	@Test
	public void testSetCellGuess() {
		MainGame game = new MainGame();
		game.resetBoard();
		assertTrue(game.setCellGuess(1, 1, 5));
		assertTrue(game.gameboard[1][1].getGuess(5));
		// for guess not set
		assertFalse(game.gameboard[1][1].getGuess(8));
	}

	@Test
	public void testGuessToString() {
		MainGame game = new MainGame();
		game.resetBoard();
		game.setCellGuess(1, 1, 5);
		String expectedString = "      \n" + "  5   \n" + "      ";
		assertEquals(expectedString, game.guessToString(1, 1)); // Assuming that guessToString just returns the guess as
																// a string.
	}

	@Test
	public void testNewBoard() {
		MainGame game = new MainGame();
		String board1 = game.toString();
		game.newBoard();
		String board2 = game.toString();

		assertNotEquals(board1, board2);
	}

	@Test
	public void testIsSetAfterReset() {
		// create a new MainGame
		MainGame game = new MainGame();

		// reset the game
		game.resetBoard();

		// check that all cells are not set
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				assertFalse(game.isSet(i, j));
			}
		}
	}

	@Test
	public void testSetCellGuessReturnsFalse() {
		MainGame game = new MainGame();
		game.resetBoard();
		game.gameboard[1][1].chooseSet(true);
		assertFalse(game.setCellGuess(1, 1, 5));
	}

	@Test
	public void testCheckWinReturnsFalse() {
		MainGame game = new MainGame();
		game.resetBoard();
		assertFalse(game.checkWin());
	}

	@Test
	public void testCheckOne3x3ReturnsFalse() {
		MainGame game = new MainGame();
		game.resetBoard();
		game.setCellValue(0, 0, 1);
		game.setCellValue(0, 1, 1);
		assertFalse(game.checkOne3x3(0, 0));
	}

	@Test
	public void testCheck3X3ReturnsFalse() {
		MainGame game = new MainGame();
		game.resetBoard();
		game.setCellValue(0, 0, 1);
		game.setCellValue(0, 1, 1);
		assertFalse(game.check3X3());
	}

	@Test
	public void testSetDifficulty() {
		MainGame mainGame = new MainGame();

		mainGame.setDifficulty("Easy");
		assertEquals(30, mainGame.MISSING);

		mainGame.setDifficulty("Medium");
		assertEquals(40, mainGame.MISSING);

		mainGame.setDifficulty("Hard");
		assertEquals(50, mainGame.MISSING);
	}

	@Test
	public void testSetSolution() {
		MainGame game = new MainGame();
		game.setSolution();
		assertTrue(game.checkWin());
	}

}