package objects;

import game.Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Vector;


public class Object3D extends Point3D{
	boolean wireframe = false;

	private Edge[] edges;
	private double objectScale=1.0;
	private Color[] edgeColors;
	double[] rotation = new double[8];
	double[] ownrotation = new double[8];
	private int horizontalRotation;
	private int verticalRotation;
	double near = 3.0f;
	double nearToObj = 2.5f;
	
	private Vector<TriangleMesh> targets;
	private Point3D[] vertices;
	private Point2D[] mapcoords;
	
	double sinT,cosT,sinP,cosP,cosTcosP,cosTsinP,sinTcosP,sinTsinP;
	double OWNsinT,OWNcosT,OWNsinP,OWNcosP,OWNcosTcosP,OWNcosTsinP,OWNsinTcosP,OWNsinTsinP;
	
	Object3D(double x, double y, double z){
		super(x,y,z);
		setHorizontalRotation(0);
		setVerticalRotation(0);
		this.targets = new Vector<TriangleMesh>();
	}
	
	Object3D(double x, double y, double z,int hrot,int vrot){
		this(x,y,z);
		setHorizontalRotation(hrot);
		setVerticalRotation(vrot);
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
	
	public void drawGeneralPath(Graphics2D g2d, GeneralPath p){
		//if(wireframe){
		if(getEdgeColors() != null && getEdgeColors().length==1){
			g2d.setColor(getEdgeColors()[0]);
		}else{
			g2d.setColor(Color.white);
		}
		g2d.draw(p);
		g2d.fill(p);

	}
	
	public void render(Graphics g, Camera c){
		// project vertices onto the 2D viewport
		Graphics2D g2d = (Graphics2D)g;
		Point2D[] points = new Point2D[vertices.length];
		int j;
		int scaleFactor = (int) ((Engine.getWidth() / 8));

		double[] d;
		for (j = 0; j < vertices.length; ++j) {
			Point2D p = new Point2D();
			d = computeOrtogonalProjection(vertices[j].x*objectScale,vertices[j].y*objectScale,vertices[j].z*objectScale,ownrotation);
			d = computeOrtogonalProjection(d[0]+ (this.x - c.x),d[1]+ (this.y - c.y),d[2]+ (this.z - c.z),rotation);
			if(!((d[2] + near + nearToObj) < 0)){
				//Calculate a perspective projection
				d=computePerspectiveProjection(d[0],d[1],d[2],near,nearToObj);
				if(!((Engine.getWidth()/2 - scaleFactor * d[0]) < 0) && !((Engine.getWidth()/2 - scaleFactor * d[0])<0)){
					p.setLocation((Engine.getWidth()/2 - scaleFactor * d[0]),(Engine.getHeight()/2  - scaleFactor * d[1]));
				}
			}
			points[j] = p;
		}

		// draw the wireframe
		GeneralPath p = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		Point2D from;
		boolean started=false;
		Point2D to;
		for (j = 0; j < edges.length; ++j) {
			from = points[edges[j].a];
			to = points[edges[j].b];
			 if((j%3)==0){
				if(started){
					p.closePath();
				}
				drawGeneralPath(g2d,p);
				p = new GeneralPath(GeneralPath.WIND_NON_ZERO);
				started=false;
			}
			if(from.isDefined() && to.isDefined() && from.isOnScreen() && to.isOnScreen() ){
				if(!started){
					p.moveTo((int)from.x, (int)from.y);
					started=true;
				}
				p.lineTo((int)from.x, (int)from.y);
				p.lineTo((int)to.x, (int)to.y);
			}
		}
		if(started){p.closePath(); drawGeneralPath(g2d,p);};
	}

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
	
}
