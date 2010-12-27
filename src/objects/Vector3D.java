/*
#
# Vector3D.java
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

package objects;


public class Vector3D extends Point3D{
	public double[] direction = new double[3];
	double magnitude;

	public Vector3D() {
		
	}
	
	public Vector3D(double x, double y, double z){
		super(x,y,z);
	}
	
	public Vector3D(double x, double y, double z,double vx, double vy, double vz){
		this(x,y,z,vx,vy,vz,1.0);
	}
	
	public Vector3D(double x, double y, double z,double vx, double vy, double vz,double m){
		super(x,y,z);
		direction[0]=vx;
		direction[1]=vy;
		direction[2]=vz;
		magnitude=m;
	}
	
	public Vector3D(double[] loc,double[] dir,double m){
		super(loc[0],loc[1],loc[2]);
		direction[0]=dir[0];
		direction[1]=dir[1];
		direction[2]=dir[2];
		magnitude=m;
	}
	
	public void setDirection(double[] dir){
		this.direction=dir;
	}
	
	public double[] getDirection(){
		return this.direction;
	}
	
	public double[] getEndPoint() {
		double[] endPoint = { location[0] + magnitude * direction[0], location[1] + magnitude * direction[1], location[2] + magnitude * direction[2] };
		return endPoint;
	}

	public void setMagnitude(double m) {
		magnitude=m;
	}
}
