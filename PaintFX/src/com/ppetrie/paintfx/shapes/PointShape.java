package com.ppetrie.paintfx.shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class PointShape extends Shape {
	
	protected double[] center;
	
	/**
	 * @return	an array of points which make up the shape in the form { {x1, y1}, {x2, y2}, ... {x1, y1} }
	 */
	public abstract double[][] getPoints();
	
	/**
	 * Fill the inner portion of this shape
	 * @param graphics	the GraphicsContext onto which the shape is being drawn
	 */
	public abstract void fill(GraphicsContext graphics);
	
}
