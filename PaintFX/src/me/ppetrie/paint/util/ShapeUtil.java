package me.ppetrie.paint.util;

import java.util.HashMap;

public class ShapeUtil {
	
	private static final double FIFTH_CIRCLE = (2 * Math.PI) / 5;
	private static final double[][] STAR_PENT_POINTS = {
			{ Math.sin(FIFTH_CIRCLE), Math.cos(FIFTH_CIRCLE) },
			{ Math.sin(2 * FIFTH_CIRCLE), Math.cos(2 * FIFTH_CIRCLE) },
			{ Math.sin(3 * FIFTH_CIRCLE), Math.cos(3 * FIFTH_CIRCLE) },
			{ Math.sin(4 * FIFTH_CIRCLE), Math.cos(4 * FIFTH_CIRCLE) },
			{ Math.sin(5 * FIFTH_CIRCLE), Math.cos(5 * FIFTH_CIRCLE) }
	};
	private static final double[][] STAR_TIP_POINTS = {
			{ Math.sin(FIFTH_CIRCLE + (FIFTH_CIRCLE / 2)), Math.cos(FIFTH_CIRCLE + (FIFTH_CIRCLE / 2)) },
			{ Math.sin((2 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)), Math.cos((2 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)) },
			{ Math.sin((3 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)), Math.cos((3 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)) },
			{ Math.sin((4 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)), Math.cos((4 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)) },
			{ Math.sin((5 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)), Math.cos((5 * FIFTH_CIRCLE) + (FIFTH_CIRCLE / 2)) }
	};
	
	/**
	 * Calculates the inner points of a star
	 * @param tl		the top left corner of the shape
	 * @param br		the bottom right corner of the shape
	 * @param center	the center point of the shaper
	 * @return	the set of points in the order { {x1, y1}, {x2, y2}, ... {x1, y1} }
	 */
	public static double[][] getStarPentPoints(double[] tl, double[] br, double[] center) {
		double sWidth = (br[0] - tl[0]) / 6;
		double sHeight = (br[1] - tl[1]) / 6;
		double[][] out = new double[6][];
		for(int i = 0;i < 5;i++) {
			out[i] = new double[2];
			out[i][0] = STAR_PENT_POINTS[i][0];
			out[i][1] = STAR_PENT_POINTS[i][1];
			out[i][0] *= sWidth;
			out[i][1] *= sHeight;
			out[i][0] += center[0];
			out[i][1] += center[1];
		}
		out[5] = out[0];
		return out;
	}
	
	/**
	 * Calculates the outer points of a star
	 * @param tl		the top left corner of the shape
	 * @param br		the bottom right corner of the shape
	 * @param center	the center point of the shaper
	 * @return	the set of points in the order { {x1, y1}, {x2, y2}, ... }
	 */
	public static double[][] getStarTipPoints(double[] tl, double[] br, double[] center) {
		double hWidth = (br[0] - tl[0]) / 2;
		double hHeight = (br[1] - tl[1]) / 2;
		double[][] out = new double[5][];
		for(int i = 0;i < 5;i++) {
			out[i] = new double[2];
			out[i][0] = STAR_TIP_POINTS[i][0];
			out[i][1] = STAR_TIP_POINTS[i][1];
			out[i][0] *= hWidth;
			out[i][1] *= hHeight;
			out[i][0] += center[0];
			out[i][1] += center[1];
		}
		return out;
	}
	
	private static final HashMap<Integer, double[][]> NGON_POINTS = new HashMap<>();
	
	/**
	 * Calculates the points of an N-gon
	 * @param n			the number of sides
	 * @param tl		the top left corner of the shape
	 * @param br		the bottom right corner of the shape
	 * @param center	the center point of the shape
	 * @return	the set of points in the order { {x1, y1}, {x2, y2}, ... {x1, y1} }
	 */
	public static double[][] getNGonPoints(int n, double[] tl, double[] br, double[] center) {
		if(!NGON_POINTS.containsKey(n)) {
			double[][] points = new double[n][];
			double nth = (2 * Math.PI) / n;
			for(int i = 0;i < n;i++) {
				points[i] = new double[2];
				points[i][0] = Math.sin(i * nth);
				points[i][1] = Math.cos(i * nth);
			}
			NGON_POINTS.put(n, points);
		}
		double hWidth = (br[0] - tl[0]) / 2;
		double hHeight = (br[1] - tl[1]) / 2;
		double[][] out = new double[n + 1][];
		for(int i = 0;i < n;i++) {
			out[i] = new double[2];
			out[i][0] = NGON_POINTS.get(n)[i][0];
			out[i][1] = NGON_POINTS.get(n)[i][1];
			out[i][0] *= hWidth;
			out[i][1] *= hHeight;
			out[i][0] += center[0];
			out[i][1] += center[1];
		}
		out[n] = new double[2];
		out[n][0] = out[0][0];
		out[n][1] = out[0][1];
		return out;
	}
	
}
