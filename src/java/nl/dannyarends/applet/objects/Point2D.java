/*
#
# Point2D.java
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

/// Point2D
//<p>
//Class representing a point in 2D space (INTEGER range)
//</p>
//
public class Point2D extends Object{
	public int x;
	public int y;
	
	public Point2D() {
	}
	
	public Point2D(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public void setLocation(int x,int y){
		this.x=x;
		this.y=y;
	}
	
}
