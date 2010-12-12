package objects.renderables;

import java.awt.Color;

import objects.Edge;
import objects.Point3D;

public class Surface extends Object3D {

	Surface(double x, double y, double z) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
	}
	
	public Surface(double x, double y, double z,int hrot,int vrot, double hscale, double vscale, Color c) {
		super(x, y, z,hrot,vrot);
		// TODO Auto-generated constructor stub
		Point3D[] vertices = {new Point3D(-hscale, 0, -vscale), new Point3D(hscale, 0, -vscale), new Point3D(-hscale, 0.0, vscale), new Point3D(hscale, 0.0, vscale)};
		this.setVertices(vertices);
	
		Edge[] edges = {new Edge(0, 1), new Edge(1, 2), new Edge(2, 0),new Edge(0, 1), new Edge(1, 3), new Edge(3, 2)};
		this.setEdges(edges);
		
		Color[] colors = new Color[1];
		colors[0] = c;
		setEdgeColors(colors);
	}

}
