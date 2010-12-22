/*
#
# Object3D.java
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Vector;

import objects.Camera;
import objects.Edge;
import objects.Point2D;
import objects.Point3D;
import objects.TriangleMesh;
import rendering.Engine;


public class Object3D extends Point3D{
	private boolean wireframe = false;
	private Edge[] edges;
	private double objectScale=1.0;
	private Color[] edgeColors;
	private Vector<TriangleMesh> targets;
	private Point3D[] vertices;
	private Point2D[] mapcoords;

	
	public Object3D(double x, double y, double z){
		super(x,y,z);
		setHorizontalRotation(0);
		setVerticalRotation(0);
		this.targets = new Vector<TriangleMesh>();
	}
	
	public Object3D(double x, double y, double z,int hrot,int vrot){
		this(x,y,z);
		setHorizontalRotation(hrot);
		setVerticalRotation(vrot);
	}
	
	public Object3D(Object3D o) {
		super(o.x,o.y,o.z);
		setRotation(o.getHorizontalRotation(),o.getVerticalRotation());
		targets = o.targets;
		vertices = o.vertices;
		mapcoords = o.mapcoords;
		edges = o.edges;
		targets = o.targets;
	}

	public void setRotation(int hrot,int vrot){
		setHorizontalRotation(hrot);
		setVerticalRotation(vrot);
	}
	
	public void setVertices(Point3D[] vertices) {
		this.vertices = vertices;
	}
	
	public Point3D[] getVertices() {
		return vertices;
	}
	
	public void setEdges(Edge[] edges) {
		this.edges = edges;
	}
	
	public Edge[] getEdges() {
		return edges;
	}
	
	public void addTriangleMesh(TriangleMesh t){
		targets.add(t);
	}
	

	public void render(Graphics g, Camera c){
		// project vertices onto the 2D viewport
		Graphics2D g2d = (Graphics2D)g;
		Point2D[] points = new Point2D[vertices.length];
		GeneralPath path;
		int width = Engine.getWidth();
		int height = Engine.getHeight();
		int scaleFactor = (int) ((width / 8));
		g2d.setColor(Color.gray);
		double[] d;
		for (int j = 0; j < vertices.length; ++j) {
			d = computeOrtogonalProjection(vertices[j].x*objectScale,vertices[j].y*objectScale,vertices[j].z*objectScale,ownrotation);
			d = computeOrtogonalProjection(d[0]+ (this.x - c.x),d[1]+ (this.y - c.y),d[2]+ (this.z - c.z),rotation);
			if(!((d[2] + Engine.near + Engine.nearToObj) < 0)){
				//Calculate a perspective projection
				d=computePerspectiveProjection(d[0],d[1],d[2]);
				points[j] = new Point2D((int)(width/2 - scaleFactor * d[0]),(int)(height/2  - scaleFactor * d[1]));
			}
		}
		for(int j=0; j < edges.length;j+=3){
			path = new GeneralPath(GeneralPath.WIND_NON_ZERO);
			path.moveTo(points[edges[j].a].x, points[edges[j].a].y);
			path.lineTo(points[edges[j].a].x, points[edges[j].a].y);
			path.lineTo(points[edges[j].b].x, points[edges[j].b].y);
			path.lineTo(points[edges[j+1].a].x, points[edges[j+1].a].y);
			path.lineTo(points[edges[j+1].b].x, points[edges[j+1].b].y);
			path.lineTo(points[edges[j+2].a].x, points[edges[j+2].a].y);
			path.lineTo(points[edges[j+2].b].x, points[edges[j+2].b].y);
			path.closePath();
			g2d.draw(path);
			if(!wireframe) g2d.fill(path);	
		}
	}

	public void setEdgeColors(Color[] edgeColors) {
		this.edgeColors = edgeColors;
	}

	public Color[] getEdgeColors() {
		return edgeColors;
	}

	public void setMapcoords(Point2D[] mapcoords) {
		this.mapcoords = mapcoords;
	}

	public Point2D[] getMapcoords() {
		return mapcoords;
	}

	public void setObjectScale(double d) {
		this.objectScale = d;
	}

	public double getObjectScale() {
		return objectScale;
	}
	
	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}
	
}
