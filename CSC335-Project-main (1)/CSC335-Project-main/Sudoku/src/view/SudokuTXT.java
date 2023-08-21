/**
 * SudokuTXT.java
 * This is a text-based version of the Sudoku game. It allows the user to play
 * the game by entering commands into the console.
 * 
 * The commands are as follows:
 * 		newgame - start a new game
 * 		guess - make a guess
 * 		answer - submit an answer
 * 		printboard - print the current board
 * 		help - display a help message
 * 		exit - exit the game
 * @author Hamad Ayaz
 * @since 2023-08-06
 */

package view;

import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.MainGame;
import model.Sound;

public class SudokuTXT {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		MainGame game = new MainGame();

		System.out.println("Welcome to Sudoku game. Type 'help' for a list of commands");

		while (true) {
			System.out.print("> ");
			String command = scanner.nextLine();

			switch (command) {
			case "newgame":
				System.out.println("A new game has been started!");
				game = new MainGame(); // Start a new game
				System.out.println(game);
				break;

			case "guess":
				System.out.println("Enter row, column and guess (1-9):");

				int row;
				if (scanner.hasNextInt()) {
					row = scanner.nextInt();
				} else {
					System.out.println("Invalid input. Please enter an integer.");
					scanner.next();
					continue;
				}

				int col;
				if (scanner.hasNextInt()) {
					col = scanner.nextInt();
				} else {
					System.out.println("Invalid input. Please enter an integer.");
					scanner.next();
					continue;
				}

				int guess;
				if (scanner.hasNextInt()) {
					guess = scanner.nextInt();
				} else {
					System.out.println("Invalid input. Please enter an integer.");
					scanner.next();
					continue;
				}

				scanner.nextLine(); // Consume newline

				if (row < 1 || row > 9 || col < 1 || col > 9 || guess < 1 || guess > 9) {
					System.out.println(
							"Invalid input! Please make sure your row, column, and guess values are between 1 and 9.");
				} else if (!game.setCellGuess(row - 1, col - 1, guess)) {
					System.out.println("The cell at (" + row + "," + col + ") was initialized and cannot be changed.");
				} else {
					System.out.println("Guess has been set!");
					System.out.println(game.guessToString(row - 1, col - 1));
				}

				if (game.checkWin()) {
					System.out.println(game);
					System.out.println("Congratulations! You've won the game.");
				}
				break;

			case "answer":
				System.out.println("Enter row, column and answer (0-9):");

				if (scanner.hasNextInt()) {
					row = scanner.nextInt();
				} else {
					System.out.println("Invalid input. Please enter an integer.");
					scanner.next();
					continue;
				}

				if (scanner.hasNextInt()) {
					col = scanner.nextInt();
				} else {
					System.out.println("Invalid input. Please enter an integer.");
					scanner.next();
					continue;
				}

				int answer;
				if (scanner.hasNextInt()) {
					answer = scanner.nextInt();
				} else {
					System.out.println("Invalid input. Please enter an integer.");
					scanner.next();
					continue;
				}

				scanner.nextLine(); // Consume newline

				if (row < 1 || row > 9 || col < 1 || col > 9 || answer < 0 || answer > 9) {
					System.out.println(
							"Invalid input! Please make sure your row, column, and answer values are between 0 and 9.");
				} else if (!game.setCellValue(row - 1, col - 1, answer)) {
					System.out.println("The cell at (" + row + "," + col + ") was initialized and cannot be changed.");
				} else {
					System.out.println(game);
					System.out.println("Answer has been set!");
				}

				if (game.checkWin()) {
					System.out.println(game);
					System.out.println("Congratulations! You've won the game.");

					try {
						String cwd = System.getProperty("user.dir");
						String filename = cwd + "/src/sound/VictorySoundEffect.wav";
						Sound player = new Sound(filename);
						player.play();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException
							| InterruptedException e) {
						System.out.println("Couldn't play victory sound");
						e.printStackTrace();
					}
				}
				break;

			case "printboard":
				System.out.println(game);
				break;

			case "help":
				System.out.println(
						"Commands:\n newgame - start a new game\n guess - make a guess\n answer - submit an answer\n printboard - print the current board\n help - display this help message\n exit - exit the game");
				break;

			case "exit":
				System.out.println("Exiting the game. Goodbye!");
				System.exit(0);

			default:
				System.out.println("Unrecognized command. Type 'help' for list of commands");
				break;
			}
		}
	}
}