/*
#
# Cube3D.java
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
