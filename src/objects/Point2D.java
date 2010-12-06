package objects;

import game.Engine;

public class Point2D{
	double x=-1;
	double y=-1;
	Point2D(){
		
	}
	
	public Point2D(double x,double y){
		this.x=x;
		this.y=y;
	}
	
	public void setLocation(double x,double y){
		this.x=x;
		this.y=y;
	}
	
	boolean isDefined(){
		return !(this.x==-1);
	}
	
	boolean isOnScreen(){
		return (this.x>0 && this.y > 0 && this.x < Engine.getWidth() && this.y < Engine.getHeight());
	}
}
