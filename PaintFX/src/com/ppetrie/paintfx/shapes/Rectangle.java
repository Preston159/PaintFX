package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.Paint;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends PointShape {
	
	/**
	 * The top left corner of the rectangle
	 */
	private double[] tl;
	/**
	 * The top right corner of the rectangle
	 */
	private double[] tr;
	/**
	 * The bottom left corner of the rectangle
	 */
	private double[] bl;
	/**
	 * The bottom right corner of the rectangle
	 */
	private double[] br;
	
	/**
	 * Create a Rectangle
	 * @param paint	the main class of the program
	 * @param tl	the top left corner of the rectangle
	 * @param br	the bottom right corner of the rectangle
	 */
	public Rectangle(Paint paint, double[] tl, double[] br) {
		this.paint = paint;
		this.center = new double[]{ (tl[0] + br[0] / 2), (tl[1] + br[1]) /2 };
		this.tl = tl;
		this.br = br;
		this.tr = new double[]{ br[0], tl[1] };
		this.bl = new double[]{ tl[0], br[1] };
	}
	
	@Override
	public double[][] getPoints() {
		return new double[][]{ tl, tr, br, bl, tl };
	}
	
	@Override
	public void fill(GraphicsContext graphics) {
		graphics.fillRect(tl[0], tl[1], br[0] - tl[0], br[1] - tl[1]);
	}
	
}
