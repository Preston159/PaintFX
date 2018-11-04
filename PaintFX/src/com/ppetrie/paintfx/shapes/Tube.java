package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Tube extends DrawShape {
	
	/**
	 * The radius of the initial circle
	 */
	private int radius;
	
	/**
	 * Create a Tube
	 * @param paint	the main class of the program
	 * @param x		the x-coordinate of the center of the first circle
	 * @param y		the y-coordinate of the center of the first circle
	 * @param r		the radius of the first circle
	 */
	public Tube(Paint paint, int x, int y, int r) {
		this.paint = paint;
		this.center = new double[]{ x, y };
		this.radius = r;
	}
	
	/**
	 * Create a Tube
	 * @param paint	the main class of the program
	 * @param tl	the top left corner of the first circle
	 * @param br	the bottom right corner of the first circle
	 */
	public Tube(Paint paint, double[] tl, double[] br) {
		this.paint = paint;
		this.center = new double[]{ (tl[0] + br[0] / 2), (tl[1] + br[1]) /2 };
		this.tl = tl;
		this.br = br;
		this.radius = (int) (br[0] - tl[0]) / 2;
	}
	
	@Override
	public void draw(GraphicsContext graphics, boolean fill) {
		graphics.strokeArc(this.tl[0], this.tl[1], radius * 2, radius * 2, 0, 360, ArcType.CHORD);
		drawInset(graphics, (int) graphics.getLineWidth(), (int) graphics.getLineWidth());
	}
	
	/**
	 * Recursively draws the inset circles of the shape
	 * @param graphics	the GraphicsContext on which to draw
	 * @param inset		the inset distance from the previous circle
	 * @param width		the width of the new circle
	 */
	private void drawInset(GraphicsContext graphics, int inset, int width) {
		if(inset > radius)
			return;
		graphics.strokeArc(this.tl[0] + inset * 1.1, this.tl[1] + inset * 1.1, radius * 2 - (inset / 2), radius * 2 - (inset / 2),
				0, 360, ArcType.CHORD);
		drawInset(graphics, inset + width, width);
	}
	
}
