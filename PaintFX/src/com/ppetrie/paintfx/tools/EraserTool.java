package com.ppetrie.paintfx.tools;

import com.ppetrie.paintfx.PaintController;

import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;

public class EraserTool extends Tool {
	
	/**
	 * Create an EraserTool
	 * @param button	the tool button corresponding to this tool
	 * @param menuItem	the menu item corresponding to this tool
	 */
	public EraserTool(Button button, CheckMenuItem menuItem) {
		super(button, menuItem);
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		
	}
	
}
