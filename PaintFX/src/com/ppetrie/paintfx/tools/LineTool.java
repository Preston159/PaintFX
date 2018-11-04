package com.ppetrie.paintfx.tools;

import com.ppetrie.paintfx.PaintController;

import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;

public class LineTool extends Tool {
	
	/**
	 * The position of the mouse when it was pressed
	 */
	private double[] lastPos = new double[2];
	
	/**
	 * Create a LineTool
	 * @param button	the tool button corresponding to this tool
	 * @param menuItem	the menu item corresponding to this tool
	 * @param paint		the main class of the program
	 */
	public LineTool(Button button, CheckMenuItem menuItem) {
		super(button, menuItem);
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		if(type == MouseEvent.MOUSE_PRESSED) {
			lastPos[0] = e.getX();
			lastPos[1] = e.getY();
		} else if(type == MouseEvent.MOUSE_DRAGGED) {
			controller.getDrawer().clearDrawCanvas();
			controller.getDrawCanvas().getGraphicsContext2D().strokeLine(lastPos[0], lastPos[1], e.getX(), e.getY());
		} else if(type == MouseEvent.MOUSE_RELEASED) {
			controller.getDrawCanvas().getGraphicsContext2D().strokeLine(lastPos[0], lastPos[1], e.getX(), e.getY());
			controller.getDrawer().drawToLayer();
		}
	}
	
}
