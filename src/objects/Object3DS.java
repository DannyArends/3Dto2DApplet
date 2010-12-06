package objects;


public class Object3DS extends Object3D{
	String objectName;

	
	public Object3DS(String name){
		super(0,0,0);
		setRotation(0,90);		//Always there is a 90Degree flip
		setObjectScale(0.10);	//And objects are designed quite large
		this.objectName=name;
	}
	
	public Object3DS(String name, double x,double y,double z){
		super(x,y,z);
		setRotation(0,90);
		setObjectScale(0.10);
		this.objectName=name;
	}
	

}
