package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.util.ShapeUtil;

import javafx.scene.canvas.GraphicsContext;

public class NGon extends PointShape {
	
	private static int n = 5;
	
	private double[] tl, br;
	private double[] center;
	
	public NGon(double[] tl, double[] br) {
		this.tl = tl;
		this.br = br;
		this.center = new double[]{ (tl[0] + br[0]) / 2, (tl[1] + br[1]) / 2 };
	}

	@Override
	public double[][] getPoints() {
		return ShapeUtil.getNGonPoints(n, tl, br, center);
	}

	@Override
	public void fill(GraphicsContext graphics) {
		double[] xPoints = new double[n];
		double[] yPoints = new double[n];
		double[][] points = getPoints();
		for(int i = 0;i < n;i++) {
			xPoints[i] = points[i][0];
			yPoints[i] = points[i][1];
		}
		graphics.fillPolygon(xPoints, yPoints, n);
	}
	
	/**
	 * Updates the number of sides to be drawn by the shape
	 * @param n	the number of sides to draw
	 */
	public static void setN(int n) {
		NGon.n = n;
	}

}
