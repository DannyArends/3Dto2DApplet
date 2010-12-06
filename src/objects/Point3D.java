package objects;

public class Point3D {
	public double x, y, z;
	public boolean undefined;

	public Point3D(){
		undefined=true;
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
}
