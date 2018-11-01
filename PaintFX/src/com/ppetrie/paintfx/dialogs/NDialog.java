package com.ppetrie.paintfx.dialogs;

import com.ppetrie.paintfx.PaintController;
import com.ppetrie.paintfx.shapes.NGon;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NDialog {
	
	private static final int DEFAULT_FONT_SIZE = 12;
	
	private PaintController controller;
	
	private Dialog<Integer> textDialog;
	
	public NDialog(PaintController controller) {
		this.controller = controller;
		textDialog = new Dialog<>();
		textDialog.setTitle("NGon");
		textDialog.setHeaderText("Please enter the number of sides");
		textDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		TextField text = new TextField();
		text.setPromptText("N");
		gridPane.add(new Label("N:"), 0, 0);
		gridPane.add(text, 1, 0);
		textDialog.getDialogPane().setContent(gridPane);
		textDialog.setResultConverter(dialogButton -> {
			try {
				return Integer.valueOf(text.getText());
			} catch(NumberFormatException nfe) {
				return 5;
			}
		});
	}
	
	/**
	 * Prompts the user for the number of sides to use for the NGon shape and sets the value
	 */
	public void showAndWait() {
		NGon.setN(textDialog.showAndWait().get());
	}
	
}
