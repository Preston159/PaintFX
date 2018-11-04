package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.Paint;

import javafx.scene.canvas.GraphicsContext;

public class Square extends PointShape {
	
	/**
	 * The top left corner of the square
	 */
	private double[] tl;
	/**
	 * The top right corner of the square
	 */
	private double[] tr;
	/**
	 * The bottom left corner of the square
	 */
	private double[] bl;
	/**
	 * The bottom right corner of the square
	 */
	private double[] br;
	
	/**
	 * Create a Square
	 * @param paint	the main class of the program
	 * @param tl	the top left corner of the square
	 * @param br	the bottom right corner of the square
	 */
	public Square(Paint paint, double[] tl, double[] br) {
		this.paint = paint;
		
		this.tl = tl;
		double side = br[0] - tl[0];
		this.br = new double[]{ this.tl[0] + side, this.tl[1] + side };
		this.tr = new double[]{ this.br[0], this.tl[1] };
		this.bl = new double[]{ this.tl[0], this.br[1] };
		
		this.center = new double[]{ (tl[0] + br[0] / 2), (tl[1] + br[1]) /2 };
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
