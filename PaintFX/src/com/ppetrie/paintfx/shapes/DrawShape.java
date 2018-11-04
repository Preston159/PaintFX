package com.ppetrie.paintfx.shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class DrawShape extends Shape {
	
	/**
	 * The center point of the shape
	 */
	protected double[] center;
	/**
	 * The top left corner of the shape
	 */
	protected double[] tl;
	/**
	 * The bottom right corner of the shape
	 */
	protected double[] br;
	
	/**
	 * Draws this shape<br>
	 * Some shapes (like Tube) may ignore the fill parameter
	 * @param graphics	the GraphicsContext onto which this image is being drawn
	 * @param fill		true if the shape should be filled, false otherwise
	 */
	public abstract void draw(GraphicsContext graphics, boolean fill);
	
}
