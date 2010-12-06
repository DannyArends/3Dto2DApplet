package objects;

import game.Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Vector;


public class Object3D extends Point3D{
	boolean wireframe = false;
	private Point3D[] vertices;
	private Point2D[] mapcoords;
	private Edge[] edges;
	private double objectScale=1.0;
	private Color[] edgeColors;
	private int horizontalRotation;
	private int verticalRotation;
	private Vector<TriangleMesh> targets;
	
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
		cosT = (float) Math.cos(theta);
		sinT = (float) Math.sin(theta);
		cosP = (float) Math.cos(phi);
		sinP = (float) Math.sin(phi);
		cosTcosP = cosT * cosP; 
		cosTsinP = cosT * sinP;
		sinTcosP = sinT * cosP;
		sinTsinP = sinT * sinP;
		
		theta = Math.PI * (getHorizontalRotation()) / 180.0;
		phi = Math.PI * (getVerticalRotation()) / 180.0;
		OWNcosT = (float) Math.cos(theta);
		OWNsinT = (float) Math.sin(theta);
		OWNcosP = (float) Math.cos(phi);
		OWNsinP = (float) Math.sin(phi);
		OWNcosTcosP = OWNcosT * OWNcosP; 
		OWNcosTsinP = OWNcosT * OWNsinP;
		OWNsinTcosP = OWNsinT * OWNcosP;
		OWNsinTsinP = OWNsinT * OWNsinP;

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
		double near = 3.0f; // distance from eye to near plane
		double nearToObj = 2.5f; // distance from near plane to center of object
		for (j = 0; j < vertices.length; ++j) {
			Point2D p = new Point2D();
			
			double x0 = vertices[j].x*objectScale;
			double y0 = vertices[j].y*objectScale;
			double z0 = vertices[j].z*objectScale;
			// compute an orthographic projection of the object
			double x1 = OWNcosT * x0 + OWNsinT * z0;
			double y1 = -OWNsinTsinP * x0 + OWNcosP * y0 + OWNcosTsinP * z0;
			double z1 = OWNcosTcosP * z0 - OWNsinTcosP * x0 - OWNsinP * y0;
			// compute a double orthographic projection of camera
			double x2 = x1 + (this.x - c.x);
			double y2 = y1 + (this.y - c.y);
			double z2 = z1 + (this.z - c.z);
			x1 = cosT * x2 + sinT * z2;
			y1 = -sinTsinP * x2 + cosP * y2 + cosTsinP * z2;
			z1 = cosTcosP * z2 - sinTcosP * x2 - sinP * y2;
			
			if(!((z1 + near + nearToObj) < 0)){
				//Calculate a perspective projection
				x1 = x1 * near / (z1 + near + nearToObj);
				y1 = y1 * near / (z1 + near + nearToObj);
				if(!((Engine.getWidth()/2 - scaleFactor * x1) < 0) && !((Engine.getWidth()/2 - scaleFactor * x1)<0)){
					p.setLocation((Engine.getWidth()/2 - scaleFactor * x1),(Engine.getHeight()/2  - scaleFactor * y1));
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
