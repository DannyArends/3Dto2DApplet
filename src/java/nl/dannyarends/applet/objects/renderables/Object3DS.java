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

package nl.dannyarends.applet.objects.renderables;

import nl.dannyarends.applet.objects.Vector3D;

/// Object3DS (model file)
//<p>
//Holds vertices edges and normals, and after object buffering triangles
//</p>
//

public class Object3DS extends Object3D{
	
	public Object3DS(String name){
		super(0,0,0);
		setRotation(0,90);		//Always there is a 90Degree flip
		setName(name);
	}
	
	public Object3DS(String name, double x,double y,double z){
		super(x,y,z);
	}

	public Object3DS(Object3DS o) {
		super(o);
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
