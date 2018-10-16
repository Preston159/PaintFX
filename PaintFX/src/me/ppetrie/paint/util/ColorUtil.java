package me.ppetrie.paint.util;

import javafx.scene.paint.Color;

public class ColorUtil {
	
	/**
	 * Converts a JavaFX Color to ARGB with no transparency
	 * @param c	the JavaFX color
	 * @return	the equivalent ARGB representation, where alpha is 255
	 */
	public static int colorToArgb(Color c) {
		int a = 255;
		int r = (int) (c.getRed() * 255);
		int g = (int) (c.getGreen() * 255);
		int b = (int) (c.getBlue() * 255);
		int out = 0;
		out += (a << 24);
		out += (r << 16);
		out += (g << 8);
		out += b;
		return out;
	}
	
}
