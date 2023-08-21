/**
 * BoardGUI.java
 * This class is responsible for showing the board on the MainGUI.
 * It also handles the mouse events for the board.
 * 
 * @author Hamad Ayaz, James Lee, Chris Brinkley
 * @since 2023-08-06
 */
package view;

import model.Sound;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.MainGame;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class BoardGUI extends BorderPane {

	private static final String MISSING_COLOR = "#292929";
	private static final String SELECTED_COLOR = "#1C1C1C";
	private static final String HIGHLIGHTED_COLOR = "#5B5B5B";

	private MainGUI mainGUI;
	private GridPane gridPane;
	private MainGame game;
	private int currentX = -1;
	private int currentY = -1;

	/**
	 * The constructor for the BoardGUI class.
	 * 
	 * @param mainGUI The MainGUI object that this BoardGUI is a part of
	 * @param game    The MainGame object that this BoardGUI is a part of
	 */
	public BoardGUI(MainGUI mainGUI, MainGame game) {
		this.mainGUI = mainGUI;
		this.game = game;
		gridPane = new GridPane();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String value = game.gameboard[i][j].getValue() == 0 ? " "
						: Integer.toString(game.gameboard[i][j].getValue());
				Label label = new Label(value);
				label.setAlignment(Pos.CENTER);
				label.setMinSize(60, 60);
				String textColor = "-fx-text-fill: white;"; // Set the text color to white
				if (game.isSet(i, j)) {
					label.setStyle(
							textColor + "-fx-background-color: " + SELECTED_COLOR + ";" + borderStringMaker(i, j));
				} else {
					label.setStyle(
							textColor + "-fx-background-color: " + MISSING_COLOR + ";" + borderStringMaker(i, j)); // Background
																													// color
																													// for
																													// empty
																													// cells
				}
				label.setOnMouseClicked(new MouseHandler(i, j));
				gridPane.add(label, i, j);

				label.setOnMouseEntered(e -> {
					javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(Duration.millis(150),
							label);
					st.setToX(1.09); // 10% increase in width
					st.setToY(1.09); // 10% increase in height
					st.play();
				});
				label.setOnMouseExited(e -> {
					javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(Duration.millis(150),
							label);
					st.setToX(1); // Return to original width
					st.setToY(1); // Return to original height
					st.play();
				});

			}
		}

		gridPane.setPadding(new Insets(10));
		setCenter(gridPane);
	}

	/**
	 * This method creates the border string for the cell at the given coordinates.
	 * 
	 * @param i The x coordinate of the cell
	 * @param j The y coordinate of the cell
	 */
	private String borderStringMaker(int i, int j) {
		StringBuilder returnString = new StringBuilder(
				"-fx-border-color: black; -fx-border-style: solid; -fx-border-width: ");
		returnString.append(j == 0 || j == 3 || j == 6 ? "3 " : "1 ").append(i == 2 || i == 5 || i == 8 ? "3 " : "1 ")
				.append(j == 8 || j == 2 || j == 5 ? "3 " : "1 ").append(i == 0 || i == 3 || i == 6 ? "3 " : "1 ");
		return returnString.toString();
	}

	/**
	 * This class handles the mouse events for the board. It mainly changes the
	 * color of the cell that is clicked on. It also updates the currentX and
	 * currentY variables so that the board knows which cell is currently selected.
	 */
	private class MouseHandler implements EventHandler<MouseEvent> {

		private int x;
		private int y;

		/**
		 * The constructor for the MouseHandler class.
		 * 
		 * @param x The x coordinate of the lebel
		 * @param y The y coordinate of the label
		 */
		public MouseHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * This method handles the mouse events for the board. It mainly changes the
		 * color of the cell that is clicked on.
		 * 
		 * @param event The mouse event to be handled
		 */
		@Override
		public void handle(MouseEvent event) {
			if (game.isSet(x, y)) {
				return;
			}

			String textColor = "-fx-text-fill: white;"; // Set the text color to white

			if (currentX != -1 && currentY != -1) {
				String previousCellColor = game.isSet(currentX, currentY) ? SELECTED_COLOR : MISSING_COLOR;
				getGridLabel(currentX, currentY).setStyle(textColor + "-fx-background-color: " + previousCellColor + ";"
						+ borderStringMaker(currentX, currentY));
			}

			getGridLabel(x, y)
					.setStyle(textColor + "-fx-background-color: " + HIGHLIGHTED_COLOR + ";" + borderStringMaker(x, y));

			currentX = x;
			currentY = y;
			mainGUI.setSelectedCell(x, y);
		}
	}

	/**
	 * This method returns the label at the given coordinates.
	 */
	private Label getGridLabel(int x, int y) {
		return (Label) gridPane.getChildren().get(x * 9 + y);
	}

	/**
	 * This method updates one cell on the board. It also checks to see if the game
	 * has been won.
	 * 
	 * @param Xcoord The x coordinate of the cell to be updated
	 * @param Ycoord The y coordinate of the cell to be updated
	 */
	public void updateBoard(int Xcoord, int Ycoord) {
		String textColor = "-fx-text-fill: white;"; // Set the text color to white

		if (currentX != -1 && currentY != -1) {
			if (mainGUI.getMode()) {
				int answer = game.getValue(Xcoord, Ycoord);
				if (answer == 0) {
					getGridLabel(currentX, currentY).setText("");
				} else {
					getGridLabel(currentX, currentY).setText(String.valueOf(answer));
					getGridLabel(currentX, currentY).setStyle(textColor + "-fx-background-color: " + HIGHLIGHTED_COLOR
							+ ";" + borderStringMaker(currentX, currentY));

				}
				if (game.checkWin()) {
					winEffect();
				}
			} else {
				String guessString = game.guessToString(currentX, currentY);
				getGridLabel(currentX, currentY).setText(guessString);
				if (game.getValue(currentX, currentY) == 0) {
					getGridLabel(currentX, currentY).setStyle(textColor + "-fx-background-color: " + HIGHLIGHTED_COLOR
							+ ";" + borderStringMaker(currentX, currentY));
				} else {
					getGridLabel(currentX, currentY).setStyle(textColor + "-fx-background-color: " + HIGHLIGHTED_COLOR
							+ ";" + borderStringMaker(currentX, currentY));

				}
			}
		}
	}

	/**
	 * Updates the board to match the gameboard
	 * 
	 * @param game The game to update the board to
	 */
	public void updateBoard(MainGame game) {
		String textColor = "-fx-text-fill: white;"; // Set the text color to white
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int cellValue = game.gameboard[i][j].getValue();
				String value = cellValue == 0 ? "" : String.valueOf(cellValue);
				Label label = getGridLabel(i, j);
				label.setText(value);

				if (game.isSet(i, j)) {
					label.setStyle(
							textColor + "-fx-background-color: " + SELECTED_COLOR + ";" + borderStringMaker(i, j));
				} else {
					label.setStyle(
							textColor + "-fx-background-color: " + MISSING_COLOR + ";" + borderStringMaker(i, j)); // Background
																													// color
																													// for
																													// empty
																													// cells
				}

			}
		}
	}

	/**
	 * Animates the board, used when the player wins
	 */
	private void animateWin() {
		// Step 1: Call rotation effect once for each label
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Label label = getGridLabel(i, j);
				rotateEffect(label);
			}
		}

		// Step 2: Update color with Timeline
		Timeline timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), e -> {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					Label label = getGridLabel(i, j);
					double random = Math.random();
					String color;
					if (random < 0.333) {
						color = "#292929";
					} else if (random < 0.666) {
						color = "#5B5B5B";
					} else {
						color = "darkorange";
					}
					label.setStyle(
							"-fx-text-fill: white; -fx-background-color: " + color + ";" + borderStringMaker(i, j));
				}
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(13); // Set the number of cycles
		timeline.setOnFinished(e -> updateBoard(game)); // Reset the board to its original state after the animation
		timeline.play();
	}

	/**
	 * Plays the sound, when the player wins.
	 */
	private void winSound() {
		new Thread(() -> {
			try {
				String cwd = System.getProperty("user.dir");
				String filename = cwd + "/src/sound/VictorySoundEffect.wav";
				Sound player = new Sound(filename);
				player.play();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
				System.out.println("Couldn't play victory sound");
				e.printStackTrace();
			}
		}).start();

	}

	/**
	 * Plays win effects, when the player wins.
	 */
	void winEffect() {
		animateWin();
		winSound();
	}

	/**
	 * Plays rotate effect, when the player wins.
	 */
	private void rotateEffect(Label label) {
		javafx.animation.RotateTransition rt = new javafx.animation.RotateTransition(Duration.millis(5000), label);
		rt.setByAngle(360); // Rotate by 360 degrees
		rt.setCycleCount(1); // Rotate once
		rt.setOnFinished(e -> label.setRotate(0)); // Reset the rotation after the animation completes
		rt.play();
	}
}
