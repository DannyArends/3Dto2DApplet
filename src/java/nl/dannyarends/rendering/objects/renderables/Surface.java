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

package nl.dannyarends.rendering.objects.renderables;

import nl.dannyarends.generic.MathUtils;
import nl.dannyarends.rendering.objects.Edge;
import nl.dannyarends.rendering.objects.Point3D;
import nl.dannyarends.rendering.objects.Vector3D;

import java.awt.Color;


/// Surface
//<p>
//Class to used to render a 3 Dimensional tile
//</p>
//
public class Surface extends Object3D {
	private double[] AB, AC;
	private double ABdotAB, ACdotAC;
	private double ABnorm;
	private double ACnorm;

	public Surface(double x, double y, double z) {
		super(x, y, z);
	}
	
	public Surface(double x, double y, double z,int hrot,int vrot, double hscale, double vscale, Color c) {
		super(x, y, z,hrot,vrot);
		// TODO Auto-generated constructor stub
		Point3D[] vertices = {
				new Point3D(-hscale, 0, -vscale), 
				new Point3D(hscale, 0, -vscale), 
				new Point3D(-hscale, 0, vscale), 
				new Point3D(hscale, 0, vscale)};
		setVertices(vertices);
	
		Edge[] edges = {
				new Edge(0, 1), 
				new Edge(1, 2), 
				new Edge(2, 0),
				new Edge(0, 1), 
				new Edge(1, 3), 
				new Edge(3, 2)};
		setEdges(edges);
		
		Color[] colors = new Color[1];
		colors[0] = c;
		setEdgeColors(colors);
	}
	
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
	public double intersect(Vector3D ray) {
		double distance = intersectWithPlane(ray,0);
		if(distance != Double.POSITIVE_INFINITY  &&  distance != Double.NEGATIVE_INFINITY){
			return intersectBarycentric(ray,
					vertices[0].getWorldLocation(location),
					vertices[1].getWorldLocation(location),
					vertices[2].getWorldLocation(location), distance);			
		}
		return Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Plane barycentric intersection algorithm
	 * 
	 * @param ray Ray to check intersection with
	 * @param p0 A corner of the surface
	 * @param p1 A corner of the surface
	 * @param p2 A corner of the surface
	 * @param distance Distance from ray origin to the object
	 * @return Distance to intersection point
	 */
	public double intersectBarycentric(Vector3D ray,double[] p0,double[] p1,double[] p2, double distance) {
		if(vertices==null) return Double.POSITIVE_INFINITY;
		double[] v0, v1, v2;
		double dot00, dot01, dot02, dot11, dot12;
		double denominator, u, v;
				
		// Get the intersection point with the rectangle's plane
		ray.setMagnitude(distance);
		double[] intersectionPoint = ray.getEndPoint();

		// Compute vectors        
		v0 = MathUtils.calcPointsDiff(p0, p2);
		v1 = MathUtils.calcPointsDiff(p0, p1);
		v2 = MathUtils.calcPointsDiff(p0, intersectionPoint);

		// Compute dot products
		dot00 = MathUtils.dotProduct(v0, v0);
		dot01 = MathUtils.dotProduct(v0, v1);
		dot02 = MathUtils.dotProduct(v0, v2);
		dot11 = MathUtils.dotProduct(v1, v1);
		dot12 = MathUtils.dotProduct(v1, v2);

		// Compute barycentric coordinates
		denominator = 1 / (dot00 * dot11 - dot01 * dot01);
		u = (dot11 * dot02 - dot01 * dot12) * denominator;
		v = (dot00 * dot12 - dot01 * dot02) * denominator;

		// Check if point is in rectangle
		if ((u > 0) && (v > 0) && (u < 1) && (v < 1)) {
			return distance;
		}
		
		return Double.POSITIVE_INFINITY;
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
