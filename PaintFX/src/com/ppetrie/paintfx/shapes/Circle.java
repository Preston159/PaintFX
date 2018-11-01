package com.ppetrie.paintfx.shapes;

import com.ppetrie.paintfx.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Circle extends DrawShape {
	
	int radius;
	
	public Circle(Paint paint, int x, int y, int r) {
		this.paint = paint;
		this.center = new double[]{ x, y };
		this.radius = r;
	}
	
	public Circle(Paint paint, double[] tl, double[] br) {
		this.paint = paint;
		this.center = new double[]{ (tl[0] + br[0] / 2), (tl[1] + br[1]) /2 };
		this.tl = tl;
		this.br = br;
		this.radius = (int) (br[0] - tl[0]) / 2;
	}
	
	@Override
	public void draw(GraphicsContext graphics, boolean fill) {
		if(fill) {
			graphics.fillArc(this.tl[0], this.tl[1], radius * 2, radius * 2, 0, 360, ArcType.CHORD);
		}
		graphics.strokeArc(this.tl[0], this.tl[1], radius * 2, radius * 2, 0, 360, ArcType.CHORD);
	}
	
}
