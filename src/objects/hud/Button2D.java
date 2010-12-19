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

import events.ButtonControler;
import generic.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import objects.Point2D;
import rendering.Hud;
import rendering.Scene;

public class Button2D extends Object2D{
	private Point2D size;
	private String name;
	private Color color = Color.darkGray;

	private Object2D parent;
	
	public Button2D(int x,int y, int sx, int sy,HudWindow p){
		super(x,y);
		setSize(new Point2D(sx,sy));
		if(p==null){
			ButtonControler.addButton(this);
		}else{
			p.addChild(this);
		}
	}
	
	public Button2D(int x,int y, int sx, int sy,HudWindow p, String name,Color c){
		this(x,y,sx,sy,p);
		setSize(new Point2D(sx,sy));
		setName(name);
		setParent(p);
		setColor(c);
	}
	
	public Button2D(int x,int y, String name){
		this(x,y,name,null);
	}
	
	public Button2D(int x,int y, String name,HudWindow p){
		this(x,y,name.length()*10,20,p);
		setName(name);
	}
	
	public Button2D(Point2D loc,Point2D size){
		this((int)loc.x,(int)loc.y,(int)size.x,(int)size.y,null);
		setName("None");
	}
	
	public Button2D(int x, int y, String name, boolean b) {
		this(x, y, name, b,Color.darkGray);
	}

	public Button2D(int x, int y, String name, boolean b,Color c) {
		this(x,y,name);
		setVisible(b);
		setColor(c);
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
		Scene.updateScene();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void render(Graphics2D g) {
		if(isVisible()){
			Hud.drawBox(g, (int)x, (int)y, (int)size.x, (int)size.y, color);
			if(getName()!=null)Hud.drawString(g, getName(), (int)x+2, (int)y+15);
		}
	}

	@Override
	public void handleKeystroke(KeyEvent e) {
		
	}

	@Override
	public boolean handleSlide(int mx, int my) {
		return false;	
	}


	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setParent(Object2D parent) {
		this.parent = parent;
	}

	public Object2D getParent() {
		return parent;
	}
}
