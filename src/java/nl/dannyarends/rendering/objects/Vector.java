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

package nl.dannyarends.rendering.objects;

import nl.dannyarends.generic.MathUtils;

/// Vector3D
//<p>
//Class representing an vector, contains a direction and magnitude
//</p>
//
public class Vector{
  private Location l;
	public double[] d = new double[3];
	double m;

	public Vector(double[] location,double[] direction,double magnitude){
		l = new Location(location);
		d = direction;
		m = magnitude;
	}
	
	public Vector(Vector v) {
    this(v.l.l,v.d,v.m);
  }

  public void setDirection(double[] dir){
		d=dir;
	}
	
	public double[] getDirection(){
		return d;
	}
	
	public void normalize(){
		double norm  = MathUtils.norm(d);
		d[0] /=norm;
		d[1] /=norm;
		d[2] /=norm;
		m = 1;
	}
	
	public Vector getNormalizedVector(){
		Vector r = new Vector(this);
		r.normalize();
		return r;
	}
	
	// Returns the dot product of the current vector's direction with the other vector's direction
	public double dotProduct(Vector v) {
		return(d[0] * v.d[0] + 
				   d[1] * v.d[1] +
				   d[2] * v.d[2]);
	}

	public double[] getEndPoint() {
		double[] endPoint = { l.l[0] + m * d[0], l.l[1] + m * d[1], l.l[2] + m * d[2] };
		return endPoint;
	}

	public void setMagnitude(double magnitude) {
		m=magnitude;
	}
}
