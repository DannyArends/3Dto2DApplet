/*
#
# Point3D.java
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

import generic.MathUtils;
import rendering.Engine;

public class Point3D {
	public double[] location = new double[3];

	public Point3D(){

	}
	
	public Point3D(double x, double y, double z){
		location[0] = x;
		location[1] = y;
		location[2] = z;
	}
	
	public void setLocation(double X, double Y, double Z){
		location[0] = X;
		location[1] = Y;
		location[2] = Z;
	}
	
	public double[] getLocation(){
		return location;
	}
	
	public Point3D(Point3D point3d) {
		location[0]=point3d.location[0];
		location[1]=point3d.location[1];
		location[2]=point3d.location[2];
	}

	public Point3D difference(Point3D c){
		Point3D d = new Point3D(location[0]-c.location[0],location[1]-c.location[1],location[2]-c.location[2]);
		return d;
	}
	
	public double[] difference(double[] c){
		double[] d = new double[]{location[0]-c[0],location[1]-c[1],location[2]-c[2]};
		return d;
	}
	
	public Point3D sum(Point3D c){
		Point3D d = new Point3D(location[0]+c.location[0],location[1]+c.location[1],location[2]+c.location[2]);
		return d;
	}
	
	public Point3D sum(double[] c){
		Point3D d = new Point3D(location[0]+c[0],location[1]+c[1],location[2]+c[2]);
		return d;
	}
	
	public void multiply(double factor){
		location[0] *=factor;
		location[1] *=factor;
		location[2] *=factor;
	}
	
	public Point3D getMultipleVector(double objectScale){
		Point3D r = new Point3D(this);
		r.multiply(objectScale);
		return r;
	}
	
	public double getLength(){
		return (Math.sqrt(MathUtils.sqr(location[0]) + MathUtils.sqr(location[1]) + MathUtils.sqr(location[2])));
	}
	
	public double pointDifference(Point3D p){
		return (Math.sqrt(MathUtils.sqrDiff(location[0] , p.location[0]) + MathUtils.sqrDiff(location[1] , p.location[1])+ MathUtils.sqrDiff(location[2] , p.location[2])));
	}
	
	public double xypointDifference(Point3D p){
		return (Math.sqrt(MathUtils.sqrDiff(location[0] , p.location[0]) + MathUtils.sqrDiff(location[2] , p.location[2])));
	}
	
	protected double[] computeOrtogonalProjection(Point3D p,double[] rotation){
		return computeOrtogonalProjection(p.location[0],p.location[1],p.location[2],rotation);
	}
	
	protected double[] computeOrtogonalProjection(double x0,double y0,double z0,double[] rotation){
		double[] d = new double[3];
		d[0] = rotation[0] * x0 + rotation[1] * z0;
		d[1] = -rotation[7] * x0 + rotation[2] * y0 + rotation[5] * z0;
		d[2] = rotation[4] * z0 - rotation[6] * x0 - rotation[3] * y0;
		return d;
	}
	
	protected double[] computePerspectiveProjection(double[] x){
		double[] d = new double[2];
		double t = Engine.near / (x[2] + Engine.near + Engine.nearToObj);
		d[0] = x[0] * t;
		d[1] = x[1] * t;
		return d;
	}
}
