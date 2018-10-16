package me.ppetrie.paint.tools;

import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import me.ppetrie.paint.Paint;
import me.ppetrie.paint.PaintController;

public class DropperTool extends Tool {
	
	private Paint paint;
	
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
