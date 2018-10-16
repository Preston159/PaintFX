package me.ppetrie.paint.tools;

import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;
import me.ppetrie.paint.PaintController;

public class EraserTool extends Tool {
	
	public EraserTool(Button button, CheckMenuItem menuItem) {
		super(button, menuItem);
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		
	}
	
}
