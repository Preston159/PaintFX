package com.ppetrie.paintfx.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ShapeDialog {
	
	/**
	 * The ButtonTypes which will be shown when the user selects a shape
	 */
	public static final ButtonType RECTANGLE = new ButtonType("Rectangle"),
	SQUARE = new ButtonType("Square"),
	OVAL = new ButtonType("Oval"),
	CIRCLE = new ButtonType("Circle"),
	STAR = new ButtonType("Star"),
	TUBE = new ButtonType("Tube"),
	NGON = new ButtonType("N-Gon");
	
	private Alert alert;
	
	/**
	 * Create a ShapeDialog
	 */
	public ShapeDialog() {
		alert = new Alert(AlertType.NONE, "Please select the shape type you would like to use.",
				RECTANGLE, SQUARE, OVAL, CIRCLE, STAR, TUBE, NGON);
	}
	
	/**
	 * Prompts the user for the type of shape they wish to use
	 * @return	the ButtonType corresponding to the shape chosen
	 */
	public ButtonType showAndWait() {
		alert.showAndWait();
		return alert.getResult();
	}
	
}
