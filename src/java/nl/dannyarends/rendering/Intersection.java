package nl.dannyarends.rendering;

import nl.dannyarends.rendering.objects.renderables.Object3D;

/**
 * \brief Class representing an intersection between a ray and an object<br>
 *
 * Class representing an intersection between a ray and an object
 * bugs: none found<br>
 */
public class Intersection {
	private Object3D primitive;
	private double distance;
	
	public Intersection(double d, Object3D p) {
		primitive = p;
		distance = d;
	}

	public Object3D getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Object3D p) {
		primitive = p;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double d) {
		distance = d;
	}	
}
