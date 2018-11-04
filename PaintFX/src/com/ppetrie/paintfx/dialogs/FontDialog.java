package com.ppetrie.paintfx.dialogs;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class FontDialog {
	
	/**
	 * The number by which to multiply the input font size
	 */
	private static final int FONT_MULTIPLIER = 5;
	/**
	 * The default font size
	 */
	private static final int DEFAULT_FONT_SIZE = 12 * FONT_MULTIPLIER;
	/**
	 * The list of fonts which should be available in the dropdown menu
	 */
	private static final ArrayList<String> fonts = new ArrayList<String>();
	static {
		fonts.add("Calibri");
		fonts.add("Times New Roman");
	}
	
	private Dialog<Pair<String, Integer>> textDialog;
	
	/**
	 * Create a FontDialog
	 */
	public FontDialog() {
		textDialog = new Dialog<>();
		textDialog.setTitle("Font selection");
		textDialog.setHeaderText("Please enter your choice of font");
		textDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		ComboBox<String> fontName = new ComboBox<>();
		fontName.getItems().addAll(fonts);
		fontName.getSelectionModel().selectFirst();
		TextField fontSize = new TextField("12");
		fontSize.setPromptText("Font size");
		gridPane.add(new Label("Text:"), 0, 0);
		gridPane.add(fontName, 1, 0);
		gridPane.add(new Label("Font size:"), 0, 1);
		gridPane.add(fontSize, 1, 1);
		textDialog.getDialogPane().setContent(gridPane);
		textDialog.setResultConverter(dialogButton -> {
			try {
				return new Pair<>(fontName.getValue(), Integer.valueOf(fontSize.getText()) * FONT_MULTIPLIER);
			} catch(NumberFormatException nfe) {
				return new Pair<>(fontName.getValue(), DEFAULT_FONT_SIZE);
			}
		});
	}
	
	/**
	 * Prompts the user for a choice of font
	 * @return	a Pair where the first object is the name of the font, and the second object is the font size
	 */
	public Pair<String, Integer> showAndWait() {
		return textDialog.showAndWait().get();
	}
	
}
