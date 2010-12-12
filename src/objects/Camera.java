/*
#
# Camera.java
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

import objects.renderables.Object3D;

public class Camera extends Object3D{

	public Camera(double x, double y, double z){
		super(x,y,z);
	}
	
	public Camera(double x, double y, double z,int hrot, int vrot){
		super(x,y,z,hrot,vrot);
	}
	
	public void move(int x, int y, int z){
		this.x+=5*x;
		this.y+=5*y;
		this.z+=5*z;
	}
}
