package com.ppetrie.paintfx.tools;

import com.ppetrie.paintfx.Paint;
import com.ppetrie.paintfx.PaintController;
import com.ppetrie.paintfx.events.Drawer.Shapes;
import com.ppetrie.paintfx.shapes.Circle;
import com.ppetrie.paintfx.shapes.DrawShape;
import com.ppetrie.paintfx.shapes.NGon;
import com.ppetrie.paintfx.shapes.Oval;
import com.ppetrie.paintfx.shapes.PointShape;
import com.ppetrie.paintfx.shapes.Rectangle;
import com.ppetrie.paintfx.shapes.Shape;
import com.ppetrie.paintfx.shapes.Square;
import com.ppetrie.paintfx.shapes.Star;
import com.ppetrie.paintfx.shapes.Tube;
import com.ppetrie.paintfx.util.CanvasUtil;

import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.MouseEvent;

public class ShapeTool extends Tool {
	
	/**
	 * The main class of the program
	 */
	private Paint paint;
	
	/**
	 * The currently-selected shape
	 */
	private Shapes currentShape = Shapes.RECTANGLE;
	/**
	 * Whether to fill shapes when drawn
	 */
	private boolean fillShapes = false;
	
	
	/**
	 * The position of the mouse when it was pressed
	 */
	private double[] lastPos = new double[2];
	
	/**
	 * Create a ShapeTool
	 * @param button	the tool button corresponding to this tool
	 * @param menuItem	the menu item corresponding to this tool
	 * @param paint		the main class of the program
	 */
	public ShapeTool(Button button, CheckMenuItem menuItem, Paint paint) {
		super(button, menuItem);
		this.paint = paint;
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		if(type == MouseEvent.MOUSE_PRESSED) {
			lastPos[0] = e.getX();
			lastPos[1] = e.getY();
		} else if(type == MouseEvent.MOUSE_DRAGGED) {
			controller.getDrawer().clearDrawCanvas();
			doShape(e, controller);
		} else if(type == MouseEvent.MOUSE_RELEASED) {
			doShape(e, controller);
			controller.getDrawer().drawToLayer();
		}
	}
	
	/**
	 * Update the drawn shape on the draw canvas
	 * @param event			the mouse event from the user
	 * @param controller	the controller corresponding to the canvas on which the update should be drawn
	 */
	private void doShape(MouseEvent event, PaintController controller) {
		Shape shape;
		double[][] corners = CanvasUtil.getCorners(paint, lastPos[0], event.getX(), lastPos[1], event.getY());
		switch(currentShape) {
		case RECTANGLE:
			shape = new Rectangle(paint, corners[0], corners[1]);
			break;
		case SQUARE:
			shape = new Square(paint, corners[0], corners[1]);
			break;
		case CIRCLE:
			shape = new Circle(paint, corners[0], corners[1]);
			break;
		case OVAL:
			shape = new Oval(paint, corners[0], corners[1]);
			break;
		case STAR:
			shape = new Star(paint, corners[0], corners[1]);
			break;
		case TUBE:
			shape = new Tube(paint, corners[0], corners[1]);
			break;
		case NGON:
			shape = new NGon(corners[0], corners[1]);
			break;
		default: return;
		}
		if(shape instanceof PointShape) {
			if(fillShapes) {
				((PointShape) shape).fill(controller.getDrawCanvas().getGraphicsContext2D());
			}
			double[][] points = ((PointShape) shape).getPoints();
			for(int i = 0;i < points.length - 1;i++) {
				double[] currPoint = points[i];
				double[] nextPoint = points[i + 1];
				controller.getDrawCanvas().getGraphicsContext2D().strokeLine(currPoint[0], currPoint[1], nextPoint[0], nextPoint[1]);
			}
		} else {
			((DrawShape) shape).draw(controller.getDrawCanvas().getGraphicsContext2D(), fillShapes);
		}
	}
	
	/**
	 * Set the currently-selected shape
	 * @param shape	the new shape
	 */
	public void setCurrentShape(Shapes shape) {
		this.currentShape = shape;
	}
	
	/**
	 * Set whether to fill shapes when drawn
	 * @param fill	whether to fill shapes
	 */
	public void setFillShapes(boolean fill) {
		this.fillShapes = fill;
	}
	
	/**
	 * @return	true if shapes are to be filled, false otherwise
	 */
	public boolean getFillShapes() {
		return this.fillShapes;
	}
	
}
