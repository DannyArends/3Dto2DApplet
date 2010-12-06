package objects;

public class Camera extends Object3D{

	public Camera(double x, double y, double z){
		super(x,y,z);
	}
	
	public Camera(double x, double y, double z,int hrot, int vrot){
		super(x,y,z,hrot,vrot);
	}
	
	public void move(int x, int y, int z){
		this.x+=x;
		this.y+=y;
		this.z+=z;
	}
}
