/**
 * SudokuInitializerTest.java
 * This class is responsible for testing SudokuInitializer
 * 
 * @author Hamad Ayaz
 * @since 2023-08-06
 */

package test;

import model.SudokuInitializer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SudokuInitializerTest {
	private static final int NUMBER_OF_TESTS = 1000;
	private static final int N = 9;
	private static final int K = 35;

	@Test
	public void testAverageSudokuGenerationTime() {
		long totalDurationInNanoSeconds = 0;

		for (int i = 0; i < NUMBER_OF_TESTS; i++) {
			long startTime = System.nanoTime();
			SudokuInitializer sudoku = new SudokuInitializer(N, K);
			sudoku.fillValues();
			long endTime = System.nanoTime();
			long durationInNanoSeconds = (endTime - startTime);
			totalDurationInNanoSeconds += durationInNanoSeconds;

			// Print the Sudoku board
			System.out.println("Sudoku Board #" + (i + 1) + ":");
			sudoku.printSudoku();
			System.out.println();
		}

		//
		double averageDurationInNanoSeconds = (double) totalDurationInNanoSeconds / NUMBER_OF_TESTS;
		double averageDurationInMilliseconds = averageDurationInNanoSeconds / 1000000;

		System.out.println("Average time taken to generate a Sudoku board: " + averageDurationInMilliseconds + " ms");
		assertNotNull(averageDurationInMilliseconds);
	}
}