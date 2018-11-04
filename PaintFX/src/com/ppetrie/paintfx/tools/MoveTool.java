package com.ppetrie.paintfx.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.ppetrie.paintfx.Paint;
import com.ppetrie.paintfx.PaintController;
import com.ppetrie.paintfx.util.CanvasUtil;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MoveTool extends Tool {
	
	/**
	 * The main class of the program
	 */
	private Paint paint;
	
	/**
	 * Whether an image is currently selected for placing
	 */
	private boolean awaitingPlace;
	/**
	 * Whether an image was just placed
	 */
	private boolean justPlaced;
	/**
	 * The current selection
	 */
	private BufferedImage selection;
	
	/**
	 * The position of the mouse when it was pressed
	 */
	private double[] lastPos = new double[2];
	
	/**
	 * Create a MoveTool
	 * @param button	the tool button corresponding to this tool
	 * @param menuItem	the menu item corresponding to this tool
	 * @param paint		the main class of the program
	 */
	public MoveTool(Button button, CheckMenuItem menuItem, Paint paint) {
		super(button, menuItem);
		this.paint = paint;
	}

	@Override
	public void handleMouse(MouseEvent e, EventType<? extends MouseEvent> type, PaintController controller) {
		if(type == MouseEvent.MOUSE_MOVED && awaitingPlace) {
			controller.getDrawer().clearDrawCanvas();
			Image toBePlaced = SwingFXUtils.toFXImage(selection, null);
			controller.getDrawCanvas().getGraphicsContext2D().drawImage(toBePlaced, e.getX(), e.getY());
		} else if(type == MouseEvent.MOUSE_PRESSED) {
			lastPos[0] = e.getX();
			lastPos[1] = e.getY();
			if(awaitingPlace) {
				BufferedImage bi = SwingFXUtils.fromFXImage(paint.getImageHandler().getImage(), null);
				bi.getGraphics().drawImage(selection, (int) e.getX(), (int) e.getY(), null);
				Image newImage = SwingFXUtils.toFXImage(bi, null);
				paint.getController().getDrawCanvas().getGraphicsContext2D().drawImage(newImage, 0, 0);
				awaitingPlace = false;
				justPlaced = true;
				
				controller.getDrawer().drawToLayer();
			}
		} else if(type == MouseEvent.MOUSE_RELEASED) {
			if(justPlaced) {
				justPlaced = false;
				return;
			}
			
			int x1 = (int) lastPos[0];
			int x2 = (int) e.getX();
			int y1 = (int) lastPos[1];
			int y2 = (int) e.getY();
			
			if(x1 == x2 || y1 == y2)
				return;
			
			int[][] corners = CanvasUtil.getCorners(paint, x1, x2, y1, y2);
			
			int[] topLeft = corners[0];
			int[] bottomRight = corners[1];
			
			int width = bottomRight[0] - topLeft[0];
			int height = bottomRight[1] - topLeft[1];
			
			BufferedImage bi = SwingFXUtils.fromFXImage(paint.getImageHandler().getImage(), null);
			selection = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			selection.setData(bi.getSubimage(topLeft[0], topLeft[1], width, height).getData());
			awaitingPlace = true;
			Graphics2D graphics = bi.createGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(topLeft[0], topLeft[1], width, height);
			Image newImage = SwingFXUtils.toFXImage(bi, null);
			paint.getController().getDrawCanvas().getGraphicsContext2D().drawImage(newImage, 0, 0);
			
			controller.getDrawer().drawToLayer();
		}
	}
	
	public void setSelection(BufferedImage bi) {
		this.selection = bi;
	}
	
	public BufferedImage getSelection() {
		return this.selection;
	}
	
	public void setAwaitingPlace() {
		this.awaitingPlace = true;
	}
	
	public boolean getAwaitingPlace() {
		return this.awaitingPlace;
	}
	
	public void reset() {
		justPlaced = false;
		awaitingPlace = false;
		selection = null;
	}
	
}
