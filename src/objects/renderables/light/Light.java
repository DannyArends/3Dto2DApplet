package objects.renderables.light;

import objects.Point3D;

public abstract class Light extends Point3D{
	private double[] color = {1, 1, 1};
	
	public Light() {
		super(0,0,0);
	}
	
	public Light(double x, double y, double z) {
		super(x,y,z);
	}
	
	public Light(double x, double y, double z,double c1,double c2,double c3) {
		super(x,y,z);
		this.color = new double[]{c1,c2,c3};
	}
	
	public abstract double[] getAmountOfLight(double[] point);
	public abstract double[] getVectorToLight(double[] pointOfIntersection);
	
	public void setColor(double[] color) {
		this.color = color;
	}
	public double[] getColor() {
		return color;
	}
}
