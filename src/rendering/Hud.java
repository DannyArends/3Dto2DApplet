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
//		addtestWindow();
		addHelpWindow(100, 100);
		addControlsWindow(100, 100);
		addAboutWindow(100, 100);
		addIconBar(0,Engine.height-100);
	}
	
	public void addDataset(QTLdataset d){
		dataset=d;
	}
	
//	public void addtestWindow(){
//		HudWindow h = new HudWindow(300,300,400,200,"TestWindow");
//		new Button2D(10,0,"HOI",h);
//		new Slider(100,25,h);
//		new Slider(100,40,h);
//		new Slider(100,55,h);
//		new Slider(100,70,h);
//		new Slider(100,85,h);
//		new InputBox(10,100,10,h);
//		children.add(h);
//	}
	
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
		HudWindow a=null;
		for(HudWindow o : getHudWindows()){
			if(!o.isActive()){
				o.render(g);
			}else{
				a = o;
			}
		}
		if(a!=null)a.render(g);
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
			h.setActive(false);
		}
	}

	public void addHelpWindow(int x, int y) {
		HudWindow h = new HudWindow(x,y,400,100,"HELP");
		h.setVisible(false);
		h.addChild(new HudText(10,10,"Open Triangle: QTL at marker"));
		h.addChild(new HudText(10,25,"Filled triangles: Selected Cofactor at marker"));
		h.addChild(new HudText(10,40,"Triangle size: QTL effect/likelihood"));
		h.addChild(new HudImage(300,10,"help.png"));
		this.addChild(h);
	}

	public void addControlsWindow(int x, int y) {
		HudWindow h = new HudWindow(x,y,400,200,"CONTROLS");
		h.setVisible(false);
		h.addChild(new HudText(10,10,"Click and move mouse to look around"));
		h.addChild(new HudText(10,25,"[Left]       step left"));
		h.addChild(new HudText(10,40,"[Right]      step right"));
		h.addChild(new HudText(10,55,"[Down]       step back"));
		h.addChild(new HudText(10,70,"[Up]         step forward"));
		h.addChild(new HudText(10,85,"[Page Up]    float up"));
		h.addChild(new HudText(10,100,"[Page Up]    float up"));
		h.addChild(new HudText(10,115,"[M]          Toggle between model only view"));
		h.addChild(new HudText(10,130,"[+]/[-]      Increade/Decrease LOD score Cut-off"));
		this.addChild(h);
	}

	public void addAboutWindow(int x, int y) {
		HudWindow h = new HudWindow(x,y,400,100,"ABOUT");
		h.setVisible(false);
		h.addChild(new HudText(10,10,"QTL viewing applet"));
		h.addChild(new HudText(10,25,"Part of the iqtl package"));
		h.addChild(new HudText(10,40,"(c) 2010 Danny Arends - GBIC"));
		h.addChild(new HudText(10,55,"https://github.com/DannyArends/3Dto2DApplet"));
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
}
