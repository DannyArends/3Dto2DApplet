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

package objects.renderables;

import generic.MathUtils;

import java.awt.Color;

import objects.Edge;
import objects.Point3D;
import objects.Vector3D;

/// Triangle3D
//<p>
//Class to used to render a 3 Dimensional triangle
//</p>
//
public class Triangle3D extends Object3D{
	private double[] AB, AC;
	private double ABdotAB, ACdotAC;
	private double ABnorm;
	private double ACnorm;
	
	public Triangle3D(double x,double y, double z,int hrot, int vrot, double hscale, double vscale, Color c){
		super(x,y,z,hrot,vrot);
		Point3D[] vertices = {
				new Point3D(0, 0, (0.2*hscale)), 
				new Point3D(0, vscale, 0), 
				new Point3D(0, 0, (-0.2*hscale))};
		setVertices(vertices);
	
		Edge[] edges = {new Edge(0, 1), new Edge(1, 2), new Edge(2, 0)};
		setEdges(edges);
		
		Color[] colors = new Color[]{c};
		setEdgeColors(colors);
	}
	
	@Override
	public double intersect(Vector3D ray) {

		double distance = intersectWithPlane(ray,0);
		if(distance != Double.POSITIVE_INFINITY  &&  distance != Double.NEGATIVE_INFINITY){
			return intersectBarycentric(ray,0, distance);			
		}
		return Double.POSITIVE_INFINITY;
	}
	
	@Override
	public void bufferMyObject(){
		super.bufferMyObject();
		AB = MathUtils.calcPointsDiff(triangles[0][0], triangles[0][1]);
		ABdotAB = MathUtils.dotProduct(AB, AB);
		AC = MathUtils.calcPointsDiff(triangles[0][0], triangles[0][2]);
		ACdotAC = MathUtils.dotProduct(AC, AC);
		ABnorm = MathUtils.norm(AB);
		ACnorm = MathUtils.norm(AC);
	}

	@Override
	public double[] getTextureCoords(double[] point) {
		double[] AP;
		
		// Calculate the projection of the intersection point onto the rectangle vectors
		AP = MathUtils.calcPointsDiff(triangles[0][0], point);
		double q = 1 / MathUtils.norm(MathUtils.calcPointsDiff(triangles[0][0], triangles[0][1]));
				
		double u = MathUtils.dotProduct(AB, AP) / ABdotAB;
		double v = MathUtils.dotProduct(AC, AP) / ACdotAC;
		
		u /= ABnorm * q;
		v /= ACnorm * q;
		
		return new double[] { u, v };
	}
}

