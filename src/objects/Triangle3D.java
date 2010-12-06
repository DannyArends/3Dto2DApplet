package objects;

import java.awt.Color;


public class Triangle3D extends Object3D{
	
	public Triangle3D(double x,double y, double z,int hrot, int vrot,double hscale,double vscale,Color c){
		super(x,y,z,hrot,vrot);
		Point3D[] vertices = new Point3D[3];
			vertices[0] = new Point3D(0, 0, .5*hscale);
			vertices[1] = new Point3D(0, vscale, 0);
			vertices[2] = new Point3D(0.0, 0, -0.5*hscale);
		this.setVertices(vertices);
	
		Edge[] edges = new Edge[3];
			edges[0] = new Edge(0, 1);
			edges[1] = new Edge(0, 2);
			edges[2] = new Edge(1, 2);
		this.setEdges(edges);
		
		Color[] colors = new Color[1];
		colors[0] = c;
		setEdgeColors(colors);
	}
}

