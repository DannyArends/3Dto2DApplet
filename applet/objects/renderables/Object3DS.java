/*
#
# Object3DS.java
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

package objects.renderables;

import objects.Vector3D;


public class Object3DS extends Object3D{
	
	public Object3DS(String name){
		super(0,0,0);
		setRotation(0,90);		//Always there is a 90Degree flip
		setObjectScale(0.10);	//And objects are designed quite large
		setName(name);
	}
	
	public Object3DS(String name, double x,double y,double z){
		super(x,y,z);
		setRotation(0,90);
		setObjectScale(0.10);
		setName(name);
	}

	public Object3DS(Object3DS o) {
		super((Object3D) o);
		setRotation(0,90);
		setObjectScale(0.10);
		setName(o.getName());
	}
	
	@Override
	public double intersect(Vector3D ray) {
		for(int i=0;i< edges.length/3;i++){
			double distance = intersectWithPlane(ray,i);
			if(distance != Double.POSITIVE_INFINITY  &&  distance != Double.NEGATIVE_INFINITY){
				distance = intersectBarycentric(ray, i, distance);
				if(distance != Double.POSITIVE_INFINITY  &&  distance != Double.NEGATIVE_INFINITY){
					return distance;
				}
			}
		}
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double[] getTextureCoords(double[] point) {
		// TODO Auto-generated method stub
		return null;
	}
}
