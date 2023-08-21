/**
 * SudokuSolverTest.java
 * This class is responsible for testing the Sudoku Solver
 * 
 * @author James Lee
 * @since 2023-08-06
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.SudokuSolver;

class SudokuSolverTest {
	private static final int N = 9;
	private SudokuSolver testSolver = new SudokuSolver();

	private int[][] board1 = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
			{ 0, 9, 8, 0, 0, 0, 0, 6, 0 }, { 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
			{ 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
	private int[][] solution1 = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
			{ 1, 9, 8, 3, 4, 2, 5, 6, 7 }, { 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
			{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

	private int[][] board2 = { { 5, 3, 4, 6, 7, 0, 9, 1, 0 }, { 6, 0, 2, 0, 9, 5, 3, 0, 8 },
			{ 1, 9, 0, 3, 0, 0, 5, 6, 7 }, { 8, 5, 9, 0, 6, 1, 4, 0, 0 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 0, 3, 9, 2, 4, 8, 5, 6 }, { 9, 0, 1, 5, 3, 7, 2, 0, 4 }, { 2, 0, 7, 0, 1, 9, 6, 3, 5 },
			{ 3, 4, 5, 0, 8, 0, 1, 7, 9 } };
	private int[][] solution2 = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
			{ 1, 9, 8, 3, 4, 2, 5, 6, 7 }, { 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
			{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

	private int[][] board3 = { { 0, 0, 0, 2, 6, 0, 7, 0, 1 }, { 6, 8, 0, 0, 7, 0, 0, 9, 0 },
			{ 1, 9, 0, 0, 0, 4, 5, 0, 0 }, { 8, 2, 0, 1, 0, 0, 0, 4, 0 }, { 0, 0, 4, 6, 0, 2, 9, 0, 0 },
			{ 0, 5, 0, 0, 0, 3, 0, 2, 8 }, { 0, 0, 9, 3, 0, 0, 0, 7, 4 }, { 0, 4, 0, 0, 5, 0, 0, 3, 6 },
			{ 7, 0, 3, 0, 1, 8, 0, 0, 0 } };
	private int[][] solution3 = { { 4, 3, 5, 2, 6, 9, 7, 8, 1 }, { 6, 8, 2, 5, 7, 1, 4, 9, 3 },
			{ 1, 9, 7, 8, 3, 4, 5, 6, 2 }, { 8, 2, 6, 1, 9, 5, 3, 4, 7 }, { 3, 7, 4, 6, 8, 2, 9, 1, 5 },
			{ 9, 5, 1, 7, 4, 3, 6, 2, 8 }, { 5, 1, 9, 3, 2, 6, 8, 7, 4 }, { 2, 4, 8, 9, 5, 7, 1, 3, 6 },
			{ 7, 6, 3, 4, 1, 8, 2, 5, 9 } };

	private boolean checkIf2DArrayEqual(int[][] arr1, int[][] arr2) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr1[i][j] != arr2[i][j])
					return false;
			}
		}
		return true;
	}

	@Test
	void testSolve() {
		testSolver.solve(board1);
		System.out.println("Board 1 Solution:");
		testSolver.printSolution();
		System.out.println();
		assertTrue(checkIf2DArrayEqual(testSolver.getSolution(), solution1));

		testSolver.solve(board2);
		System.out.println("Board 2 Solution:");
		testSolver.printSolution();
		System.out.println();
		assertTrue(checkIf2DArrayEqual(testSolver.getSolution(), solution2));

		testSolver.solve(board3);
		System.out.println("Board 3 Solution:");
		testSolver.printSolution();
		System.out.println();
		assertTrue(checkIf2DArrayEqual(testSolver.getSolution(), solution3));
	}
}
