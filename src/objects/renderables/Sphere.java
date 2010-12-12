/*
#
# Sphere.java
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
#
#     This program is free software; you can redistribute it and/or
#     modify it under the terms of the GNU General Public License,
#     version 3, as published by the Free Software Foundation.
# 
#     This program is distributed in the hope that it will be useful,
#     but without any warranty; without even the implied warranty of
#     merchantability or fitness for a particular purpose.  See the GNU
#     General Public License, version 3, for more details.
# 
#     A copy of the GNU General Public License, version 3, is available
#     at http://www.r-project.org/Licenses/GPL-3
#
*/


package objects.renderables;

import objects.Edge;
import objects.Point3D;
import generic.Utils;

public class Sphere extends Object3D {
	double size = 0.0f;
	int p = 20;
	double twopi = 6.28318530717958f;
	double pidtwo = 1.57079632679489f;

	public Sphere(double x, double y, double z) {
		super(x, y, z);
		Point3D[] vertices = new Point3D[2*p];
		Edge[] edges = new Edge[p];
	  double theta1,theta2,theta3;
	  double ex,ey,ez;
	  double px,py,pz;
	  int tsum = 0;
	  int ssum = 0;
	  double si;
	  for(int i = 0; i < p; i++ ){
	    ssum++;
	    si = (double) (i)/2.0f;
	    theta1 = si * twopi / p - pidtwo;
	    theta2 = (si + 1) * twopi / p - pidtwo;
	    for(int j = 0; j < p; j++ ){
		      tsum++;
		      theta3 = (p-j) * twopi / p;
		      ex = Math.cos(theta2) * Math.cos(theta3);
		      ey = Math.sin(theta2);
		      ez = Math.cos(theta2) * Math.sin(theta3);
			  px = size * ex;
			  py = size * ey;
			  pz = size * ez;
		      vertices[(i*p)+j] = new Point3D(px, py, pz);
      
		      ex = Math.cos(theta1) * Math.cos(theta3);
		      ey = Math.sin(theta1);
		      ez = Math.cos(theta1) * Math.sin(theta3);
		      
		      px = size * ex;
		      py = size * ey;
		      pz = size * ez;
     
		      vertices[p+(i*p)+j] = new Point3D(px, py, pz);
			  edges[i] = new Edge((i*p)+j, p+(i*p)+j);
		    }
		  }
		this.setVertices(vertices);
		this.setEdges(edges);
		Utils.console("Done");
	}
}
