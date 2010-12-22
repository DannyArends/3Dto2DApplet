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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Vector;

import objects.Point2D;

abstract public class HudObject extends Point2D{
	private Point2D size = new Point2D();
	private boolean visible = true;
	private boolean minimized = false;
	private String name;
	private HudObject parent;
	public Vector<HudObject> children = new Vector<HudObject>();
	private Color color = Color.darkGray;
	
	public HudObject(){
	}
	
	public HudObject(int x, int y){
		super(x,y);
	}
	
	public HudObject(int x, int y, HudObject p){
		super(x,y);
		this.setParent(p);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		for(HudObject h : children){
			h.setVisible(visible);
		}
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setSize(Point2D size) {
		this.size = size;
	}
	
	public void setSize(int sx,int sy) {
		this.size = new Point2D(sx,sy);
	}

	public Point2D getSize() {
		return size;
	}
	
	public int getAbsoluteSizeX(){
		return (int) (getSize().x+x);
	}
	
	public int getAbsoluteSizeY(){
		return (int) (getSize().y+y);
	}
	
	public void addChild(HudObject o){
		if(o!=null){
			o.setLocation(o.x+x, o.y+y+20);
			children.add(o);
		}
	}
	
	public abstract void render(Graphics2D g);

	abstract public void handleKeystroke(KeyEvent e);

	abstract public boolean handleSlide(int mx, int my);

	public void setMinimized(boolean minimized) {
		this.minimized = minimized;
	}

	public boolean isMinimized() {
		return minimized;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public void setParent(HudObject parent) {
		this.parent = parent;
	}

	public HudObject getParent() {
		return parent;
	}
}
