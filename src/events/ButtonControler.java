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

import game.Scene;

import java.awt.Graphics2D;
import java.util.Vector;

import objects.hud.Button2D;
import objects.hud.InputBox;
import objects.hud.MenuButton2D;
import objects.hud.Slider;

public class ButtonControler {
	
	static Vector<Button2D> monitored = new Vector<Button2D>();
	public ButtonControler(){
		addMainMenu();
	}
	
	public static void checkLocation(int x,  int y){
		for(Button2D b : monitored){
			//Utils.console(""+x+","+y+"=="+b.x+","+b.y);
			if(b.x < x && b.y < y){
				if(b.getAbsoluteSizeX() > x && b.getAbsoluteSizeY() > y){
					//resetButtons();
					b.runPayload();
					//Scene.reDrawScene();
					//Scene.updateScene();
					break;
				}	
			}
		}
	}
	
	public static void resetButtons(){
		monitored.clear();
		addMainMenu();
	}
	
	public static void addMainMenu(){
		addButton(new MenuButton2D(0,0,"File"));
		addButton(new MenuButton2D(60,0,"Edit"));
		addButton(new MenuButton2D(120,0,"View"));
		addButton(new Button2D(180,0,"Help"));
		addButton(new InputBox(240,0,10));
		addButton(new Slider(500,15));
		addButton(new Slider(500,30));
		addButton(new Slider(500,45));
		addButton(new Slider(500,60));
		addButton(new Slider(500,75));
	}
	
	public static void rightClickMenu(int x, int y){
		resetButtons();
		addButton(new Button2D(x,y+00,"Button1"));
		addButton(new Button2D(x,y+20,"Button2"));
		addButton(new Button2D(x,y+40,"Button3"));
		addButton(new Button2D(x,y+60,"Button4"));
		Scene.reDrawScene();
		Scene.updateScene();
	}
	
	public static void addButton(Button2D b){
		monitored.add(b);
	}

	public void render(Graphics2D g) {
		for(Button2D b : monitored){
			b.render(g);
		}
	}

	public static void addButtons(Vector<Button2D> children) {
		resetButtons();
		for(Button2D b : children){
			addButton(b);
		}
		Scene.reDrawScene();
		Scene.updateScene();
	}
}
