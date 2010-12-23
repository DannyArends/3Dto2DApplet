/*
#
# Surface.java
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

import java.awt.Color;

import objects.Edge;
import objects.Point3D;

public class Surface extends Object3D {

	public Surface(double x, double y, double z) {
		super(x, y, z);
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
