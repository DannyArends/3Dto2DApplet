/*
#
# Hud.java
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

package rendering;

import events.ButtonControler;
import generic.Utils;
import genetics.QTLdataset;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Vector;

import objects.hud.HudObject;
import objects.hud.HudWindow;
import objects.hud.windows.AboutWindow;
import objects.hud.windows.ControlsWindow;
import objects.hud.windows.HelpWindow;
import objects.hud.windows.IconBar;
import objects.hud.windows.LoginWindow;
import objects.hud.windows.StatsWindow;

public class Hud extends HudObject{
	QTLdataset dataset;
	private static Vector<HudWindow> hudWindows = new Vector<HudWindow>();
	private ButtonControler buttonarray= new ButtonControler();
	
	static Font[] fonts = new Font[]{
		new Font("Dialog", Font.BOLD,  12),
		new Font("Dialog", Font.PLAIN, 10)
	};
	
	public Hud(int sx, int sy){
		super(sx,sy);
		addChild(new AboutWindow(100, 100));
		addChild(new HelpWindow(120, 120));
		addChild(new ControlsWindow(140, 140));
		addChild(new StatsWindow(160,160));
		addChild(new IconBar());
		addChild(new LoginWindow());
	}
	
	public void addDataset(QTLdataset d){
		dataset=d;
	}
	
	public static void drawString(Graphics2D g, String s,int x,int y){
		g.setColor(Color.gray);
		g.drawString(s, x+1, y+1);
		g.setColor(Color.white);
		g.drawString(s, x, y);
	}
	
	public static void drawBox(Graphics2D g, int x, int y, int width,int height,Color c){
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
		g.fillRect(x, y+2, width-10, height);
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
		g.fillRect(x, y+4, width-15, height-2);
	}
	
	public static void setFont(Graphics2D g,int f){
		if(f >0 && f< getFonts().length){
			g.setFont(getFonts()[f]);
		}else{
			g.setColor(Color.red);
			g.setFont(getFonts()[1]);
		}
	}
	
	public static Font[] getFonts() {
		return fonts;
	}

	public void setFonts(Font[] fonts) {
		Hud.fonts = fonts;
	}

	public void render(Graphics2D g){
		for(HudWindow o : getSortedHudWindows()){
			if(o.isNeedUpdate()) o.update();
			o.render(g);
		}
		buttonarray.render(g);
	}
	
	public static void showChildWindowByName(String name){
		deactivateChildren();
		for(HudWindow h : getHudWindows()){
			if(Engine.verbose) Utils.console("Showing Window " + name + " " + h.getName());
			if(h.getName().equalsIgnoreCase(name)){
				h.setVisible(!h.isVisible());
				h.setActive(true);
				Utils.console("Showing Window" + h.getName() + " is visible, active:" + h.isVisible() +","+h.isActive());
			}
		}
	}

	public static void addWindow(HudWindow window){
		getHudWindows().add(window);
	}
	
	public static void deactivateChildren(){
		for(HudWindow h : getHudWindows()){
			if(!h.getName().equalsIgnoreCase("iconbar"))h.setActive(false);
		}
	}
	
	public void addChild(HudObject o){
		try{
			getHudWindows().add((HudWindow)o);
		}catch(Exception e){
			Utils.log("Trying to add a non HudWindow to the HUD", System.err);
		}
	}

	@Override
	public void handleKeystroke(KeyEvent e) {
		
	}

	@Override
	public boolean handleSlide(int mx, int my) {
		return false;
	}

	public static void setHudWindows(Vector<HudWindow> hudWindows) {
		Hud.hudWindows = hudWindows;
	}

	public static Vector<HudWindow> getHudWindows() {
		return Hud.hudWindows;
	}
	
	public static Vector<HudWindow> getVisibleWindows() {
		Vector<HudWindow> visibleWindows = new Vector<HudWindow>();
		for(HudWindow e : hudWindows){
			if(e.isVisible())visibleWindows.add(e);
		}
		return visibleWindows;
	}
	
	public static Vector<HudObject> getHudObjectWindows() {
		Vector<HudObject> t = new Vector<HudObject>();
		for(HudWindow h : hudWindows){
			if(h.isActive())t.add((HudObject)h);
		}
		for(HudWindow h : hudWindows){
			if(!h.isActive())t.add((HudObject)h);
		}
		return t;
	}
	
	public static Vector<HudWindow> getSortedHudWindows() {
		Vector<HudWindow> t = new Vector<HudWindow>();
		for(HudWindow h : hudWindows){
			if(!h.isActive()) t.add(h);
		}
		for(HudWindow h : hudWindows){
			if(h.isActive()) t.add(h);
		}
		return t;
	}
}
