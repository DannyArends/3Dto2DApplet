/*
#
# Triangle3D.java
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

package objects;

import java.awt.Color;


public class Triangle3D extends Object3D{
	
	public Triangle3D(double x,double y, double z,int hrot, int vrot,double hscale,double vscale,Color c){
		super(x,y,z,hrot,vrot);
		Point3D[] vertices = new Point3D[3];
			vertices[0] = new Point3D(0, 0, .2*hscale);
			vertices[1] = new Point3D(0, vscale, 0);
			vertices[2] = new Point3D(0.0, 0, -0.2*hscale);
		this.setVertices(vertices);
	
		Edge[] edges = new Edge[3];
			edges[0] = new Edge(0, 1);
			edges[1] = new Edge(1, 2);
			edges[2] = new Edge(2, 0);
		this.setEdges(edges);
		
		Color[] colors = new Color[1];
		colors[0] = c;
		setEdgeColors(colors);
	}
}

