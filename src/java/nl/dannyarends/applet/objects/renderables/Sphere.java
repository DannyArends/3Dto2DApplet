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


package nl.dannyarends.applet.objects.renderables;

import java.awt.Color;

import nl.dannyarends.generic.MathUtils;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.applet.objects.Edge;
import nl.dannyarends.applet.objects.Point3D;
import nl.dannyarends.applet.objects.Vector3D;


/// Sphere
//<p>
//Class to used to render a 3 Dimensional sphere
//</p>
//
public class Sphere extends Object3D {
	double radius;
	double p         = 20.0;
	double twopi     = 6.283185307;
	double pidtwo    = 1.570796326;

	public Sphere(double x, double y, double z, double r) {
		super(x, y, z);
		radius = r;
		calculatevertices();
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
	
	void calculatevertices(){
		double theta1,theta2,theta3;
		double ex,ey,ez;
		double px,py,pz;
		int tsum = 0;
		int ssum = 0;
		double si;
		int vertexcount = 0;
		int edgecount = 0;
		int cnt = 0;
		Point3D[] vertices = new Point3D[(int) ((p*p)*2)];
		Edge[] edges = new Edge[(int) ((p*p)*1.75)];
		for(int i = 0; i < p; i++ ){
			ssum++;
			si = i/2.0;
			theta1 = si * twopi / p - pidtwo;
			theta2 = (si + 1) * twopi / p - pidtwo;
			for(int j = 0; j < p; j+=2 ){
				theta3 = (p-j) * twopi / p;
				ex = Math.cos(theta2) * Math.cos(theta3);
			    ey = Math.sin(theta2);
			    ez = Math.cos(theta2) * Math.sin(theta3);
			    px = location[0] + radius * ex;
			    py = location[1] + radius * ey;
			    pz = location[2] + radius * ez;
			    vertices[vertexcount] = new Point3D(px, py, pz);
			    
			    ex = Math.cos(theta1) * Math.cos(theta3);
			    ey = Math.sin(theta1);
			    ez = Math.cos(theta1) * Math.sin(theta3);
			    px = location[0] + radius * ex;
			    py = location[1] + radius * ey;
			    pz = location[2] + radius * ez;
			    vertices[vertexcount+1] = new Point3D(px, py, pz);
			    edges[edgecount] = new Edge(vertexcount, vertexcount+1);

				theta3 = (p-j) * twopi / p;
				ex = Math.cos(theta2) * Math.cos(theta3);
			    ey = Math.sin(theta2);
			    ez = Math.cos(theta2) * Math.sin(theta3);
			    px = location[0] + radius * ex;
			    py = location[1] + radius * ey;
			    pz = location[2] + radius * ez;
			    vertices[vertexcount+2] = new Point3D(px, py, pz);
			    edges[edgecount+1] = new Edge(vertexcount+1, vertexcount+2);
			    
			    ex = Math.cos(theta1) * Math.cos(theta3);
			    ey = Math.sin(theta1);
			    ez = Math.cos(theta1) * Math.sin(theta3);
			    px = location[0] + radius * ex;
			    py = location[1] + radius * ey;
			    pz = location[2] + radius * ez;
			    vertices[vertexcount+3] = new Point3D(px, py, pz);
			    edges[edgecount+2] = new Edge(vertexcount+2, vertexcount+3);
			    if(cnt==0){
			    	edgecount+=3;
			    	cnt++;
			    }else{
			    	edges[edgecount+3] = new Edge(vertexcount-3, vertexcount+1);
			    	edgecount+=4;
			    	cnt--;
			    }
			    vertexcount+=4;
			}
		}
		setVertices(vertices);
		setEdges(edges);
		Utils.console("Created a sphere: " + vertexcount + " vertices, ssum: " + ssum + ", tsum: "+ tsum);
	}

	@Override
	public double intersect(Vector3D ray) {
		return intersectGeometric(ray,radius);
	}
}
