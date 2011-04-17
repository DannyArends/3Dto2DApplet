package nl.dannyarends.applet.objects.renderables.light;

import nl.dannyarends.generic.MathUtils;

public class DirectedLight extends Light{
	public double[] direction;		
	public double[] oppositeDirection;
	
	public DirectedLight(double x, double y, double z) {
		super(x,y,z);
	}
	
	public void setDirection(double[] dir){
		MathUtils.normalize(dir);
		direction = dir;
		oppositeDirection = MathUtils.oppositeVector(dir);
	}
	
	public double[] getAmountOfLight(double[] point) {					
		return color;
	}
	
	@Override
	public double[] getVectorToLight(double[] pointOfIntersection){
		return oppositeDirection;
	}
}
