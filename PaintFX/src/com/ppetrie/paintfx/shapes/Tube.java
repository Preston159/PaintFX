package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Tube extends DrawShape {
	
	int radius;
	
	public Tube(Paint paint, int x, int y, int r) {
		this.paint = paint;
		this.center = new double[]{ x, y };
		this.radius = r;
	}
	
	public Tube(Paint paint, double[] tl, double[] br) {
		this.paint = paint;
		this.center = new double[]{ (tl[0] + br[0] / 2), (tl[1] + br[1]) /2 };
		this.tl = tl;
		this.br = br;
		this.radius = (int) (br[0] - tl[0]) / 2;
	}
	
	public void draw(GraphicsContext graphics, boolean fill) {
		graphics.strokeArc(this.tl[0], this.tl[1], radius * 2, radius * 2, 0, 360, ArcType.CHORD);
		drawInset(graphics, (int) graphics.getLineWidth(), (int) graphics.getLineWidth());
	}
	
	/**
	 * Recursively draws the inset circles of the shape
	 */
	private void drawInset(GraphicsContext graphics, int inset, int width) {
		if(inset > radius)
			return;
		graphics.strokeArc(this.tl[0] + inset * 1.1, this.tl[1] + inset * 1.1, radius * 2 - (inset / 2), radius * 2 - (inset / 2),
				0, 360, ArcType.CHORD);
		drawInset(graphics, inset + width, width);
	}
	
}
