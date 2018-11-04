package com.ppetrie.paintfx.tools;

import com.ppetrie.paintfx.PaintController;

import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;

public abstract class Tool {
	
	/**
	 * The button corresponding to this tool
	 */
	protected Button button;
	/**
	 * The menu item corresponding to this tool
	 */
	protected CheckMenuItem menuItem;
	
	public Tool(Button button, CheckMenuItem menuItem) {
		this.button = button;
		this.menuItem = menuItem;
	}
	
	/**
	 * Checks whether the tool button or menu item pressed matches this tool
	 * @param target	the tool button or menu item pressed
	 * @return			true if the target matches this tool, false otherwise
	 */
	public boolean matches(EventTarget target) {
		if(target == null) {
			return false;
		}
		return (target.equals(button) || target.equals(menuItem));
	}
	
	/**
	 * @return	the menu item belonging to this tool
	 */
	public CheckMenuItem getMenuItem() {
		return this.menuItem;
	}
	
	/**
	 * @return	the button belonging to this tool
	 */
	public Button getButton() {
		return this.button;
	}
	
	/**
	 * Handles canvas mouse events while this tool is selected
	 * @param e				the mouse event
	 * @param type			the type of the mouse event
	 * @param controller	the program's controller
	 */
	public abstract void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller);

}
