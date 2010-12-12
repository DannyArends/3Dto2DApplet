/*
#
# Object2D.java
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

package objects.hud;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import objects.Point2D;

abstract public class Object2D extends Point2D{
	Object2D(int x, int y){
		super(x,y);
	}

	public Object2D(double x, double y) {
		super(x,y);
	}
	
	public abstract void render(Graphics2D g);

	abstract public void handleKeystroke(KeyEvent e);

	abstract public boolean handleSlide(int mx, int my);
}
