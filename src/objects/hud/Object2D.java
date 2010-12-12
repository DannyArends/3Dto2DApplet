package objects.hud;

import java.awt.Graphics;
import java.awt.Graphics2D;

abstract public class Object2D extends Point2D{
	Object2D(int x, int y){
		super(x,y);
	}

	public Object2D(double x, double y) {
		super(x,y);
	}
	
	public abstract void render(Graphics2D g);
}
