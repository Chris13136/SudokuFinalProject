/**
 * MainGame.java
* This class represents the main game logic for Sudoku.
*
* @author Hamad Ayaz, James Lee, Chris Brinkley, May Niu

* @since 2023-08-06
*/

package model;

import java.util.HashSet;
import java.util.Set;

public class MainGame {
	private static final int EASY = 30;
	private static final int MEDIUM = 40;
	private static final int HARD = 50;

	public int MISSING = EASY; // Change from final to non-final variable
	public Cell[][] gameboard;
	public int[][] solution;
	public SudokuInitializer initializer;
	public SudokuSolver solver;

	/**
	 * The SudokuInitializer used to initialize the gameboard.
	 */
	public MainGame() {
		solver = new SudokuSolver();
		newBoard();
	}

	/**
	 * Makes a board from the SudokuInitializer and fills it with cell objects along
	 * with their values
	 */
	private void initializeBoard() {
		gameboard = new Cell[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				gameboard[i][j] = new Cell(initializer.mat[i][j]);
			}
		}
	}

	/**
	 * Checks the gameboard for errors. An error occurs if a row, column, or 3x3
	 * block contains duplicate numbers.
	 *
	 * @return True if the gameboard is error-free, false otherwise.
	 */
	public boolean errorCheck() {
		return checkRows() && checkCols() && check3X3();
	}

	/**
	 * Checks each row in the gameboard for duplicate numbers.
	 *
	 * @return True if all rows are error-free, false otherwise.
	 */
	private boolean checkRows() {
		for (int row = 0; row < 9; row++) {
			Set<Integer> seen = new HashSet<>();
			for (int col = 0; col < 9; col++) {
				int value = gameboard[row][col].getValue();
				if (value != 0 && !seen.add(value)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks each column in the gameboard for duplicate numbers.
	 *
	 * @return True if all columns are error-free, false otherwise.
	 */
	private boolean checkCols() {
		for (int col = 0; col < 9; col++) {
			Set<Integer> seen = new HashSet<>();
			for (int row = 0; row < 9; row++) {
				int value = gameboard[row][col].getValue();
				if (value != 0 && !seen.add(value)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks each 3x3 block in the gameboard for duplicate numbers.
	 *
	 * @return True if all 3x3 blocks are error-free, false otherwise.
	 */
	public boolean check3X3() {
		for (int row = 0; row < 9; row += 3) {
			for (int col = 0; col < 9; col += 3) {
				if (!checkOne3x3(row, col)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks a single 3x3 block in the gameboard for duplicate numbers.
	 *
	 * @param rowStart The row index of the top left corner of the 3x3 block.
	 * @param colStart The column index of the top left corner of the 3x3 block.
	 * @return True if the 3x3 block is error-free, false otherwise.
	 */
	public boolean checkOne3x3(int rowStart, int colStart) {
		// Create a set to store the numbers that have already been seen in this grid.
		Set<Integer> seen = new HashSet<>();

		// Iterate over each row in the grid.
		for (int row = rowStart; row < rowStart + 3; row++) {
			for (int col = colStart; col < colStart + 3; col++) {
				int cellValue = gameboard[row][col].getValue();
				// 0 is default and we want to be able to use this in more than just a end game
				// function
				if (cellValue != 0 && !seen.add(cellValue)) {
					return false;
				}
			}
		}
		// If we reach this point, all numbers 1-9 must be present in the grid.
		return true;
	}

	/**
	 * This creates a text version of the board biased off of the current board
	 * state. If no guesses are put in it will show 0 in that spot TODO: add
	 * printing guesses either function or add it to this
	 */
	@Override
	public String toString() {
		StringBuilder printString = new StringBuilder(170); // allocate necessary capacity upfront
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				printString.append(gameboard[i][j].getValue()).append(" ");
				if (j == 2 || j == 5) {
					printString.append("| ");
				}
			}
			printString.append("\n");
			if (i == 2 || i == 5) {
				printString.append("------+-------+------\n");
			}
		}
		return printString.toString();
	}

	/**
	 * This method checks if the board is full and correct
	 * 
	 * @return false if there is open spaces
	 */
	public boolean checkWin() {
		if (!errorCheck()) {
			return false; // Return false if there's an error on the board
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (gameboard[i][j].getValue() == 0) {
					return false; // Return false if there's an empty cell
				}
			}
		}

		return true; // Return true if there's no error and no empty cells
	}

	/**
	 * This method sets a value in a specfic cell There is no check whether or not
	 * this value is correct or not
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public boolean setCellValue(int x, int y, int value) {
		if (!gameboard[x][y].isSet()) {
			gameboard[x][y].setValue(value);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method sets a Guess in a cell There is no check whether or not this
	 * guess is correct or not
	 * 
	 * @param x
	 * @param y
	 * @param guess
	 */
	public boolean setCellGuess(int x, int y, int guess) {
		if (!gameboard[x][y].isSet()) {
			gameboard[x][y].setGuess(guess);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method resets the board to zero
	 */
	public void resetBoard() {
		for (Cell[] row : gameboard) {
			for (Cell cell : row) {
				cell.resetCell();
			}
		}
	}

	/**
	 * This method resets a certain cell
	 * 
	 * @param x
	 * @param y
	 */
	public void resetCell(int x, int y) {
		gameboard[x][y].resetCell();
	}

	/**
	 * This method returns a string of guesses for each cell
	 * 
	 * @param x
	 * @param y
	 */
	public String guessToString(int x, int y) {
		return gameboard[x][y].guessToString();
	}

	/**
	 * isSet = true: The cell has been initialized with a value and is not editable.
	 * isSet = false: The cell is editable.
	 * 
	 * @param x
	 * @param yyy
	 */
	public boolean isSet(int x, int y) {
		return gameboard[x][y].isSet();
	}

	/**
	 * This method resets the board to zero and populates it with new values.
	 */
	public void newBoard() {
		initializer = new SudokuInitializer(9, MISSING);
		initializer.fillValues(); // Generate a new Sudoku puzzle

		solver.solve(initializer.mat);
		solution = solver.getSolution();

		initializeBoard(); // Initialize the game board with the new puzzle
	}

	public void setSolution() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				gameboard[i][j].setValue(solution[i][j]);
			}
		}
	}

	/**
	 * This method returns value from cell.
	 */
	public int getValue(int x, int y) {
		return gameboard[x][y].getValue();
	}

	// public void setBoardAsSolution() {
	// SudokuSolver solver = new SudokuSolver();
	// solver.solve(null)
	// }

	/**
	 * This method resets the guesses.
	 */
	public void resetGuess(int x, int y) {
		gameboard[x][y].resetGuess();
	}

	/**
	 * This method sets MISSING to the correct number based on the difficulty.
	 * 
	 * @param difficulty The difficulty of the game.
	 */
	public void setDifficulty(String difficulty) {
		switch (difficulty) {
		case "Easy":
			MISSING = EASY;
			break;
		case "Medium":
			MISSING = MEDIUM;
			break;
		case "Hard":
			MISSING = HARD;
			break;
		case "Test":
			MISSING = 1;
			break;
		}
		newBoard();
	}
}
