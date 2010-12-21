/*
#
# Scene.java
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

import events.ServerConnection;
import generic.Utils;
import genetics.QTLdataset;
import genetics.QTLheatmap;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import objects.Camera;
import objects.renderables.Object3D;


public class Scene{
	static private Camera camera = new Camera(0.0, 20.0, 0.0, -45, 15);
	static Vector<Object3D> myobjects = new Vector<Object3D>();
	public static int softmyobjectslimit = 12500;
	static Hud headsupdisplay;
	private static QTLdataset dataset;
	static QTLheatmap heatmap;
	
	public Scene(ServerConnection s){
		headsupdisplay=new Hud(Engine.width,Engine.height);
		try{
			dataset = new QTLdataset("data/data.dat");
			heatmap = new QTLheatmap();
			headsupdisplay.addDataset(dataset);
			reDrawScene();
		}catch(Exception e){
			Utils.log("Error unable to load dataset", e);
		}
	}
	
	public static void reDrawScene() {
		clearObjects();
		for(Object3D x : heatmap.getQTLObjects(dataset)){
			Scene.addObject(x);
		}
		for(Object3D x : heatmap.getAnnotationObjects(dataset)){
			Scene.addObject(x);
		}
	}
	
	public static void updateScene() {
		if(Engine.verbose) Utils.console("Re-rendering on back buffer");
		Engine.getBackBufferGraphics().setColor(Color.black);
		Engine.getBackBufferGraphics().fillRect(0, 0, Engine.width, Engine.height);
		for(Object3D myobject : myobjects){
			myobject.update(camera);
			myobject.render(Engine.getBackBufferGraphics(),camera);
		}
		((Graphics2D) Engine.getBackBufferGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		headsupdisplay.render((Graphics2D)Engine.getBackBufferGraphics());
		if(Engine.verbose) Utils.console("DONE Re-rendering on back buffer");
		updateGraphics(Engine.getParentApplet().getGraphics());
	}
	
	public static void addObject(Object3D o){
		myobjects.add(o);
	}
	
	public static void clearObjects(){
		myobjects.clear();
	}
	
	public static void updateGraphics(Graphics g) {
		if(Engine.verbose) Utils.console("Back buffer to front buffer");
		g.drawImage(Engine.getBackBuffer(), 0, 0,Engine.width,Engine.height, Engine.getParentApplet());
		Engine.getParentApplet().showStatus("Hrot: " + camera.getHorizontalRotation() + " deg, Vrot: " + camera.getVerticalRotation() + " deg");
	}
	
	public static Camera getCamera() {
		return camera;
	}

	public static void setCamera(Camera camera) {
		Scene.camera = camera;
	}
}
