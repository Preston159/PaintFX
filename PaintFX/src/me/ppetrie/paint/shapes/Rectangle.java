package me.ppetrie.paint.shapes;

import javafx.scene.canvas.GraphicsContext;
import me.ppetrie.paint.Paint;

public class Rectangle extends PointShape {
	
	private double[] tl, tr, bl, br;
	
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
