package com.ppetrie.paintfx.tools;

import com.ppetrie.paintfx.PaintController;
import com.ppetrie.paintfx.util.CanvasUtil;

import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;

public class PenTool extends Tool {
	
	private double[] lastPos = new double[2];
	
	public PenTool(Button button, CheckMenuItem menuItem) {
		super(button, menuItem);
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		if(type == MouseEvent.MOUSE_PRESSED) {
			lastPos[0] = e.getX();
			lastPos[1] = e.getY();
		}
		if(type == MouseEvent.MOUSE_DRAGGED) {
			double[] newPos = new double[]{ e.getX(), e.getY() };
			double lineWidth = controller.getDrawCanvas().getGraphicsContext2D().getLineWidth();
			if(CanvasUtil.distance(lastPos, newPos) > (lineWidth / 2)) {
				controller.getDrawCanvas().getGraphicsContext2D().strokeLine(lastPos[0], lastPos[1], newPos[0], newPos[1]);
				lastPos = newPos;
			} else {
				GraphicsContext gc = controller.getDrawCanvas().getGraphicsContext2D();
				javafx.scene.paint.Paint fill = gc.getFill();
				gc.setFill(gc.getStroke());
				gc.fillOval(newPos[0] - (lineWidth / 2), newPos[1] - (lineWidth / 2), lineWidth, lineWidth);
				gc.setFill(fill);
				lastPos = newPos;
			}
		}
		if(type == MouseEvent.MOUSE_RELEASED) {
			controller.getDrawer().drawToLayer();
		}
	}
	
}
