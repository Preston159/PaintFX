package com.ppetrie.paintfx.tools;

import com.ppetrie.paintfx.Paint;
import com.ppetrie.paintfx.PaintController;

import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;

public class DropperTool extends Tool {
	
	/**
	 * The main class of the program
	 */
	private Paint paint;
	
	/**
	 * Create a DropperTool
	 * @param button	the tool button corresponding to this tool
	 * @param menuItem	the menu item corresponding to this tool
	 * @param paint		the main class of the program
	 */
	public DropperTool(Button button, CheckMenuItem menuItem, Paint paint) {
		super(button, menuItem);
		this.paint = paint;
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		if(type == MouseEvent.MOUSE_PRESSED) {
			PixelReader reader = paint.getImageHandler().getImage().getPixelReader();
			int x = (int) (e.getX());
			int y = (int) (e.getY());
			controller.getPrimaryColorPicker().setValue(reader.getColor(x, y));
		}
	}

}
