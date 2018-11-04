package com.ppetrie.paintfx.events;

import java.awt.image.BufferedImage;

import com.ppetrie.paintfx.Paint;
import com.ppetrie.paintfx.PaintController;
import com.ppetrie.paintfx.dialogs.NDialog;
import com.ppetrie.paintfx.dialogs.ShapeDialog;
import com.ppetrie.paintfx.tools.ToolSet;
import com.ppetrie.paintfx.util.ColorUtil;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;

public class Drawer implements EventHandler<MouseEvent> {
	
	private Paint paint;
	
	/**
	 * The available tools
	 */
	public static enum Tools { LINE, PEN, ERASER, MOVE, SHAPE, FILL, DROPPER, TEXT }
	
	/**
	 * The available shapes
	 */
	public static enum Shapes { RECTANGLE, SQUARE, OVAL, CIRCLE, STAR, TUBE, NGON }
	
	/**
	 * The program's controller
	 */
	private PaintController controller;
	
	private ShapeDialog shapeDialog;
	private NDialog nDialog;
	
	/**
	 * The current tool
	 */
	private Tools currentTool = Tools.LINE;
	
	/**
	 * Whether shapes should be filled with the primary color (false) or the secondary color (true)
	 */
	private boolean fillShapesSec = false;
	
	/**
	 * The system clipboard
	 */
	private Clipboard clipboard;
	
	
	public Drawer(Paint paint, PaintController controller) {
		this.paint = paint;
		this.controller = controller;
		this.shapeDialog = new ShapeDialog();
		this.clipboard = Clipboard.getSystemClipboard();
	}
	
	/**
	 * Returns the current tool
	 * @return	The current tool
	 */
	public Tools getCurrentTool() {
		return this.currentTool;
	}
	
	/**
	 * Set the current tool
	 * @param tool	the new tool
	 */
	public void setTool(Tools tool) {
		this.currentTool = tool;
		if(tool == Tools.SHAPE) {
			ButtonType result = shapeDialog.showAndWait();
			if(result.equals(ShapeDialog.RECTANGLE)) {
				ToolSet.SHAPE.setCurrentShape(Shapes.RECTANGLE);
			} else if(result.equals(ShapeDialog.SQUARE)) {
				ToolSet.SHAPE.setCurrentShape(Shapes.SQUARE);
			} else if(result.equals(ShapeDialog.CIRCLE)) {
				ToolSet.SHAPE.setCurrentShape(Shapes.CIRCLE);
			} else if(result.equals(ShapeDialog.OVAL)) {
				ToolSet.SHAPE.setCurrentShape(Shapes.OVAL);
			} else if(result.equals(ShapeDialog.STAR)) {
				ToolSet.SHAPE.setCurrentShape(Shapes.STAR);
			} else if(result.equals(ShapeDialog.TUBE)) {
				ToolSet.SHAPE.setCurrentShape(Shapes.TUBE);
			} else if(result.equals(ShapeDialog.NGON)) {
				ToolSet.SHAPE.setCurrentShape(Shapes.NGON);
			} else {
				ToolSet.SHAPE.setCurrentShape(Shapes.RECTANGLE);
			}
			if(result.equals(ShapeDialog.NGON)) {
				nDialog.showAndWait();
			}
		} else if(tool == Tools.TEXT) {
			ToolSet.TEXT.handleText();
		}
	}
	
	/**
	 * @param width	the new stroke width
	 */
	public void setWidth(int width) {
		paint.getController().getDrawCanvas().getGraphicsContext2D().setLineWidth(width);
	}
	
	/**
	 * @param fill	whether to fill drawn shapes
	 */
	public void setFillShapes(boolean fill) {
		ToolSet.SHAPE.setFillShapes(fill);
	}
	
	/**
	 * @param fill	whether to fill drawn shapes with the secondary color rather than primary color
	 */
	public void setFillShapesWithSecondary(boolean fill) {
		this.fillShapesSec = fill;
	}
	
	/**
	 * @return	true if shapes are to be filled with the secondary color, or false if primary
	 */
	public boolean getFillShapesWithSecondary() {
		return this.fillShapesSec;
	}
	
	@Override
	public void handle(MouseEvent event) {
		boolean changed = false;
		
		EventType<? extends MouseEvent> type = event.getEventType();
		
		if(type == MouseEvent.MOUSE_PRESSED) {
			if(ToolSet.MOVE.getAwaitingPlace() && currentTool != Tools.MOVE) {
				ToolSet.MOVE.reset();
			}
		}
		
		switch(currentTool) {
		case LINE: {
			ToolSet.LINE.handleMouse(event, type, controller);
			break;
		}
		case ERASER: {
			controller.getDrawCanvas().getGraphicsContext2D().setStroke(javafx.scene.paint.Color.WHITE);
			ToolSet.PEN.handleMouse(event, type, controller);
			controller.getDrawCanvas().getGraphicsContext2D().setStroke(controller.getPrimaryColor());
			break;
		}
		case PEN: {
			ToolSet.PEN.handleMouse(event, type, controller);
			break;
		}
		case MOVE: {
			ToolSet.MOVE.handleMouse(event, type, controller);
			break;
		}
		case SHAPE: {
			ToolSet.SHAPE.handleMouse(event, type, controller);
			break;
		}
		case FILL: {
			int to = ColorUtil.colorToArgb(paint.getController().getPrimaryColor());
			fill(to, (int) event.getX(), (int) event.getY());
			break;
		}
		case DROPPER: {
			ToolSet.DROPPER.handleMouse(event, type, controller);
			break;
		}
		case TEXT: break;
		}
		
		if(changed) {
			double height = paint.getController().getCurrentLayer().getHeight();
			double width = paint.getController().getCurrentLayer().getWidth();
			SnapshotParameters params = new SnapshotParameters();
			params.setTransform(Transform.scale(paint.getController().getInverseScale(), paint.getController().getInverseScale()));
			params.setFill(javafx.scene.paint.Color.TRANSPARENT);
			Image drawn = controller.getDrawCanvas().snapshot(params, new WritableImage((int) width, (int) height));
			controller.getCurrentLayer().getGraphicsContext2D().drawImage(drawn, 0, 0);
			controller.getDrawCanvas().getGraphicsContext2D().clearRect(0, 0, width, height);
			paint.getImageHandler().setImageChanged(true);
			paint.getController().getEventListener().handleType(PaintEventType.IMAGE_CHANGED);
		}
	}
	
	/**
	 * Removes the section of the image selected by the move tool and copies it to the system clipboard
	 */
	public void cut() {
		BufferedImage selection = ToolSet.MOVE.getSelection();
		if(currentTool != Tools.MOVE || selection == null) {
			return;
		}
		ClipboardContent newContent = new ClipboardContent();
		newContent.putImage(SwingFXUtils.toFXImage(selection, null));
		clipboard.setContent(newContent);
		ToolSet.MOVE.reset();
		clearDrawCanvas();
	}
	
	/**
	 * Gets the image from the system clipboard (if present) and allows it to be placed
	 */
	public void paste() {
		WritableImage newImg;
		Image ti = clipboard.getImage();
		if(ti != null) {
			Image clipboardImg = clipboard.getImage();
			newImg = new WritableImage((int) clipboardImg.getWidth(), (int) clipboardImg.getHeight());
			PixelWriter writer = newImg.getPixelWriter();
			PixelReader reader = clipboardImg.getPixelReader();
			for(int x = 0;x < clipboardImg.getWidth();x++) {
				for(int y = 0;y < clipboardImg.getHeight();y++) {
					writer.setArgb(x, y, reader.getArgb(x, y) | 0xff000000); //fix strange transparency issue when copying from some programs on Windows
				}
			}
			ToolSet.MOVE.setSelection(SwingFXUtils.fromFXImage(newImg, null));
		}
		currentTool = Tools.MOVE;
		ToolSet.MOVE.setAwaitingPlace();	
	}
	
	/**
	 * Crops the image to the portion selected by the move tool
	 */
/*	public void crop() {
		if(currentTool == Tools.MOVE && ToolSet.MOVE.getAwaitingPlace()) {
			for(Canvas c : controller.getAllLayers()) {
				c.setWidth(ToolSet.MOVE.getSelection().getWidth());
				c.setHeight(ToolSet.MOVE.getSelection().getHeight());
			}
			controller.getDrawCanvas().setWidth(ToolSet.MOVE.getSelection().getWidth());
			controller.getDrawCanvas().setHeight(ToolSet.MOVE.getSelection().getHeight());
		}
		clearDrawCanvas();
		controller.getDrawCanvas().getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(ToolSet.MOVE.getSelection(), null), 0, 0);
		drawToLayer();
		ToolSet.MOVE.reset();
	}	*/
	
	private static final int LEFT = 0;
	private static final int UP = 1;
	private static final int RIGHT = 2;
	private static final int DOWN = 3;
	
	/**
	 * Begin a bucket fill
	 * @param to	the color to fill with (ARGB)
	 * @param x		the x-coordinate to begin fill
	 * @param y		the y-coordinate to begin fill
	 */
	public void fill(int to, int x, int y) {
		WritableImage writable = (WritableImage) paint.getImageHandler().getImage();
		PixelWriter writer = writable.getPixelWriter();
		PixelReader reader = writable.getPixelReader();
		int from = reader.getArgb(x, y);
		int w = (int) writable.getWidth();
		int h = (int) writable.getHeight();
		fill(from, to, x, y, w, h, reader, writer, -1);
		paint.getController().getDrawCanvas().getGraphicsContext2D().drawImage(writable, 0, 0);
	}
	
	private void fill(int from, int to, int x, int y, int w, int h, PixelReader reader, PixelWriter writer, int dirFrom) {
		writer.setArgb(x, y, to);
		if(dirFrom != LEFT && x - 1 >= 0 && reader.getArgb(x - 1, y) == from) {
			fill(from, to, x - 1, y, w, h, reader, writer, RIGHT);
		}
		if(dirFrom != UP && y - 1 >= 0 && reader.getArgb(x, y - 1) == from) {
			fill(from, to, x, y - 1, w, h, reader, writer, DOWN);
		}
		if(dirFrom != RIGHT && x + 1 < w && reader.getArgb(x + 1, y) == from) {
			fill(from, to, x + 1, y, w, h, reader, writer, LEFT);
		}
		if(dirFrom != DOWN && y + 1 < h && reader.getArgb(x, y + 1) == from) {
			fill(from, to, x, y + 1, w, h, reader, writer, UP);
		}
	}
	
	/**
	 * Clears the draw canvas
	 */
	public void clearDrawCanvas() {
		double height = paint.getController().getCurrentLayer().getHeight();
		double width = paint.getController().getCurrentLayer().getWidth();
		controller.getDrawCanvas().getGraphicsContext2D().clearRect(0, 0, width, height);
	}
	
	/**
	 * Copies the draw canvas contents onto the current layer
	 */
	public void drawToLayer() {
		double height = paint.getController().getCurrentLayer().getHeight();
		double width = paint.getController().getCurrentLayer().getWidth();
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(paint.getController().getInverseScale(), paint.getController().getInverseScale()));
		params.setFill(javafx.scene.paint.Color.TRANSPARENT);
		Image drawn = controller.getDrawCanvas().snapshot(params, new WritableImage((int) width, (int) height));
		controller.getCurrentLayer().getGraphicsContext2D().drawImage(drawn, 0, 0);
		controller.getDrawCanvas().getGraphicsContext2D().clearRect(0, 0, width, height);
		paint.getImageHandler().setImageChanged(true);
		paint.getController().getEventListener().handleType(PaintEventType.IMAGE_CHANGED);
	}
	
	
}
