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

public class HudButton extends HudObject{
	private String name;
	private HudObject parent;
	
	public HudButton(int x,int y, int sx, int sy,HudObject p){
		super(x,y);
		setSize(new Point2D(sx,sy));
		if(p==null){
			ButtonControler.addButton(this);
		}else{
			p.addChild(this);
		}
	}
	
	public HudButton(int x,int y, int sx, int sy,HudObject p, String name,Color c){
		this(x,y,sx,sy,p);
		setSize(new Point2D(sx,sy));
		setName(name);
		setParent(p);
		setColor(c);
	}
	
	public HudButton(int x,int y, String name){
		this(x,y,name,null);
	}
	
	public HudButton(int x,int y, String name,HudObject p){
		this(x,y,name.length()*10,20,p);
		setName(name);
	}
	
	public HudButton(Point2D loc,Point2D size){
		this((int)loc.x,(int)loc.y,(int)size.x,(int)size.y,null);
		setName("None");
	}
	
	public HudButton(int x, int y, String name, boolean b) {
		this(x, y, name, b,null);
	}
	
	public HudButton(int x, int y, String name, boolean b,HudObject p) {
		this(x, y, name, b,Color.darkGray,p);
	}

	public HudButton(int x, int y, String name, boolean b,Color c,HudObject p) {
		this(x,y,name,p);
		setVisible(b);
		setColor(c);
	}

	public HudButton(int x, int y, int s, String name, boolean b) {
		this(x,y,s,20,null);
		setName(name);
		setVisible(b);
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
			Hud.drawBox(g, (int)x, (int)y, (int)getSize().x, (int)getSize().y, getColor());
			Hud.setFont(g, 1);
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


	public void setParent(HudObject parent) {
		this.parent = parent;
	}

	public HudObject getParent() {
		return parent;
	}
}
