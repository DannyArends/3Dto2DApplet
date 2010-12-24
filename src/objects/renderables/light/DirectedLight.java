package objects.renderables.light;

import generic.MathUtils;

public class DirectedLight extends Light{
	public double[] direction;		
	public double[] oppositeDirection;
	
	public DirectedLight(double x, double y, double z) {
		super(x,y,z);
	}
	
	public void setDirection(double[] dir){
		direction = dir;
		MathUtils.normalize(direction);
		oppositeDirection = MathUtils.oppositeVector(direction);
	}
	
	public double[] getAmountOfLight(double[] point) {					
		return getColor(); // constant light, regardless of distance to target
	}
	
	@Override
	public double[] getVectorToLight(double[] pointOfIntersection){
		return oppositeDirection;
	}
}
