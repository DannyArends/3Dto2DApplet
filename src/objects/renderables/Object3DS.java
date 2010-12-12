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
