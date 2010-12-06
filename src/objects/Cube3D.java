package objects;

public class Cube3D extends Object3D{
	public Cube3D(double x,double y, double z,int hrot, int vrot,double size){
		super(x,y,z,hrot,vrot);
		
		Point3D[] vertices = new Point3D[8];
		vertices[0] = new Point3D(-size, -size, -size);
		vertices[1] = new Point3D(-size, -size, +size);
		vertices[2] = new Point3D(-size, +size, -size);
		vertices[3] = new Point3D(-size, +size, +size);
		vertices[4] = new Point3D(+size, -size, -size);
		vertices[5] = new Point3D(+size, -size, +size);
		vertices[6] = new Point3D(+size, +size, -size);
		vertices[7] = new Point3D(+size, +size, +size);
		this.setVertices(vertices);
		
		Edge[] edges = new Edge[12];
		edges[0] = new Edge(0, 1);
		edges[1] = new Edge(0, 2);
		edges[2] = new Edge(0, 4);
		edges[3] = new Edge(1, 3);
		edges[4] = new Edge(1, 5);
		edges[5] = new Edge(2, 3);
		edges[6] = new Edge(2, 6);
		edges[7] = new Edge(3, 7);
		edges[8] = new Edge(4, 5);
		edges[9] = new Edge(4, 6);
		edges[10] = new Edge(5, 7);
		edges[11] = new Edge(6, 7);
		this.setEdges(edges);
	}
}
