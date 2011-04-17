package nl.dannyarends.generic;

import java.awt.Color;

public class ColorUtils {
	public static Color doubleToColor(double x,double maxx){
	    float cR = (float) (0.5 + (x / (2 * maxx)));
	    float cG = (float) (0.5 - (x / (2*maxx)));
	    float cB = (float) (0.5 -(x / (2 * maxx)));
	    Color c = new Color(cR, cG, cB);
	    return c;
	}
	
	// Converts an array of double in [0, 1] to SWT Color
	public static Color floatArrayToColor(double[] color) {
		int r = Math.min(255, (int)Math.round(color[0] * 255));
		int g = Math.min(255, (int)Math.round(color[1] * 255));
		int b = Math.min(255, (int)Math.round(color[2] * 255));
						
		return new Color(r, g, b);
	}
	
	// Converts an array of double in [0, 1] to an Integer representing a color
	public static int floatArrayToColorInt(double[] color) {
		int colorInt = Math.min(255, (int)Math.round(color[0] * 255)) << 16 & 0xFF0000 |
					   Math.min(255, (int)Math.round(color[1] * 255)) << 8 & 0xFF00 |
					   Math.min(255, (int)Math.round(color[2] * 255));
						
		return colorInt;
	}
	
	public static Color getRandomColor(){
		return new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)Math.random()*255);
	}
}
