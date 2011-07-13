package nl.dannyarends.rendering.objects.lighting;

import nl.dannyarends.rendering.objects.Location;

public abstract class Light extends Location{
	public double[] color = {1.0, 1.0, 1.0};
	
	public Light() {
		super();
	}
	
	public Light(double x, double y, double z) {
		super(new double[]{x,y,z});
	}
	
	public Light(double x, double y, double z,double c1,double c2,double c3) {
		this(x,y,z);
		color = new double[]{c1,c2,c3};
	}
	
	public abstract double[] getAmountOfLight(double[] point);
	public abstract double[] getVectorToLight(double[] pointOfIntersection);
	
}
