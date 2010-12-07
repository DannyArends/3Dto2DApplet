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
