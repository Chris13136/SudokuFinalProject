/**
 * SudokuInitializer.java
 * This class is used to initialize the Sudoku board. It uses a 
 * backtracking algorithm to generatea random Sudoku board.
 * 
 * @author Hamad Ayaz, James Lee
 * @since 2023-08-06 
 */
package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SudokuInitializer {
	public int[][] mat;
	int N; // number of columns/rows.
	int SRN; // square root of N
	int K; // No. Of missing digits

	/**
	 * Constructor for SudokuInitializer
	 */
	public SudokuInitializer(int N, int K) {
		this.N = N;
		this.K = K;

		// Compute square root of N
		Double SRNd = Math.sqrt(N);
		SRN = SRNd.intValue();

		mat = new int[N][N];
	}

	/**
	 * This method fills the Sudoku board with random numbers.
	 */
	public void fillValues() {
		// Fill the diagonal of SRN x SRN matrices
		fillDiagonal();

		// Fill remaining blocks
		fillRemaining(0, SRN);

		// Remove Randomly K digits to make game
		removeKDigits();
	}

	/**
	 * This method fills the diagonal of the Sudoku board with random numbers.
	 */
	void fillDiagonal() {

		for (int i = 0; i < N; i = i + SRN)

			// for diagonal box, start coordinates->i==j
			fillBox(i, i);
	}

	/**
	 * Returns false if given 3 x 3 block contains num.
	 * 
	 * @param rowStart, the starting row of the 3 x 3 block
	 * @param colStart, the starting column of the 3 x 3 block
	 * @param num,      the number to check for
	 */
	boolean unUsedInBox(int rowStart, int colStart, int num) {
		for (int i = 0; i < SRN; i++)
			for (int j = 0; j < SRN; j++)
				if (mat[rowStart + i][colStart + j] == num)
					return false;

		return true;
	}

	/**
	 * Fill a 3 x 3 matrix.
	 * 
	 * @param row, the starting row of the 3 x 3 block
	 * @param col, the starting column of the 3 x 3 block
	 */
	void fillBox(int row, int col) {
		int num;
		for (int i = 0; i < SRN; i++) {
			for (int j = 0; j < SRN; j++) {
				do {
					num = randomGenerator(N);
				} while (!unUsedInBox(row, col, num));

				mat[row + i][col + j] = num;
			}
		}
	}

	/**
	 * returns a random number from 0-8
	 * 
	 * @param num
	 */
	int randomGenerator(int num) {
		return (int) Math.floor((Math.random() * num + 1));
	}

	/**
	 * Check if safe to put in cell
	 * 
	 * @param i,   the row of the cell
	 * @param j,   the column of the cell
	 * @param num, the number to check for
	 */
	boolean CheckIfSafe(int i, int j, int num) {
		return (unUsedInRow(i, num) && unUsedInCol(j, num) && unUsedInBox(i - i % SRN, j - j % SRN, num));
	}

	/**
	 * Check in the row for existence
	 * 
	 * @param i,   the row of the cell
	 * @param num, the number to check for
	 */
	boolean unUsedInRow(int i, int num) {
		for (int j = 0; j < N; j++)
			if (mat[i][j] == num)
				return false;
		return true;
	}

	/**
	 * Check in the col for existence
	 * 
	 * @param j,   the row of the cell
	 * @param num, the number to check for
	 */
	boolean unUsedInCol(int j, int num) {
		for (int i = 0; i < N; i++)
			if (mat[i][j] == num)
				return false;
		return true;
	}

	/**
	 * A recursive function to fill remaining matrix
	 * 
	 * @param i, the row of the cell
	 * @param j, the column of the cell
	 */
	boolean fillRemaining(int i, int j) {
		// System.out.println(i+" "+j);
		if (j >= N && i < N - 1) {
			i = i + 1;
			j = 0;
		}
		if (i >= N && j >= N)
			return true;

		if (i < SRN) {
			if (j < SRN)
				j = SRN;
		} else if (i < N - SRN) {
			if (j == (int) (i / SRN) * SRN)
				j = j + SRN;
		} else {
			if (j == N - SRN) {
				i = i + 1;
				j = 0;
				if (i >= N)
					return true;
			}
		}

		for (int num = 1; num <= N; num++) {
			if (CheckIfSafe(i, j, num)) {
				mat[i][j] = num;
				if (fillRemaining(i, j + 1))
					return true;

				mat[i][j] = 0;
			}
		}
		return false;
	}

	public List<Integer> rngUniqueIntegers() {
		ArrayList<Integer> lst = new ArrayList<>();
		for (int i = 0; i < 81; i++)
			lst.add(i);
		Collections.shuffle(lst);
		return lst;
	}

	/**
	 * Remove the K no. of digits to complete game
	 */
	public void removeKDigits() {
		int count = K;
		SudokuSolver solver = new SudokuSolver();

		for (int n : rngUniqueIntegers()) {
			int r = n / 9;
			int c = n % 9;

			int temp = mat[r][c];
			mat[r][c] = 0;
			if (solver.solve(mat) != 1) {
				mat[r][c] = temp;
			} else {
				count--;
			}

			if (count == 0)
				break;
		}
	}

	/**
	 * Prints the Sudoku board to the console.
	 */
	public void printSudoku() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(mat[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
}