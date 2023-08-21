/**
 * MainGUI.java
 * This is the main GUI class that creates the main GUI for the Sudoku game.
 * It contains the main method that launches the GUI.
 * It also contains the methods that are responsible for updating the backend
 * when the user interacts with the GUI.
 * 
 * @author Hamad Ayaz, James Lee, Chris Brinkley
 * @since 2023-08-06
 */
package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MainGame;

public class MainGUI extends Application {
	private BoardGUI boardGUI;
	private KeypadGUI keypadGUI;
	private MainGame game;
	private int Xcoord;
	private int Ycoord;
	private int keyVal;
	private boolean isAnswer;
	private MenuBar menuBar;

	@Override
	public void start(Stage primaryStage) {
		game = new MainGame();
		boardGUI = new BoardGUI(this, game);
		keypadGUI = new KeypadGUI(this);

		VBox mainContainer = setupMainContainer();

		Scene scene = new Scene(mainContainer, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method is responsible for laying out the gui parts
	 * 
	 * @return mainContainer
	 */
	private VBox setupMainContainer() {
		VBox mainContainer = new VBox();
		mainContainer.setStyle("-fx-background-color: #4B4B4B;"); // Set background color
		menuBar = new MenuBar();
		menuBar.getMenus().add(createMenu());
		HBox bottomHalf = new HBox(boardGUI, keypadGUI);
		mainContainer.getChildren().addAll(menuBar, bottomHalf);
		return mainContainer;
	}

	/**
	 * setting the X/Ycoord
	 *
	 * @param row the row
	 * @param col the col
	 */
	public void setSelectedCell(int row, int col) {
		Xcoord = row;
		Ycoord = col;
	}

	/**
	 * This is how answers are updated in the backend
	 * 
	 * @param value the value of the keypad button pressed
	 */
	private void setAnswer(int value) {
		keyVal = value;
		if (game.setCellValue(Xcoord, Ycoord, keyVal)) {
			boardGUI.updateBoard(Xcoord, Ycoord);
		}
	}

	/**
	 * This is how guesses are updated in the backend
	 * 
	 * @param value the value of the keypad button pressed
	 */
	private void setGuess(int value) {
		if (value == 0) {
			game.resetGuess(Xcoord, Ycoord);
			boardGUI.updateBoard(Xcoord, Ycoord);
		} else if (game.setCellGuess(Xcoord, Ycoord, value)) {
			boardGUI.updateBoard(Xcoord, Ycoord);
		}
	}

	/**
	 * This method is called when the user interacts with the keypad
	 * 
	 * @param isAnswer    true if the user is entering an answer, false if the user
	 *                    is entering a guess
	 * @param keypadValue the value of the keypad button pressed
	 */
	public void updateBackend(boolean isAnswer, int keypadValue) {
		this.isAnswer = isAnswer;
		if (isAnswer) {
			setAnswer(keypadValue);
		} else {
			setGuess(keypadValue);
		}
	}

	/**
	 * This method returns the current mode of the keypad
	 */
	public boolean getMode() {
		return isAnswer;
	}

	/**
	 * This method is responsible for creating the menu bar
	 */
	private Menu createMenu() {
		Menu menu = new Menu("Options");

		// Create the difficulty submenu
		Menu difficultyMenu = new Menu("Difficulty");
		difficultyMenu.getItems().addAll(createMenuItem("Easy", () -> setDifficulty("Easy")),
				createMenuItem("Medium", () -> setDifficulty("Medium")),
				createMenuItem("Hard", () -> setDifficulty("Hard")), createMenuItem("Test", () -> setDifficulty("Test"))

		);

		// Add the difficulty submenu to the options menu
		menu.getItems().addAll(createMenuItem("New Game", this::handleNewGame), difficultyMenu, // Add the difficulty
																								// submenu here
				createMenuItem("Reveal Solution", () -> { // Add the solve submenu
					game.setSolution();
					boardGUI.updateBoard(game);
					boardGUI.winEffect();
				}));

		return menu;
	}

	/**
	 * This method is responsible for creating the menu items
	 * 
	 * @param name   the name of the menu item
	 * @param action the action to be performed when the menu item is clicked
	 */
	private MenuItem createMenuItem(String name, Runnable action) {
		MenuItem menuItem = new MenuItem(name);
		menuItem.setOnAction(event -> action.run());
		return menuItem;
	}

	/**
	 * This method is responsible for setting the difficulty of the game
	 * 
	 * @param difficulty the difficulty of the game
	 */
	private void setDifficulty(String difficulty) {
		game.setDifficulty(difficulty); // Change MISSING value according to difficulty and call newBoard
		boardGUI.updateBoard(game); // Update the board GUI to depict changes
	}

	/**
	 * This method is responsible for handling the new game menu item
	 */
	public void handleNewGame() {
		newGame();
	}

	/**
	 * This method is responsible for creating a new game
	 */
	private void newGame() {
		game.newBoard(); // Reset and populate the game board with new values
		boardGUI.updateBoard(game); // Update the board GUI to reflect the new game board
	}

	public static void main(String[] args) {
		launch(args);
	}

}
