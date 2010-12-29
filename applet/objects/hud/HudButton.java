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

import generic.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import rendering.Hud;
import rendering.Scene;

public class HudButton extends HudObject{

	public HudButton(){
		
	}
	
	public HudButton(int x,int y){
		super(x,y);
		setSize(100,20);
	}
	
	public HudButton(int x,int y,HudObject p){
		super(x,y,p);
		setSize(100,20);
	}
	
	public HudButton(int x,int y,String name){
		super(x,y);
		setSize(100,20);
		setName(name);
	}
	
	public HudButton(int x,int y, int sx, int sy, String name, boolean visible, Color c){
		this(x,y);
		setSize(sx,sy);
		setName(name);
		setColor(c);
		setVisible(visible);
	}
	
	public HudButton(int x,int y, int sx, int sy, String name, boolean visible, Color c,HudObject p){
		this(x,y,p);
		setSize(sx,sy);
		setName(name);
		setColor(c);
		setVisible(visible);
		setParent(p);
	}
	
	public void runPayload() {
		Utils.console("Window: " + getName());
		Hud.showChildWindowByName(getName());
		Scene.updateScene(true,false);
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
}
