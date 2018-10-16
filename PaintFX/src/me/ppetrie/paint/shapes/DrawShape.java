package me.ppetrie.paint.shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class DrawShape extends Shape {
	
	protected double[] center;
	protected double[] tl, br;
	
	/**
	 * Draws this shape<br />
	 * Some shapes (like Tube) may ignore the fill parameter
	 * @param graphics	the GraphicsContext onto which this image is being drawn
	 * @param fill		true if the shape should be filled, false otherwise
	 */
	public abstract void draw(GraphicsContext graphics, boolean fill);
	
}
