package me.ppetrie.paint.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ShapeDialog {
	
	public static final ButtonType RECTANGLE = new ButtonType("Rectangle");
	public static final ButtonType SQUARE = new ButtonType("Square");
	public static final ButtonType OVAL = new ButtonType("Oval");
	public static final ButtonType CIRCLE = new ButtonType("Circle");
	public static final ButtonType STAR = new ButtonType("Star");
	public static final ButtonType TUBE = new ButtonType("Tube");
	public static final ButtonType NGON = new ButtonType("N-Gon");
	
	private Alert alert;
	
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
