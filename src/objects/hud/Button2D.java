/*
#
# Button2D.java
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

import game.Hud;
import generic.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import objects.Point2D;

public class Button2D extends Object2D{
	private Point2D size;
	private String name;
	
	public Button2D(int x,int y, int sx, int sy){
		super(x,y);
		setSize(new Point2D(sx,sy));
		setName("none");
	}
	
	public Button2D(int x,int y, String name){
		super(x,y);
		setSize(new Point2D(name.length()*15,20));
		setName(name);
	}
	
	public Button2D(Point2D loc,Point2D size){
		super(loc.x,loc.y);
		setSize(size);
	}

	public void setSize(Point2D size) {
		this.size = size;
	}

	public Point2D getSize() {
		return size;
	}
	
	public int getAbsoluteSizeX(){
		return (int) (size.x+x);
	}
	
	public int getAbsoluteSizeY(){
		return (int) (size.y+y);
	}

	public void runPayload() {
		Utils.console("Button at:"+x+","+y+"clicked");
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		Hud.drawBox(g, (int)x, (int)y, (int)size.x, (int)size.y, Color.darkGray);
		Hud.drawString(g, getName(), (int)x+10, (int)y+15);
	}

	@Override
	public void handleKeystroke(KeyEvent e) {
		
	}
}
