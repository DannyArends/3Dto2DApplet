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

import objects.hud.HudImage;
import objects.hud.HudInputBox;
import objects.hud.HudObject;
import objects.hud.HudText;
import objects.hud.HudWindow;

public class Hud extends HudObject{
	QTLdataset dataset;
	private static Vector<HudWindow> hudWindows = new Vector<HudWindow>();
	private ButtonControler buttonarray = new ButtonControler();
	
	static Font[] fonts = new Font[]{
		new Font("Dialog", Font.BOLD,  12),
		new Font("Dialog", Font.PLAIN, 10)
	};
	
	public Hud(int sx, int sy){
		super(sx,sy);
		addAboutWindow(100, 100);
		addHelpWindow(100, 100);
		addControlsWindow(100, 100);
		addIconBar(0,Engine.height-100);
		addLoginWindow();
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
		buttonarray.render(g);
		for(HudObject o : getSortedHudWindows()){
			o.render(g);
		}
	}
	
	public static void showChildWindowByName(String name){
		deactivateChildren();
		for(HudWindow h : getHudWindows()){
			Utils.console("Showing Window " + name + " " + h.getName());
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

	public void addHelpWindow(int x, int y) {
		HudWindow h = new HudWindow(x,y,400,125,"HELP");
		h.setVisible(false);
		HudText t = new HudText(10,10,"Open Triangle: QTL at marker");
		t.addLine("Filled triangles: Selected Cofactor at marker");
		t.addLine("Triangle size: QTL effect/likelihood");
		h.addChild(t);
		h.addChild(new HudImage(300,10,"help.png"));
		this.addChild(h);
	}

	public void addControlsWindow(int x, int y) {
		HudWindow h = new HudWindow(x,y,400,250,"CONTROLS");
		h.setVisible(false);
		HudText t = new HudText(10,10,"Click and move mouse to look around");
		t.addLine("[Left]       step left");
		t.addLine("[Right]      step right");
		t.addLine("[Down]       step back");
		t.addLine("[Up]         step forward");
		t.addLine("[Page Up]    float up");
		t.addLine("[Page Up]    float up");
		t.addLine("[M]          Toggle between model only view");
		t.addLine("[+]/[-]      Increade/Decrease LOD score Cut-off");
		h.addChild(t);
		this.addChild(h);
	}

	public void addAboutWindow(int x, int y) {
		HudWindow h = new HudWindow(x,y,400,125,"ABOUT");
		h.setVisible(false);
		HudText t = new HudText(10,10,"QTL viewing applet");
		t.addLine("Part of the iqtl package");
		t.addLine("(c) 2010 Danny Arends - GBIC");
		t.addLine("https://github.com/DannyArends/3Dto2DApplet");
		h.addChild(t);
		h.addChild(new HudImage(300,10,"signup.png"));
		this.addChild(h);
	}
	
	public void addIconBar(int x, int y) {
		HudWindow h = new HudWindow(0,Engine.height-100,650,100,"ICONBAR");
		h.addChild(IconLoader.getIcon(25,0,"user_id.png"));
		h.addChild(IconLoader.getIcon(75,0,"walk.png"));
		h.addChild(IconLoader.getIcon(125,0,"world.png"));
		h.addChild(IconLoader.getIcon(175,0,"fight.png"));
		h.addChild(IconLoader.getIcon(225,0,"magic.png"));
		h.addChild(IconLoader.getIcon(275,0,"hammer.png"));
		h.addChild(IconLoader.getIcon(325,0,"leaf.png"));
		
		h.addChild(IconLoader.getIcon(450,0,"settings.png"));
		h.addChild(IconLoader.getIcon(500,0,"stats.png"));
		h.addChild(IconLoader.getIcon(550,0,"help.png"));
		h.setShowTopMenu(false);
		h.setActive(true);
		h.setVisible(true);
		this.addChild(h);
	}
	
	
	public void addLoginWindow() {
		HudWindow h = new HudWindow(Engine.width/2-200,Engine.height/2-100,325,200,"Login");
		h.setColor(Color.orange);
		HudText t = new HudText(10,10,"Login to the system");
		t.addLine("Username: ");
		t.addLine("Password: ");
		h.addChild(t);
		h.addChild(new HudInputBox(100,32,10));
		h.addChild(new HudInputBox(100,52,10));
		h.addChild(IconLoader.getIcon(25,75,"arrow_right.png"));
		h.addChild(IconLoader.getIcon(25,120,"user_add.png"));
		h.setShowTopMenu(false);
		h.setActive(true);
		h.setVisible(true);
		this.addChild(h);
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
