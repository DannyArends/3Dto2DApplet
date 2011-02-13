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
import events.ServerConnection;
import generic.Utils;
import genetics.QTLdataset;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import objects.hud.HudObject;
import objects.hud.HudWindow;
import objects.hud.windows.AboutWindow;
import objects.hud.windows.BuildWindow;
import objects.hud.windows.ControlsWindow;
import objects.hud.windows.HelpWindow;
import objects.hud.windows.IconBar;
import objects.hud.windows.LoginWindow;
import objects.hud.windows.MouseOverWindow;
import objects.hud.windows.RegistrationWindow;
import objects.hud.windows.SettingsWindow;
import objects.hud.windows.StatsWindow;
import objects.hud.windows.TerraformWindow;
import objects.renderables.Object3D;

/// Head Up Display
//<p>
//TODO
//</p>
//

public class Hud extends HudObject{
	QTLdataset dataset;
	static boolean mouseOver = false;
	static MouseOverWindow mouseOverWindow = new MouseOverWindow(0,0);
	private static ArrayList<HudWindow> hudWindows = new ArrayList<HudWindow>();
	private ButtonControler buttonarray= new ButtonControler();
	
	static Font[] fonts = new Font[]{
		new Font("Dialog", Font.BOLD,  12),
		new Font("Dialog", Font.PLAIN, 10)
	};
	
	/**
	 * Initialize the HUD by loading all the windows
	 * 
	 * @param sx Width of the HUD
	 * @param sy Height of the HUD
	 * @return
	 */	
	public Hud(int sx, int sy, ServerConnection server){
		super(sx,sy);
		addChild(new AboutWindow(100, 100));
		addChild(new HelpWindow(120, 120));
		addChild(new ControlsWindow(140, 140));
		addChild(new StatsWindow(160,160,server));
		addChild(new SettingsWindow(180,180));
		addChild(new BuildWindow(server));
		addChild(new TerraformWindow(server));
		addChild(new IconBar());
		addChild(new LoginWindow());
		addChild(new RegistrationWindow());
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
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 255));
		g.fillRect(x, y+2, width-10, height);
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 255));
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
		if(mouseOverWindow.isNeedUpdate()) mouseOverWindow.update();
		mouseOverWindow.render(g);
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

	public static void setHudWindows(ArrayList<HudWindow> hudWindows) {
		Hud.hudWindows = hudWindows;
	}

	public static ArrayList<HudWindow> getHudWindows() {
		return Hud.hudWindows;
	}
	
	public static ArrayList<HudWindow> getVisibleWindows() {
		ArrayList<HudWindow> visibleWindows = new ArrayList<HudWindow>();
		for(HudWindow e : hudWindows){
			if(e.isVisible())visibleWindows.add(e);
		}
		return visibleWindows;
	}
	
	public static ArrayList<HudObject> getHudObjectWindows() {
		ArrayList<HudObject> t = new ArrayList<HudObject>();
		for(HudWindow h : hudWindows){
			if(h.isActive())t.add((HudObject)h);
		}
		for(HudWindow h : hudWindows){
			if(!h.isActive())t.add((HudObject)h);
		}
		return t;
	}
	
	public static ArrayList<HudWindow> getSortedHudWindows() {
		ArrayList<HudWindow> t = new ArrayList<HudWindow>();
		for(HudWindow h : hudWindows){
			if(!h.isActive()) t.add(h);
		}
		for(HudWindow h : hudWindows){
			if(h.isActive()) t.add(h);
		}
		return t;
	}

	public static boolean isMouseOver() {
		return mouseOver;
	}

	public static MouseOverWindow getMouseOverWindow() {
		return mouseOverWindow;
	}

	public static void setMouseOver(boolean b) {
		mouseOver = b;
	}
	
	public static void showMouseOver(int mx,int my) {
		if(!mouseOver){
			mouseOver=true;
			mouseOverWindow.setLocation(mx+15, my-10);
			mouseOverWindow.setShowTopMenu(false);
			String description = ButtonControler.getLocationDescription(mx,my);
			if(description.equals("")){
				Object3D o = Scene.getObjectAt(mx,my);
				if(o!=null){
					mouseOverWindow.setText(o.getName());
				}else{
					mouseOverWindow.setText("Nothing");
				}
			}else{
				mouseOverWindow.setText(description);
			}			
			mouseOverWindow.setVisible(true);
			Scene.updateScene(true,false);
		}
	}
}

