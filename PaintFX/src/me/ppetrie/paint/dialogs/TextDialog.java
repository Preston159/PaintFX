package me.ppetrie.paint.dialogs;

import javafx.event.Event;
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
import me.ppetrie.paint.PaintController;

public class TextDialog {
	
	private static final int DEFAULT_FONT_SIZE = 12;
	
	private PaintController controller;
	private FontDialog fontDialog;
	
	private Dialog<String> textDialog;
	
	public TextDialog(PaintController controller) {
		this.controller = controller;
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
