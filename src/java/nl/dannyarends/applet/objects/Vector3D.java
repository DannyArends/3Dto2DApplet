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

package nl.dannyarends.applet.objects;

import nl.dannyarends.generic.MathUtils;

/// Vector3D
//<p>
//Class representing an vector, contains a direction and magnitude
//</p>
//
public class Vector3D extends Point3D{
	public double[] direction = new double[3];
	double magnitude;

	public Vector3D() {
		
	}
	
	public Vector3D(double x, double y, double z){
		super(x,y,z);
	}
	
	public Vector3D(Vector3D o){
		this(o.location[0],o.location[1],o.location[2],o.direction[0],o.direction[1],o.direction[2],o.magnitude);
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
		location  = loc;
		direction = dir;
		magnitude = m;
	}
	
	public void setDirection(double[] dir){
		this.direction=dir;
	}
	
	public double[] getDirection(){
		return this.direction;
	}
	
	public void normalize(){
		double norm  = MathUtils.norm(direction);
		direction[0] /=norm;
		direction[1] /=norm;
		direction[2] /=norm;
		magnitude = 1;
	}
	
	public Vector3D getNormalizedVector(){
		Vector3D r = new Vector3D(this);
		r.normalize();
		return r;
	}
	
	// Returns the dot product of the current vector's direction with the other vector's direction
	public double dotProduct(Vector3D otherVec) {
		return (this.direction[0] * otherVec.direction[0] + 
				this.direction[1] * otherVec.direction[1] +
				this.direction[2] * otherVec.direction[2]);
	}

	public double[] getEndPoint() {
		double[] endPoint = { location[0] + magnitude * direction[0], location[1] + magnitude * direction[1], location[2] + magnitude * direction[2] };
		return endPoint;
	}

	public void setMagnitude(double m) {
		magnitude=m;
	}
}
