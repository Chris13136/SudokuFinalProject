/**
 * SudokuSolver.java
 * This class is responsible for solving the board.
 * 
 * @author James Lee
 * @since 2023-08-06
 */

package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import javafx.util.Pair;

public class SudokuSolver {
	private static int N = 9;
	private static int sqrtN = 3;

	private int numSolutions = 0;
	private int[][] board;
	private int[][] solution;

	private Queue<Pair<Integer, Integer>> pqCellCandidatesCount;
	private Map<Integer, Set<Integer>> rowToCandidates;
	private Map<Integer, Set<Integer>> colToCandidates;
	private Map<Integer, Set<Integer>> boxToCandidates;

	/**
	 * Creates a new SudokuSolver instance.
	 */
	public SudokuSolver() {
	}

	/**
	 * Checks if it is safe to assign a number to a given cell in the Sudoku board.
	 *
	 * @param row The row of the cell.
	 * @param col The column of the cell.
	 * @param num The number to be assigned.
	 * @return True if it is safe to assign the number, false otherwise.
	 */
	private boolean checkIfSafe(int row, int col, int num) {
		return rowToCandidates.get(row).contains(num) && colToCandidates.get(col).contains(num)
				&& boxToCandidates.get(sqrtN * (row / sqrtN) + col / sqrtN).contains(num);
	}

	/**
	 * Initializes the candidate sets for each row, column, and box on the Sudoku
	 * board.
	 */
	private void setCandidates() {
		rowToCandidates = new HashMap<>();
		colToCandidates = new HashMap<>();
		boxToCandidates = new HashMap<>();

		for (int i = 0; i < N; i++) {
			rowToCandidates.put(i, new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
			colToCandidates.put(i, new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
			boxToCandidates.put(i, new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
		}

		for (int i = 0; i < N * N; i++) {
			int r = i / N;
			int c = i % N;

			if (board[r][c] == 0)
				continue;

			rowToCandidates.get(r).remove(board[r][c]);
			colToCandidates.get(c).remove(board[r][c]);
			boxToCandidates.get(sqrtN * (r / sqrtN) + c / sqrtN).remove(board[r][c]);
		}
	}

	/**
	 * Initializes a priority queue with cells and their corresponding candidate
	 * count.
	 */
	private void setCandidatesCount() {
		pqCellCandidatesCount = new PriorityQueue<>((a, b) -> Integer.compare(a.getValue(), b.getValue()));

		for (int i = 0; i < N * N; i++) {
			int r = i / N;
			int c = i % N;

			if (board[r][c] != 0)
				continue;

			int tempCount = 0;

			for (int num = 1; num <= N; num++) {
				if (rowToCandidates.get(r).contains(num) && colToCandidates.get(c).contains(num)
						&& boxToCandidates.get(sqrtN * (r / sqrtN) + c / sqrtN).contains(num))
					tempCount++;
			}

			pqCellCandidatesCount.offer(new Pair<>(i, tempCount));
		}
	}

	/**
	 * Sets the given cell on the Sudoku board to the specified number.
	 * 
	 * @param r   The row of the cell.
	 * @param c   The column of the cell.
	 * @param num The number to be assigned.
	 */
	private void setCellToNum(int r, int c, int num) {
		rowToCandidates.get(r).remove(num);
		colToCandidates.get(c).remove(num);
		boxToCandidates.get(sqrtN * (r / sqrtN) + c / sqrtN).remove(num);
		board[r][c] = num;
	}

	/**
	 * Unsets the given cell on the Sudoku board, restoring the cell to an empty
	 * state.
	 * 
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 */
	private void unsetCell(int r, int c) {
		rowToCandidates.get(r).add(board[r][c]);
		colToCandidates.get(c).add(board[r][c]);
		boxToCandidates.get(sqrtN * (r / sqrtN) + c / sqrtN).add(board[r][c]);
		board[r][c] = 0;
	}

	/**
	 * Copies the current Sudoku board as a solution.
	 */
	private void copySolution() {
		solution = new int[9][9];
		for (int i = 0; i < N; i++)
			solution[i] = board[i].clone();
	}

	/**
	 * Solves the Sudoku puzzle using a backtracking algorithm and counts the number
	 * of solutions.
	 */
	private void sudokuSolver() {
		if (pqCellCandidatesCount.isEmpty()) {
			copySolution();
			numSolutions++;
			return;
		}

		Pair<Integer, Integer> curCell = pqCellCandidatesCount.poll();
		int r = curCell.getKey() / N;
		int c = curCell.getKey() % N;

		for (int num = 1; num <= N; num++) {
			if (!checkIfSafe(r, c, num))
				continue;
			setCellToNum(r, c, num);
			sudokuSolver();
			unsetCell(r, c);
		}

		pqCellCandidatesCount.offer(curCell);

		return;
	}

	/**
	 * Solves the given Sudoku puzzle and returns the number of solutions found.
	 * 
	 * @param mat The Sudoku board to be solved.
	 * @return The number of solutions found.
	 */
	public int solve(int[][] mat) {
		board = mat; // clone not needed since mat reference will not be changed
		numSolutions = 0; // reset counter

		setCandidates();
		setCandidatesCount();

		sudokuSolver();

		return numSolutions;
	}

	/**
	 * Returns a cloned copy of the current solution Sudoku board.
	 * 
	 * @return The Sudoku solution board.
	 */
	public int[][] getSolution() {
		return solution.clone();
	}

	public void printSolution() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(solution[i][j] + " ");
			}
			System.out.println();
		}
	}
}
