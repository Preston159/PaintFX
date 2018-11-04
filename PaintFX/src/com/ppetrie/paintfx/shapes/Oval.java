package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Oval extends DrawShape {
	
	/**
	 * the x-radius of the oval
	 */
	private int rx;
	/**
	 * the y-radius of the oval
	 */
	private int ry;
	
	/**
	 * Create an Oval
	 * @param paint	the main class of the program
	 * @param tl	the top left corner of the shape
	 * @param br	the bottom right corner of the shape
	 */
	public Oval(Paint paint, double[] tl, double[] br) {
		this.paint = paint;
		this.center = new double[]{ (tl[0] + br[0] / 2), (tl[1] + br[1]) /2 };
		this.tl = tl;
		this.br = br;
		this.rx = (int) (br[0] - tl[0]) / 2;
		this.ry = (int) (br[1] - tl[1]) / 2;
	}
	
	@Override
	public void draw(GraphicsContext graphics, boolean fill) {
		if(fill) {
			graphics.fillArc(this.tl[0], this.tl[1], rx * 2, ry * 2, 0, 360, ArcType.CHORD);
		}
		graphics.strokeArc(this.tl[0], this.tl[1], rx * 2, ry * 2, 0, 360, ArcType.CHORD);
	}
	
}
