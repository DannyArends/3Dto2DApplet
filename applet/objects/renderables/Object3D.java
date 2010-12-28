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


import generic.MathUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import objects.Camera;
import objects.Edge;
import objects.Material3DS;
import objects.Point2D;
import objects.Point3D;
import objects.Vector3D;
import rendering.Engine;

public class Object3D extends Point3D{
	private boolean wireframe = false;
	private Edge[] edges;
	public double[] rotation = new double[8];
	public double[] ownrotation = new double[8];
	private int horizontalRotation;
	private int verticalRotation;
	private double objectScale=1.0;
	private Color[] edgeColors;
	private Point3D[] vertices;
	private Point2D[] mapcoords;
	
	public void setHorizontalRotation(int horizontalRotation) {
		this.horizontalRotation = horizontalRotation;
	}

	public int getHorizontalRotation() {
		return horizontalRotation;
	}

	public void setVerticalRotation(int verticalRotation) {
		this.verticalRotation = verticalRotation;
	}

	public int getVerticalRotation() {
		return verticalRotation;
	}
	
	public Object3D(double x, double y, double z){
		super(x,y,z);
		setHorizontalRotation(0);
		setVerticalRotation(0);
	}
	
	public Object3D(double x, double y, double z,int hrot,int vrot){
		this(x,y,z);
		setHorizontalRotation(hrot);
		setVerticalRotation(vrot);
	}
	
	public void update(Camera c){
		double theta = Math.PI * (c.getHorizontalRotation()) / 180.0;
		double phi = Math.PI * (c.getVerticalRotation()) / 180.0;
		rotation[0] = (float) Math.cos(theta);
		rotation[1] = (float) Math.sin(theta);
		rotation[2] = (float) Math.cos(phi);
		rotation[3] = (float) Math.sin(phi);
		rotation[4] = rotation[0] * rotation[2]; 
		rotation[5] = rotation[0] * rotation[3];
		rotation[6] = rotation[1] * rotation[2];
		rotation[7] = rotation[1] * rotation[3];
		
		theta = Math.PI * (getHorizontalRotation()) / 180.0;
		phi = Math.PI * (getVerticalRotation()) / 180.0;
		ownrotation[0] = (float) Math.cos(theta);
		ownrotation[1] = (float) Math.sin(theta);
		ownrotation[2] = (float) Math.cos(phi);
		ownrotation[3] = (float) Math.sin(phi);
		ownrotation[4] = ownrotation[0] * ownrotation[2]; 
		ownrotation[5] = ownrotation[0] * ownrotation[3];
		ownrotation[6] = ownrotation[1] * ownrotation[2];
		ownrotation[7] = ownrotation[1] * ownrotation[3];
	}
	
	public Object3D(Object3D o) {
		location = o.location;
		setRotation(o.getHorizontalRotation(),o.getVerticalRotation());
		vertices = o.vertices;
		mapcoords = o.mapcoords;
		edges = o.edges;
		edgeColors = o.edgeColors;
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
		this.edgeColors = new Color[edges.length];
		for(int x=0;x<edges.length;x++){
			this.edgeColors[x] = Color.white;
		}
	}
	
	public Edge[] getEdges() {
		return edges;
	}
	
	public void addTriangleColor(int[] targets,Material3DS m){
		for(int x : targets){
			this.edgeColors[x] = m.getAmbientColor();
		}
	}
	

	public void render(Graphics g, Camera c){
		// project vertices onto the 2D viewport
		Graphics2D g2d = (Graphics2D)g;
		Point2D[] points = new Point2D[vertices.length];
		GeneralPath path = null;
		int width = Engine.getWidth();
		int height = Engine.getHeight();
		int scaleFactor = (int) ((width / 8));
		double[] d;
		for (int j = 0; j < vertices.length; ++j) {
			d = computeOrtogonalProjection(vertices[j].getMultipleVector(objectScale),ownrotation);
			MathUtils.addVector(d, location);
			d = computeOrtogonalProjection(MathUtils.calcPointsDiff(c.location,d),c.rotation);
			
			if(!((d[2] + Engine.near + Engine.nearToObj) < 0)){
				//Calculate a perspective projection
				d=computePerspectiveProjection(d);
				points[j] = new Point2D((int)(width/2 - scaleFactor * d[0]),(int)(height/2  - scaleFactor * d[1]));
			}
		}
		for(int j=0; j < edges.length;j+=3){
			if(points[edges[j].a] != null && points[edges[j+1].a] != null && points[edges[j+2].a] != null){
			if(points[edges[j].b] != null && points[edges[j+1].b] != null && points[edges[j+2].b] != null){
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
				if(this.edgeColors!=null){
					g2d.setColor(edgeColors[j/3]);	
				}else{
					g2d.setColor(Color.green);
				}
				if(!wireframe) g2d.fill(path);	
			}
			}
		}
	}
	
	public void setEdgeColors(Color[] ec) {
		if(ec != null && ec.length==1){
			this.edgeColors = new Color[edges.length];
			for(int x=0;x<edges.length;x++){
				this.edgeColors[x] = ec[0];
			}
		}else{
			this.edgeColors = ec;
		}
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

	public double intersect(Vector3D ray) {
		return intersectGeometric(ray);
	}
	
	/**
	 * Calculate the intersection distance of this sphere with the given ray.
	 * Calculations are done in a geometric method, using pythagorean calculations. 
	 * Some references claim that this method may work faster than the algebraic method. 
	 * 
	 * @param ray
	 * @return
	 */
	private double intersectGeometric(Vector3D ray) {
		// Note that locals are named according to the equations in the lecture notes.
		double[] L = MathUtils.calcPointsDiff(ray.getLocation(), getLocation());
		double[] V = ray.getDirection();
		
		double tCA = MathUtils.dotProduct(L, V);
		
		if(tCA < 0) return Double.POSITIVE_INFINITY;
		
		double LSquare = MathUtils.dotProduct(L, L);
		double dSquare =  LSquare - MathUtils.sqr(tCA);
		double radiusSquare = MathUtils.sqr(0.5);

		if(dSquare > radiusSquare) return Double.POSITIVE_INFINITY;
		
		double tHC = Math.sqrt(radiusSquare - dSquare);

		if(MathUtils.dotProduct(L, L) < LSquare){
			// The ray originated in the sphere - the intersection is with the exit point
			return tCA + tHC;
		}else{
			// The ray originated ouside the sphere - the intersection is with the entrance point
			return tCA - tHC;
		}
	}

	public Material3DS getMaterialAt(double[] pointOfIntersection) {
		return new Material3DS("test");
	}

	public double[] getColorAt(double[] pointOfIntersection) {
		return new double[]{1.0,1.0,1.0};
	}

	public double[] getNormalAt(double[] pointOfIntersection) {
		double[] normal = MathUtils.calcPointsDiff(pointOfIntersection,location);		
		MathUtils.normalize(normal);
		return normal;
	}
	
}
