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


package nl.dannyarends.applets.gameApplet.events;


import nl.dannyarends.generic.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import nl.dannyarends.rendering.Hud;
import nl.dannyarends.rendering.objects.hud.HudButton;
import nl.dannyarends.rendering.objects.hud.HudMenuButton;
import nl.dannyarends.rendering.objects.hud.HudObject;

/// Monitors mouse button click to head up display buttons
//<p>
//TODO
//</p>
//

public class ButtonControler {
	static HudButton tmp_button;
	static ArrayList<HudObject> monitored = new ArrayList<HudObject>();
	
	public ButtonControler(){
		addMainMenu();
	}
	
	/**
	 * Check if location of a mouse click on the HUD hits a HUD object
	 * @param x x location
	 * @param y y location
	 * 
	 */	
	public static boolean checkLocation(int x,  int y){
		boolean r = false;
		if(checkLocation(Hud.getHudObjectWindows(),x,y)) r = true;
		if(checkLocation(monitored,x,y)) r= true;	
		return r;
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
	
	public static String getLocationDescription(int x,  int y){
		String r = getLocationDescription(Hud.getHudObjectWindows(),x,y);
		r += getLocationDescription(monitored,x,y);	
		return r;
	}
	
	public static String getLocationDescription(ArrayList<HudObject> tocheck, int x,  int y){
		for(HudObject b : tocheck){
			if(b.x < x && b.y < y){
				if(b.getAbsoluteSizeX() > x && b.getAbsoluteSizeY() > y){
					if(b.isVisible()){
						return ((HudButton)b).getName();
					}
				}	
			}
		}
		return "";
	}
	
	public static void addMainMenu(){
		
		HudMenuButton mbfile = new HudMenuButton(5,20,70,20,"File");
		
		tmp_button = new HudButton(0,0,70,20,"Register",false,Color.darkGray);
		addButton(tmp_button);
		mbfile.addChild(tmp_button);
		
		tmp_button = new HudButton(0,20,70,20,"Login",false,Color.darkGray);
		addButton(tmp_button);
		mbfile.addChild(tmp_button);
		
		addButton(mbfile);
		
		HudMenuButton mbedit = new HudMenuButton(75,20,70,20,"Edit");
		
		tmp_button = new HudButton(0,0,70,20,"Terraform",false,Color.darkGray);
		addButton(tmp_button);
		mbedit.addChild(tmp_button);
		
		tmp_button = new HudButton(0,20,70,20,"Build",false,Color.darkGray);
		addButton(tmp_button);
		mbedit.addChild(tmp_button);
		
		addButton(mbedit);
		
		HudMenuButton mbview = new HudMenuButton(145,20,70,20,"View");
		
		tmp_button = new HudButton(0,0,70,20,"Other maps",false,Color.darkGray);
		addButton(tmp_button);
		mbview.addChild(tmp_button);
		
		tmp_button = new HudButton(0,20,70,20,"Maps stats",false,Color.darkGray);
		addButton(tmp_button);
		mbview.addChild(tmp_button);
		
		tmp_button = new HudButton(0,40,70,20,"User stats",false,Color.darkGray);
		addButton(tmp_button);
		mbview.addChild(tmp_button);
		
		addButton(mbview);
		
		HudMenuButton mbhelp = new HudMenuButton(215,20,70,20,"Help");
		tmp_button = new HudButton(0,0,70,20,"Controls",false,Color.darkGray);
		addButton(tmp_button);
		mbhelp.addChild(tmp_button);
		tmp_button = new HudButton(0,20,70,20,"About",false,Color.darkGray);
		addButton(tmp_button);
		mbhelp.addChild(tmp_button);
		tmp_button = new HudButton(0,40,70,20,"Help",false,Color.darkGray);
		addButton(tmp_button);
		mbhelp.addChild(tmp_button);
		addButton(mbhelp);
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
