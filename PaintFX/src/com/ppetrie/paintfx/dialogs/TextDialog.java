package com.ppetrie.paintfx.dialogs;

import com.ppetrie.paintfx.PaintController;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Pair;

public class TextDialog {
	
	/**
	 * The FontDialog instance which will be accessed when the user attempts to change the font
	 */
	private FontDialog fontDialog;
	
	private Dialog<String> textDialog;
	
	/**
	 * Create a TextDialog
	 * @param controller	The PaintController this object will use to change the font and size
	 */
	public TextDialog(final PaintController controller) {
		fontDialog = new FontDialog();
		textDialog = new Dialog<>();
		textDialog.setTitle("Text tool");
		textDialog.setHeaderText("Please enter the text you'd like to use");
		textDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		TextField text = new TextField();
		text.setPromptText("Text");
		Button fontButton = new Button("Select font");
		fontButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Pair<String, Integer> result = fontDialog.showAndWait();
				Font font = new Font(result.getKey(), result.getValue());
				controller.getDrawCanvas().getGraphicsContext2D().setFont(font);
			}
			
		});
		gridPane.add(new Label("Text:"), 0, 0);
		gridPane.add(text, 1, 0);
		gridPane.add(fontButton, 0, 1);
		textDialog.getDialogPane().setContent(gridPane);
		textDialog.setResultConverter(dialogButton -> {
			return text.getText();
		});
	}
	
	/**
	 * Prompts the user for text input
	 * @return	the string input by the user
	 */
	public String showAndWait() {
		return textDialog.showAndWait().get();
	}
	
}
