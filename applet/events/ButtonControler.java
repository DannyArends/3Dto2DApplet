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

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import objects.hud.HudButton;
import objects.hud.HudMenuButton;
import objects.hud.HudObject;
import rendering.Hud;

public class ButtonControler {
	static HudButton tmp_button;
	static ArrayList<HudObject> monitored = new ArrayList<HudObject>();
	
	public ButtonControler(){
		addMainMenu();
	}
	
	public static boolean checkLocation(int x,  int y){
		checkLocation(Hud.getHudObjectWindows(),x,y);
		return checkLocation(monitored,x,y);
	}
	
	public static boolean checkLocation(ArrayList<HudObject> tocheck, int x,  int y){
		for(HudObject b : tocheck){
			if(b.x < x && b.y < y){
				if(b.getAbsoluteSizeX() > x && b.getAbsoluteSizeY() > y){
					if(b.isVisible()){
						//Utils.console(b.getName()+ " "+x+","+y+"=="+b.x+","+b.y);
						((HudButton)b).runPayload();
						return true;
					}
				}	
			}
		}
		return false;
	}
	
	public static void addMainMenu(){
		addButton(new HudMenuButton(0,20,70,20,"File"));
		addButton(new HudMenuButton(70,20,70,20,"Edit"));
		addButton(new HudMenuButton(140,20,70,20,"View"));
		HudMenuButton mb = new HudMenuButton(210,20,70,20,"Help");
		tmp_button = new HudButton(0,0,70,20,"Controls",false,Color.darkGray);
		addButton(tmp_button);
		mb.addChild(tmp_button);
		tmp_button = new HudButton(0,20,70,20,"About",false,Color.darkGray);
		addButton(tmp_button);
		mb.addChild(tmp_button);
		tmp_button = new HudButton(0,40,70,20,"Help",false,Color.darkGray);
		addButton(tmp_button);
		mb.addChild(tmp_button);
		addButton(mb);
		Utils.console("MainMenu");
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
