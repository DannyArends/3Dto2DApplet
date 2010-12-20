/*
#
# ButtonControler.java
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


package events;


import generic.Utils;

import java.awt.Graphics2D;
import java.util.Vector;

import objects.hud.HudButton;
import objects.hud.HudMenuButton;
import objects.hud.HudObject;
import rendering.Hud;
import rendering.Scene;

public class ButtonControler {
	
	static Vector<HudObject> monitored = new Vector<HudObject>();
	
	public ButtonControler(){
		addMainMenu();
	}
	
	public static boolean checkLocation(int x,  int y){
		return checkLocation(monitored,x,y);
	}
	
	public static boolean checkLocation(Vector<HudObject> tocheck, int x,  int y){
		for(HudObject b : tocheck){
			//Utils.console(""+x+","+y+"=="+b.x+","+b.y);
			if(b.x < x && b.y < y){
				if(b.getAbsoluteSizeX() > x && b.getAbsoluteSizeY() > y){
					if(b.isVisible()){
						((HudButton)b).runPayload();
						return true;
					}
				}	
			}
		}
		return false;
	}
	
	public static void addMainMenu(){
		new HudMenuButton(0,0,"File");
		new HudMenuButton(70,0,"Edit");
		new HudMenuButton(140,0,"View");
		HudMenuButton mb = new HudMenuButton(210,0,"Help");
		mb.addChild(new HudButton(0,0,100,"Controls",false){
			public void runPayload() {
				Hud.showChildWindowByName(getName());
				Scene.updateScene();
			}
		});
		mb.addChild(new HudButton(0,20,100,"About",false){
			public void runPayload() {
				Hud.showChildWindowByName(getName());
				Scene.updateScene();
			}
		});
		mb.addChild(new HudButton(0,40,100,"Help",false){
			public void runPayload() {
				Hud.showChildWindowByName(getName());
				Scene.updateScene();
			}
		});
	}
	
	public static void addButton(HudObject b){
		monitored.add(b);
	}

	public void render(Graphics2D g) {
		for(HudObject b : monitored){
			b.render(g);
		}
	}
}
