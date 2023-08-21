/**
 * KeypadGUI.java
 * This class is responsible for showing the keypad on the MainGUI.
 * It also handles the mouse events for the keypad.
 * 
 * @author Hamad Ayaz
 * @since 2023-08-06
 */
package view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KeypadGUI extends BorderPane {

	private MainGUI mainGUI;
	private Button[] buttons;
	private Button answerModeButton;
	private Button guessModeButton;
	private Button clearButton;
	private boolean isAnswer = true;
	private static final String TEXT_COLOR_WHITE = "-fx-text-fill: white;";

	/**
	 * Constructor for KeypadGUI
	 * 
	 * @param mainGUI
	 */
	public KeypadGUI(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		buttons = new Button[9];

		String numberButtonStyle = "-fx-background-color: #262626; -fx-text-fill: white; -fx-font-size: 18px;"
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
		String numberButtonHoverStyle = "-fx-background-color: #373737; -fx-text-fill: white;";
		String clearButtonStyle = "-fx-background-color: #262626; -fx-text-fill: white; -fx-font-size: 18px;";
		String clearButtonHoverStyle = "-fx-background-color: #373737; -fx-text-fill: white;"; // Modify this color as
																								// needed

		for (int i = 0; i < 9; i++) {
			buttons[i] = new Button(String.valueOf(i + 1));
			buttons[i].setMinSize(60, 60);
			buttons[i].setStyle(numberButtonStyle);
			buttons[i].setOnAction(event -> handleButtonAction(event));
			final Button button = buttons[i]; // to handle hover effect
			button.setOnMouseEntered(e -> button.setStyle(numberButtonHoverStyle));
			button.setOnMouseExited(e -> button.setStyle(numberButtonStyle));
		}

		clearButton = new Button("X");
		clearButton.setMinSize(200, 60);
		clearButton.setStyle(clearButtonStyle);
		clearButton.setOnAction(event -> handleClearButtonAction(event));

		clearButton.setOnMouseEntered(e -> clearButton.setStyle(clearButtonStyle + clearButtonHoverStyle));
		clearButton.setOnMouseExited(e -> clearButton.setStyle(clearButtonStyle));

		HBox clearButtonPane = new HBox(clearButton);
		clearButtonPane.setPadding(new Insets(10));

		answerModeButton = new Button("Answer Mode");
		answerModeButton.setMinSize(100, 60);
		answerModeButton.setStyle(TEXT_COLOR_WHITE + "-fx-background-color: #373737; -fx-font-size: 14px;");
		answerModeButton.setOnAction(event -> updateIsAnswer(true));

		guessModeButton = new Button("Guess Mode");
		guessModeButton.setMinSize(100, 60);
		guessModeButton.setStyle(TEXT_COLOR_WHITE + "-fx-background-color: #262626; -fx-font-size: 14px;");
		guessModeButton.setOnAction(event -> updateIsAnswer(false));

		GridPane numberGridPane = new GridPane();
		numberGridPane.setHgap(10);
		numberGridPane.setVgap(10);

		for (int i = 0; i < 9; i++) {
			numberGridPane.add(buttons[i], i % 3, i / 3);
		}

		HBox numberPane = new HBox(numberGridPane); // Wrapping numbers in its own HBox
		numberPane.setPadding(new Insets(10)); // Padding for numbers

		GridPane modeGridPane = new GridPane();
		modeGridPane.setHgap(0);
		modeGridPane.setVgap(0);
		modeGridPane.add(answerModeButton, 0, 0, 1, 1);
		modeGridPane.add(guessModeButton, 1, 0, 1, 1);

		HBox modePane = new HBox(modeGridPane); // Wrapping mode buttons in its own HBox
		modePane.setPadding(new Insets(10)); // Padding for mode buttons

		VBox mainLayout = new VBox();
		mainLayout.getChildren().addAll(modePane, numberPane, clearButtonPane); // Adding all in VBox

		setCenter(mainLayout);
	}

	/**
	 * Handles the button action by updating the backend with the number on the
	 * button
	 * 
	 * @param event
	 */
	private void handleButtonAction(ActionEvent event) {
		Button button = (Button) event.getSource();
		mainGUI.updateBackend(isAnswer, Integer.parseInt(button.getText()));
	}

	/**
	 * Handles the clear button action button by updating the backend with a 0
	 * 
	 * @param event
	 */
	private void handleClearButtonAction(ActionEvent event) {
		mainGUI.updateBackend(isAnswer, 0);
	}

	/**
	 * Updates the mode of the keypad
	 * 
	 * @param isAnswerMode, tells if it is a guess or answer button
	 */
	private void updateIsAnswer(boolean isAnswerMode) {
		isAnswer = isAnswerMode;
		if (isAnswerMode) {
			answerModeButton.setStyle(TEXT_COLOR_WHITE + "-fx-background-color: #373737; -fx-font-size: 14px;");
			guessModeButton.setStyle(TEXT_COLOR_WHITE + "-fx-background-color: #262626; -fx-font-size: 14px;");
		} else {
			guessModeButton.setStyle(TEXT_COLOR_WHITE + "-fx-background-color: #373737; -fx-font-size: 14px;");
			answerModeButton.setStyle(TEXT_COLOR_WHITE + "-fx-background-color: #262626; -fx-font-size: 14px;");
		}
	}

}
