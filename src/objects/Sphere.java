package objects;

import generic.Utils;

public class Sphere extends Object3D {
	double size = 0.0f;
	int p = 20;
	double twopi = 6.28318530717958f;
	double pidtwo = 1.57079632679489f;

	public Sphere(double x, double y, double z) {
		super(x, y, z);
		Point3D[] vertices = new Point3D[p*(p+1)];
		Edge[] edges = new Edge[3*p*(p+1)];
		  double theta1,theta2,theta3;
		  double ex,ey,ez;
		  double px,py,pz;
		  int tsum = 0;
		  int ssum = 0;
		  double si;
		  for(int i = 0; i <= p; i++ ){
		    ssum++;
		    si = (double) (i)/2.0f;
		    theta1 = si * twopi / p - pidtwo;
		    theta2 = (si + 1) * twopi / p - pidtwo;
		    for(int j = 0; j <= p; j++ ){
		      tsum++;
		      theta3 = (p-j) * twopi / p;
		      ex = Math.cos(theta2) * Math.cos(theta3);
		      ey = Math.sin(theta2);
		      ez = Math.cos(theta2) * Math.sin(theta3);
				px = size * ex;
				py = size * ey;
				pz = size * ez;
		      vertices[i+j] = new Point3D(px, py, pz);
      
		      ex = Math.cos(theta1) * Math.cos(theta3);
		      ey = Math.sin(theta1);
		      ez = Math.cos(theta1) * Math.sin(theta3);
		        px = size * ex;
		        py = size * ey;
		        pz = size * ez;
     
		      vertices[i+j] = new Point3D(px, py, pz);
			  edges[i] = new Edge(i, i+1*j);
		    }
		  }
		this.setVertices(vertices);
		this.setEdges(edges);
		Utils.console("Done");
	}

	public Sphere(double x, double y, double z, int hrot, int vrot) {
		super(x, y, z, hrot, vrot);
	}

}
