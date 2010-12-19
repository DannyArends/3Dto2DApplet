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
import genetics.QTLdataset;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Vector;

import objects.hud.Button2D;
import objects.hud.HudWindow;
import objects.hud.Object2D;
import objects.hud.Slider;

public class Hud {
	QTLdataset dataset;
	static private boolean printHelp=false;
	static private boolean printControls=false;
	static private boolean printAbout=false;
	
	static Vector<Object2D> children = new Vector<Object2D>();
	private ButtonControler buttonarray = new ButtonControler();
	
	static Font[] fonts = new Font[]{
		new Font("Dialog", Font.BOLD,  12),
		new Font("Dialog", Font.PLAIN, 10)
	};
	
	public Hud(){
		addtestWindow();
	}
	
	public void addDataset(QTLdataset d){
		dataset=d;
	}
	
	public void addtestWindow(){
		HudWindow h = new HudWindow(300,300,400,200,"TestWindow");
		new Button2D(10,0,"HOI",h);
		new Slider(100,25,h);
		new Slider(100,40,h);
		new Slider(100,55,h);
		new Slider(100,70,h);
		new Slider(100,85,h);
		children.add(h);
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
		int l = 0;
		drawBox(g,5,36,250,80,Color.gray);
		g.setColor(Color.white);
		g.setFont(fonts[0]);
		drawString(g,"--Genetic Landscapes--", 15, 36);
		g.setFont(fonts[1]);
		drawString(g,"Press 'H' for Help", 15, 48);
		drawString(g,"Press 'C' for Controls", 15, 60);
		drawString(g,"Press 'A' for About", 15, 72);
		if(dataset!=null) l = dataset.DatasetInfo(g,Engine.width-200,84);
		if(printHelp) l= doPrintHelp(g,Engine.width-200,l);
		if(printControls) l= doPrintControls(g,l);
		if(printAbout) l= doPrintAbout(g,l);
		for(Object2D o : children){
			o.render(g);
		}
		buttonarray.render(g);
		
	}

	public static void setPrintHelp(boolean p) {
		printHelp = p;
	}

	public static boolean isPrintHelp() {
		return printHelp;
	}

	public static boolean isPrintControls() {
		return printControls;
	}

	public static boolean isPrintAbout() {
		return printAbout;
	}
	
	public static void addObject2D(Object2D b){
		children.add(b);
	}

	public int doPrintHelp(Graphics2D g,int x, int y) {
		drawBox(g,x,y,250,72,Color.gray);
		g.setFont(fonts[0]);
		drawString(g,"--HELP--", x, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Open triangles: QTL at marker", x, y+36);
		drawString(g,"Filled triangles: Selected Cofactor at marker", x, y+48);
		drawString(g,"Triangle size: QTL effect/likelihood", x, y+60);
		return y+72;
	}

	public static void setPrintControls(boolean p) {
		printControls = p;
	}

	public int doPrintControls(Graphics2D g, int y) {
		drawBox(g,5,y,250,132,Color.gray);
		g.setFont(fonts[0]);
		drawString(g,"--CONTROLS--", 10, y+12);
		g.setFont(fonts[1]);
		drawString(g,"Click and move mouse to look around", 10, y+36);
		drawString(g,"[Left]       step left", 10, y+48);
		drawString(g,"[Right]      step right", 10, y+60);
		drawString(g,"[Down]       step back", 10, y+72);
		drawString(g,"[Up]         step forward ", 10, y+84);
		drawString(g,"[Page Up]    float up", 10, y+96);
		drawString(g,"[M]          Toggle between model only view", 10, y+108);
		drawString(g,"[+]/[-]      Increade/Decrease LOD score Cut-off", 10, y+120);
		return y+132;
	}

	public static void setPrintAbout(boolean p) {
		printAbout = p;
	}

	public int doPrintAbout(Graphics2D g, int y) {
		drawBox(g,5,y,250,84,Color.gray);
		g.setFont(fonts[0]);
		drawString(g,"--ABOUT--", 10, y+12);
		g.setFont(fonts[1]);
		drawString(g,"QTL viewing applet", 10, y+36);
		drawString(g,"Part of the iqtl package", 10, y+48);
		drawString(g,"(c) 2010 Danny Arends - GBIC", 10, y+60);
		drawString(g,"https://github.com/DannyArends/3Dto2DApplet", 10, y+72);
		return y+84;
	}
}
