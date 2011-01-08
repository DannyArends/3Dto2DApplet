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

import java.awt.Color;

import generic.MathUtils;
import objects.Edge;
import objects.Point3D;
import objects.Vector3D;

public class Sphere extends Object3D {
	double radius;

	public Sphere(double x, double y, double z, double r) {
		super(x, y, z);
		radius = r;
		// TODO Auto-generated constructor stub
		Point3D[] vertices = {new Point3D(x, y, z), new Point3D(x, y, z), new Point3D(x, y, z), new Point3D(x, y, z)};
		setVertices(vertices);
	
		Edge[] edges = {new Edge(0, 1), new Edge(1, 2), new Edge(2, 0),new Edge(0, 1), new Edge(1, 3), new Edge(3, 2)};
		setEdges(edges);
		
		Color[] colors = new Color[1];
		colors[0] = Color.red;
		setEdgeColors(colors);
	}

	/**
	 * Return texture coordinates on a sphere
	 * 
	 * @return double[] 2D texture coordinates
	 */
	@Override
	public double[] getTextureCoords(double[] point) {
	double[] rp = MathUtils.calcPointsDiff(location, point);
		
        double v = rp[2] / radius;
        
        if (Math.abs(v) > 1) v -= 1 * Math.signum(v);
        v = Math.acos(v);
        
        double u = rp[0] / (radius * Math.sin(v));
        
        if (Math.abs(u) > 1) u = Math.signum(u);
        u = Math.acos(u);               
        
        if (rp[1] < 0)
            u = -u;
        if (rp[2] < 0)
            v = v + Math.PI;
        
        if (Double.isNaN(u)) {
        	int a = 0; a++;
        }
        
        u = (u / (2 * Math.PI));
        v = (v / Math.PI);
        
        if (u > 1) u -= 1;
        if (u < 0) u += 1;
        
        if (v > 1) v -= 1;
        if (v < 0) v += 1;
        
        return new double[] {u , v };			
	}

	@Override
	public double intersect(Vector3D ray) {
		return intersectGeometric(ray,radius);
	}
}
