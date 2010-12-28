package rendering;

import objects.renderables.Object3D;

public class Intersection {
	private Object3D primitive;
	private double distance;
	
	public Intersection(double distance, Object3D primitive) {
		this.primitive = primitive;
		this.distance = distance;
	}

	public Object3D getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Object3D primitive) {
		this.primitive = primitive;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}	
}
