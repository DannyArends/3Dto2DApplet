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

import objects.hud.Button2D;
import objects.hud.InputBox;
import objects.hud.MenuButton2D;
import objects.hud.Slider;
import rendering.Scene;

public class ButtonControler {
	
	static Vector<Button2D> monitored = new Vector<Button2D>();
	
	public ButtonControler(){
		addMainMenu();
	}
	
	public static boolean checkLocation(int x,  int y){
		return checkLocation(monitored,x,y);
	}
	
	public static boolean checkLocation(Vector<Button2D> tocheck, int x,  int y){
		for(Button2D b : tocheck){
			//Utils.console(""+x+","+y+"=="+b.x+","+b.y);
			if(b.x < x && b.y < y){
				if(b.getAbsoluteSizeX() > x && b.getAbsoluteSizeY() > y){
					if(b.isVisible())b.runPayload();
					return true;
				}	
			}
		}
		return false;
	}
	
	public static void addMainMenu(){
		new MenuButton2D(0,0,"File");
		new MenuButton2D(70,0,"Edit");
		new MenuButton2D(140,0,"View");
		new Button2D(210,0,"Help");
		new InputBox(280,0,10);
	}
	
	public static void rightClickMenu(int x, int y){
		new Button2D(x,y+00,"Button1");
		new Button2D(x,y+20,"Button2");
		new Button2D(x,y+40,"Button3");
		new Button2D(x,y+60,"Button4");
	}
	
	public static void addButton(Button2D b){
		monitored.add(b);
	}

	public void render(Graphics2D g) {
		for(Button2D b : monitored){
			b.render(g);
		}
	}
}
