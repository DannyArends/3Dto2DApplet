package objects;

public class Point3D {
	public double x, y, z;
	public boolean undefined;
	double near = 3.0f;
	double nearToObj = 2.5f;
	double[] rotation = new double[8];
	double[] ownrotation = new double[8];
	private int horizontalRotation;
	private int verticalRotation;

	public Point3D(){
		undefined=true;
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
	
	public Point3D(double X, double Y, double Z){
		undefined=false;
		x = X;
		y = Y;
		z = Z;
	}
	
	public void setLocation(double X, double Y, double Z){
		undefined=false;
		x = X;
		y = Y;
		z = Z;
	}
	
	public Point3D(Point3D point3d) {
		undefined=false;
		x=point3d.x;
		y=point3d.y;
		z=point3d.z;
	}

	public Point3D difference(Point3D c){
		Point3D d = new Point3D(this.x-c.x,this.y-c.y,this.z-c.z);
		return d;
	}
	
	public Point3D sum(Point3D c){
		Point3D d = new Point3D(this.x+c.x,this.y+c.y,this.z+c.z);
		return d;
	}
	
	public void multiply(float factor){
		this.x *=factor;
		this.y *=factor;
		this.z *=factor;
	}
	
	public Point3D getMultipleVector(float factor){
		Point3D r = new Point3D(this);
		r.x *=factor;
		r.y *=factor;
		r.z *=factor;
		return r;
	}
	
	public double magnitude(){
		return (Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z)));
	}
	
	public double magnitude(Point3D p){
		return (Math.sqrt((p.x * p.x) + (p.y * p.y) + (p.z * p.z)));
	}
	
	public double xymagnitude(Point3D p){
		return (Math.sqrt((p.x * p.x) + (p.z * p.z)));
	}
	
	public void normalize(){
		double length = magnitude();
		if(length==0) length = 1.0;
		this.x /=length;
		this.y /=length;
		this.z /=length;
	}
	
	public Point3D getNormalizedVector(){
		double length = magnitude();
		if(length==0) length = 1.0;
		Point3D r = new Point3D(this);
		r.x /=length;
		r.y /=length;
		r.z /=length;
		return r;
	}
	
	double[] computeOrtogonalProjection(double x0,double y0,double z0,double[] rotation){
		double[] d = new double[3];
		d[0] = rotation[0] * x0 + rotation[1] * z0;
		d[1] = -rotation[7] * x0 + rotation[2] * y0 + rotation[5] * z0;
		d[2] = rotation[4] * z0 - rotation[6] * x0 - rotation[3] * y0;
		return d;
	}
	
	double[] computePerspectiveProjection(double x0,double y0,double z0,double near, double nearToObj){
		double[] d = new double[2];
		d[0] = x0 * near / (z0 + near + nearToObj);
		d[1] = y0 * near / (z0 + near + nearToObj);
		return d;
	}
}
