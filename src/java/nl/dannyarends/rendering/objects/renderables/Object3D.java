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

package nl.dannyarends.rendering.objects.renderables;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import nl.dannyarends.generic.MathUtils;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.Engine;
import nl.dannyarends.rendering.objects.Camera;
import nl.dannyarends.rendering.objects.Edge;
import nl.dannyarends.rendering.objects.Material;
import nl.dannyarends.rendering.objects.Point2D;
import nl.dannyarends.rendering.objects.Point3D;
import nl.dannyarends.rendering.objects.Texture;
import nl.dannyarends.rendering.objects.Vector3D;



/// Abstract 3 Dimensional object
//<p>
//TODO
//</p>
//
public abstract class Object3D extends Point3D{
	private String name = "Object3D";
	private boolean wireframe = false;
	private boolean loaded = false;
	protected boolean buffered = false;
	public Edge[] edges = null;
	public double[] rotation = new double[8];
	public double[] ownrotation = new double[8];
	private int horizontalRotation;
	private int verticalRotation;
	private double objectScale=1.0;
	private Color[] edgeColors;
	public Point3D[] vertices;
	public Point2D[] mapcoords;
	private Point2D[] points = null;
	double transparant = 0.0;
	double[][][] triangles;
	double[][] normals;
	double[] distancequotients;
	boolean onServer = true;
	private ArrayList<Material> materials = new ArrayList<Material>();
	
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

	public Object3D(Object3D o) {
		super((Point3D)o);
		setRotation(o.getHorizontalRotation(),o.getVerticalRotation());
		vertices = o.vertices;
		mapcoords = o.mapcoords;
		edges = o.edges;
		triangles = o.triangles;
		normals = o.normals;
		distancequotients = o.distancequotients;
		edgeColors = o.edgeColors;
		materials = o.materials;
	}
	
	public double getTransparant() {
		return transparant;
	}
	
	public void setLocation(double X, double Y, double Z){
		super.setLocation(X,Y,Z);
	}
	
	public void setMaterials(ArrayList<Material> mlist) {
		materials = mlist;
	}

	public ArrayList<Material> getMaterials() {
		return materials;
	}
	
	/**
	 * Buffer the current objects vertices and edges into triangles and calculate the normals
	 * 
	 * @return
	 */	
	public void bufferMyObject(){
		if(!buffered && edges != null){
			triangles = new double[edges.length/3][3][3];
			normals = new double[edges.length/3][3];
			distancequotients = new double[edges.length/3];
			for (int i = 0; i < (edges.length-1); i+=3) {
				int l = (int) Math.floor(i/3.0);
				//Utils.console("x,y,z: " + vertices[edges[i].a].location[0] +","+vertices[edges[i].a].location[1] +","+vertices[edges[i].a].location[2]);
				triangles[l][0] = vertices[edges[i].a].getWorldLocation(location);
				triangles[l][1] = vertices[edges[i].b].getWorldLocation(location);
				triangles[l][1] = vertices[edges[i+1].b].getWorldLocation(location);
				double[] normal  = MathUtils.crossProduct(MathUtils.calcPointsDiff(triangles[l][0], triangles[l][1]), MathUtils.calcPointsDiff(triangles[l][0], triangles[l][2]));
				MathUtils.normalize(normal);
				normals[l] = normal;
				distancequotients[l] = -(MathUtils.dotProduct(normal, triangles[l][0]));
			}
			buffered = true;
		}
	}


	public void setTransparant(double t) {
		transparant = t;
	}
	
	public void setHorizontalRotation(int hRot) {
		horizontalRotation = hRot;
	}

	public int getHorizontalRotation() {
		return horizontalRotation;
	}

	public void setVerticalRotation(int vRot) {
		verticalRotation = vRot;
	}

	public int getVerticalRotation() {
		return verticalRotation;
	}
	
	public void TryLoadingFromName() {
		
	}
	
	/**
	 * Updates the objects rotation vectors based on the camera
	 * 
	 * @param c Camera
	 * @return
	 */	
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
		
		if(vertices!=null){
			Point2D[] points = new Point2D[vertices.length];
			int width = Engine.getWidth();
			int height = Engine.getHeight();
			int scaleFactor_w = (int) ((width / 1.90));
			int scaleFactor_h = (int) ((height / 1.40));
			double[] d;
			Point3D p;
			for (int j = 0; j < vertices.length; ++j) {
				p = new Point3D(vertices[j]);
				p = p.getMultipleVector(objectScale);
				MathUtils.addVector(p.location,location);
				d = computeOrtogonalProjection(p,ownrotation);
				d = computeOrtogonalProjection(MathUtils.calcPointsDiff(c.location,d),c.rotation);
				if(!inFrontOfCamera(d[2])){
					d=computePerspectiveProjection(d);
					points[j] = new Point2D((int)(width/2 - scaleFactor_w * d[0]),(int)(height/2  - scaleFactor_h * d[1]));
				}
			}
			setPoints(points);
		}
	}
	
	public void setRotation(int hrot,int vrot){
		setHorizontalRotation(hrot);
		setVerticalRotation(vrot);
	}
	
	public void setVertices(Point3D[] v) {
		vertices = v;
	}
	
	public Point3D[] getVertices() {
		return vertices;
	}
	
	public void setEdges(Edge[] e) {
		edges = e;
		bufferMyObject();
		edgeColors = new Color[edges.length];
		for(int x=0;x<edges.length;x++){
			edgeColors[x] = Color.white;
		}
	}
	
	public Edge[] getEdges() {
		return edges;
	}
	
	public void addTriangleColor(int[] targets,Material m){
		if(!materials.contains(m)) materials.add(m);
		for(int x : targets){
			edgeColors[x] = m.getAmbientColor();
		}
	}
	
	/**
	 * Render the object using the geometric transformation
	 * 
	 * @param g Graphics
	 * @param c Camera
	 * @return
	 */
	public void render(Graphics g, Camera c){
		if(points==null){
			Utils.log("Strange, We try to render something not initialized", System.err);
			return;
		}
		Graphics2D g2d = (Graphics2D)g;
		GeneralPath path = null;
		boolean textured = false;
		if(!materials.isEmpty()){
			if(materials.get(0).getTexture()!=null){
				textured=true;
				g2d.setPaint(materials.get(0).getTexture().getPaint());
			}
		}else{
			//Ugly green when we don't have a texture
			g2d.setPaint(Color.green);
		}
		for(int j=0; j < (edges.length-1);j+=3){
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
				if(!textured)g2d.setPaint(edgeColors[j/3]);
				g2d.draw(path);
				if(!wireframe) g2d.fill(path);	
			}
			}
		}
	}
	
	public void setEdgeColors(Color[] ec) {
		if(ec != null && ec.length==1){
			edgeColors = new Color[edges.length];
			for(int x=0;x<edges.length;x++){
				edgeColors[x] = ec[0];
			}
		}else{
			edgeColors = ec;
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
		objectScale = d;
		if(vertices!=null){
			for(int x =0;x < vertices.length;x++){
				MathUtils.multiplyVectorByScalar(vertices[x].location, objectScale);
			}
			bufferMyObject();
		}
	}

	public double getObjectScale() {
		return objectScale;
	}
	
	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wf) {
		wireframe = wf;
	}

	public abstract double intersect(Vector3D ray);
	
	/**
	 * Calculate the intersection distance of this sphere with the given ray.
	 * Calculations are done in a geometric method, using pythagorean calculations. 
	 * Some references claim that this method may work faster than the algebraic method. 
	 * 
	 * @param ray Ray to intersect with
	 * @param size Size of the objects
	 * @return
	 */
	protected double intersectGeometric(Vector3D ray, double size) {
		if(vertices==null) return Double.POSITIVE_INFINITY; 
		// Note that locals are named according to the equations in the lecture notes.
		double[] v = new double[3];
		v[0] = location[0];
		v[1] = location[1];
		v[2] = location[2];
		double[] L = MathUtils.calcPointsDiff(ray.location, v);
		double[] V = ray.getDirection();

		double tCA = MathUtils.dotProduct(L, V);
		
		if(tCA < 0) return Double.POSITIVE_INFINITY;
		
		double LSquare = MathUtils.dotProduct(L, L);
		double dSquare =  LSquare - MathUtils.sqr(tCA);
		double radiusSquare = MathUtils.sqr(size);

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

	public Material getMaterialAt(double[] pointOfIntersection) {
		if(materials.isEmpty()){
			return new Material("test");
		}else{
			return materials.get(0);
		}
	}

	public double[] getColorAt(double[] pointOfIntersection) {
		Material m = getMaterialAt(pointOfIntersection);
		if(m.getTexture() != null){
			return m.getTextureColor(getTextureCoords(pointOfIntersection));
		}else{
			return m.getAmbient();
		}
	}

	public double[] getNormalAt(double[] pointOfIntersection) {
		double[] normal = MathUtils.calcPointsDiff(pointOfIntersection,location);		
		MathUtils.normalize(normal);
		return normal;
	}
	
	/**
	 * Pre-intersection testing with plane of object
	 * 
	 * @param ray Ray to intersect with
	 * @param i Index to the normal of the object
	 * @return
	 */	
	public double intersectWithPlane(Vector3D ray, int i) {
		if(vertices==null) return Double.POSITIVE_INFINITY;
		// raySouce is called p0 in the lecture notes, it was rename to avoid conflicting names
		double[] raySource = ray.getLocation();
		double[] V = ray.getDirection();
		double distance = 0;
		double[] normal = normals[i];
		double d = distancequotients[i]; 	
		if(MathUtils.dotProduct(V, normal) != 0){
			distance = (-(MathUtils.dotProduct(raySource, normal) + d)) / MathUtils.dotProduct(V, normal);
		}
		if(distance <= 0) return Double.POSITIVE_INFINITY;
		return distance;
	}
	
	public abstract double[] getTextureCoords(double[] point);

	/**
	 * Triangle barycentric intersection algorithm
	 * 
	 * @param ray Ray to intersect with
	 * @param i Index to the normal of the object
	 * @param distance Distance to the intersecting object
	 * @return
	 */	
	public double intersectBarycentric(Vector3D ray,int i, double distance) {
		if(vertices==null) return Double.POSITIVE_INFINITY;
		double[] v0, v1, v2;
		double dot00, dot01, dot02, dot11, dot12;
		double denominator, u, v;
		// Get the intersection point with the rectangle's plane
		ray.setMagnitude(distance);
		double[] intersectionPoint = ray.getEndPoint();

		// Compute vectors        
		v0 = MathUtils.calcPointsDiff(triangles[i][0], triangles[i][2]);
		v1 = MathUtils.calcPointsDiff(triangles[i][0], triangles[i][1]);
		v2 = MathUtils.calcPointsDiff(triangles[i][0], intersectionPoint);

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

		// Check if point is in triangle
		if ((u > 0) && (v > 0) && (u + v < 1)) {
			return distance;
		}
		
		return Double.POSITIVE_INFINITY;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String n){
		name = n;
	}

	public void setPoints(Point2D[] p) {
		points = p;
	}

	public Point2D[] getPoints() {
		return points;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setTexture(Texture t) {
		Material m = new Material(t.getName());
		m.setTexture(t);
		materials.add(m);
	}

	public void exposed(String name2, String input) {
		if(name2.equals("x")){
			location[0] = Double.parseDouble(input);
		}
		if(name2.equals("y")){
			location[1] = Double.parseDouble(input);
		}
		if(name2.equals("z")){
			location[2] = Double.parseDouble(input);
		}
		if(name2.equals("hrot")){
			horizontalRotation = Integer.parseInt(input);
		}
		if(name2.equals("vrot")){
			verticalRotation = Integer.parseInt(input);
		}
	}
	
}