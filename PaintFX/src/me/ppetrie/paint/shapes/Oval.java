package me.ppetrie.paint.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import me.ppetrie.paint.Paint;

public class Oval extends DrawShape {
	
	int rx, ry;
	
	public Oval(Paint paint, double[] tl, double[] br) {
		this.paint = paint;
		this.center = new double[]{ (tl[0] + br[0] / 2), (tl[1] + br[1]) /2 };
		this.tl = tl;
		this.br = br;
		this.rx = (int) (br[0] - tl[0]) / 2;
		this.ry = (int) (br[1] - tl[1]) / 2;
	}
	
	@Override
	public void draw(GraphicsContext graphics, boolean fill) {
		if(fill) {
			graphics.fillArc(this.tl[0], this.tl[1], rx * 2, ry * 2, 0, 360, ArcType.CHORD);
		}
		graphics.strokeArc(this.tl[0], this.tl[1], rx * 2, ry * 2, 0, 360, ArcType.CHORD);
	}
	
}
