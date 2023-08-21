/**
 * Cell.java
 * This class represents a single square in a Sudoku puzzle.
 *
 * @author Hamad Ayaz, James Lee
 * @since 2023-08-06
 * Class: CSC 335
 */
package model;

public class Cell {
	private boolean isSet; // Status of cell which can be either editable or not editable.
	private int value; // The value of the cell (0-9)
	// 3x3 array to store guesses (true if guessed, false otherwise)
	private boolean[][] guesses = { { false, false, false }, 
								    { false, false, false }, 
							        { false, false, false } };
  
	/**
	 * Constructs a new Cell with a value of 0 and isSet = false.
	 */
	public Cell() {
	  this.value = 0;
	  this.isSet = false;
	}
  
	/**
	 * Constructs a new Cell with the specified value and isSet = true.
	 *
	 * @param value the value of the cell
	 */
	public Cell(int value) {
	  this.value = value;
		this.isSet = value != 0;
	}
  
	/**
	 * Returns whether or not the cell is set.
	 *
	 * @return true if the cell is set, false otherwise
	 */
	public boolean isSet() {
	  return this.isSet;
	}
  
	// FOR TESTING ONLY
	public void chooseSet(boolean value) {
		this.isSet = value;
	}

	// Method to set the value of the cell (1-9)
	public void setValue(int value) {
		if (!isSet) {
		this.value = value;
	  }
	}
  
	/**
	 * Removes the value from the cell.
	 *
	 * @param value the value to remove
	 */
	public void removeValue(int value) {
	  this.value = value;
	}
  
	/**
	 * Toggles a guess in the cell.
	 *
	 * @param guess the guess to toggle
	 */
	public void setGuess(int guess) {
	  int i = (guess - 1) / 3;
	  int j = (guess - 1) % 3;
	  this.guesses[i][j] = !this.guesses[i][j];
	}
  
	/**
	 * Removes a guess from the cell.
	 *
	 * @param guess the guess to remove
	 */
	public void removeGuess(int guess) {
	  this.guesses[(guess - 1) / 3][(guess - 1) % 3] = false;
	}
  
	/**
	 * Resets all guesses in the cell.
	 */
	public void resetGuess() {
	  for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
		  this.guesses[i][j] = false;
		}
	  }
	}
  
	/**
	 * Returns the value of the cell.
	 *
	 * @return the value of the cell
	 */
	public int getValue() {
	  return this.value;
	}
  
	/**
	 * Returns whether or not a guess exists in the cell.
	 *
	 * @param guess the guess to check for
	 * @return true if the guess exists, false otherwise
	 */
	public boolean getGuess(int guess) {
	  return this.guesses[(guess - 1) / 3][(guess - 1) % 3];
	}
  
	/**
	 * Resets the entire cell.
	 */
	public void resetCell() {
	  this.value = 0;
	  resetGuess();
	  this.isSet = false;
	}
  
	/**
	 * Converts the Cell object to a string representation.
	 *
	 * @return a string representation of the Cell object
	 */
	public String guessToString() {

	    String[] str = new String[3];

	    for (int i = 0; i < 3; i++) {
	        str[i] = "";
	        for (int j = 0; j < 3; j++) {
	            if (guesses[i][j]) {
	                str[i] += Integer.toString(3 * i + j + 1) + " "; // Added space after number
	            } else {
	                str[i] += "  "; // Two spaces to maintain alignment
	            }
	        }
	    }
	    return String.join("\n", str);
	}

}
