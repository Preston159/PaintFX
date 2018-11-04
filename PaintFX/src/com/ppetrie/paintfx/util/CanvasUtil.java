package com.ppetrie.paintfx.util;

import com.ppetrie.paintfx.Paint;

public class CanvasUtil {
	
	/**
	 * An integer wrapper for the
	 * {@link com.ppetrie.paintfx.util.CanvasUtil#getCorners(Paint, double, double, double, double) getCorners} function
	 * @param paint	the main JavaFX class of the program
	 * @param x1	the first x-coordinate
	 * @param x2	the second x-coordinate
	 * @param y1	the first y-coordinate
	 * @param y2	the second y-coordinate
	 * @return		two pairs of coordinates in the form { {x1, y1}, {x2, y2} } where the first pair is the top left corner
	 */
	public static int[][] getCorners(Paint paint, int x1, int x2, int y1, int y2) {
		double[][] doubleCorners = getCorners(paint, (double) x1, x2, y1, y2);
		int[] topLeft = new int[]{ (int) doubleCorners[0][0], (int) doubleCorners[0][1] };
		int[] bottomRight = new int[]{ (int) doubleCorners[1][0], (int) doubleCorners[1][1] };
		return new int[][]{ topLeft, bottomRight };
	}
	
	/**
	 * Takes a pair of coordinates and returns a pair of coordinates matching the same square where the first coordinate pair
	 * is the top left corner and the second is the bottom right, and the box does not extend past the edge of the canvas
	 * @param paint	the main JavaFX class of the program
	 * @param x1	the first x-coordinate
	 * @param x2	the second x-coordinate
	 * @param y1	the first y-coordinate
	 * @param y2	the second y-coordinate
	 * @return		two pairs of coordinates in the form { {x1, y1}, {x2, y2} } where the first pair is the top left corner
	 * and the second pair is the bottom right
	 */
	public static double[][] getCorners(Paint paint, double x1, double x2, double y1, double y2) {
		double[] topLeft = new double[2]; //control for backwards selection in x or y
		double[] bottomRight = new double[2];
		if(x1 < x2) {
			topLeft[0] = x1;
			bottomRight[0] = x2;
		} else {
			topLeft[0] = x2;
			bottomRight[0] = x1;
		}
		if(y1 < y2) {
			topLeft[1] = y1;
			bottomRight[1] = y2;
		} else {
			topLeft[1] = y2;
			bottomRight[1] = y1;
		}
		
		if(topLeft[0] < 0) topLeft[0] = 0; //if dragged box is outside canvas, use canvas edge instead
		if(topLeft[1] < 0) topLeft[1] = 0;
		double imageWidth = paint.getImageHandler().getImage().getWidth();
		double imageHeight = paint.getImageHandler().getImage().getHeight();
		if(bottomRight[0] > imageWidth) bottomRight[0] = imageWidth;
		if(bottomRight[1] > imageHeight) bottomRight[1] = imageHeight;
		return new double[][]{ topLeft, bottomRight };
	}
	
	/**
	 * Calculates the distance between two points
	 * @param a	the coordinate pair
	 * @param b	the second coordinate pair
	 * @return	the distance between
	 */
	public static double distance(double[] a, double[] b) {
		return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
	}
	
}
