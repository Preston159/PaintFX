package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.Paint;
import com.ppetrie.paintfx.util.ShapeUtil;

import javafx.scene.canvas.GraphicsContext;

public class Star extends PointShape {
	
	/**
	 * The points corresponding to the pentagon at the center of the star
	 */
	private double[][] pentPoints;
	/**
	 * The points corresponding to the tips of the star points
	 */
	private double[][] tipPoints;
	
	/**
	 * Create a Star
	 * @param paint	the main class of the program
	 * @param tl	the top left corner of the star
	 * @param br	the bottom right corner of the star
	 */
	public Star(Paint paint, double[] tl, double[] br) {
		this.center = new double[]{ (tl[0] + br[0]) / 2, (tl[1] + br[1]) / 2 };
		pentPoints = ShapeUtil.getStarPentPoints(tl, br, center);
		tipPoints = ShapeUtil.getStarTipPoints(tl, br, center);
	}

	@Override
	public double[][] getPoints() {
		return new double[][] {
			{ pentPoints[0][0], pentPoints[0][1] },
			{ tipPoints[0][0], tipPoints[0][1] },
			{ pentPoints[1][0], pentPoints[1][1] },
			{ tipPoints[1][0], tipPoints[1][1] },
			{ pentPoints[2][0], pentPoints[2][1] },
			{ tipPoints[2][0], tipPoints[2][1] },
			{ pentPoints[3][0], pentPoints[3][1] },
			{ tipPoints[3][0], tipPoints[3][1] },
			{ pentPoints[4][0], pentPoints[4][1] },
			{ tipPoints[4][0], tipPoints[4][1] },
			{ pentPoints[5][0], pentPoints[5][1] }
		};
	}

	@Override
	public void fill(GraphicsContext graphics) {
		double[] xPoints = new double[10];
		double[] yPoints = new double[10];
		double[][] points = getPoints();
		for(int i = 0;i < 10;i++) {
			xPoints[i] = points[i][0];
			yPoints[i] = points[i][1];
		}
		graphics.fillPolygon(xPoints, yPoints, 10);
	}
	
	
	
}
