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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import objects.Camera;
import objects.renderables.Object3D;
import objects.renderables.light.Light;
import objects.renderables.light.PointLight;


public class Scene{
	static private Camera camera = new Camera(-2.0, 2.0, -2.0, -45, 15);
	static Vector<Object3D> myobjects = new Vector<Object3D>();
	static Vector<Light> lights = new Vector<Light>();
	public static int softmyobjectslimit = 12500;
	static Hud headsupdisplay;
	private static QTLdataset dataset;
	static QTLheatmap heatmap;
	private static float renderTime;
	private static float hudTime;
	static RayTracer r;
	
	public Scene(ServerConnection s){
		headsupdisplay=new Hud(Engine.width,Engine.height);
		r= new RayTracer();
		//lights.add(new PointLight(0.0,-1.0,0.0,0.0,1.0,0.0));
		lights.add(new PointLight(-5,10.0,0));
		try{
			dataset = new QTLdataset("data/data.dat");
			heatmap = new QTLheatmap();
			headsupdisplay.addDataset(dataset);
			reDrawScene();
		}catch(Exception e){
			Utils.log("Error unable to load dataset", e);
		}
		//Scene.addObject(new Surface(50.0, -50.0, 50.0,0,0,50.0,50.0,Color.green));
		//Scene.addObject(Object3DSLoader.getModel(10,1,10, "lung_0.3ds"));
		Scene.addObject(Object3DSLoader.getModel(1,0,1, "avatar_1.3ds"));
		//Scene.addObject(Object3DSLoader.getModel(1,1,1, "avatar_2.3ds"));
		//Scene.addObject(Object3DSLoader.getModel(30,1,30, "humanoid.3ds"));
//		for(Object3D x : heatmap.getQTLObjects(dataset)){
//			Scene.addObject(x);
//		}
//		for(Object3D x : heatmap.getAnnotationObjects(dataset)){
//			Scene.addObject(x);
//		}
	}
	
	public static void reDrawScene() {
//		clearObjects();
//		Scene.addObject(new Surface(50.0, -50.0, 50.0,0,0,50.0,50.0,Color.green));
//		Scene.addObject(Object3DSLoader.getModel(10,1,10, "lung_0.3ds"));
//		Scene.addObject(Object3DSLoader.getModel(10,1,15, "avatar_1.3ds"));
//		Scene.addObject(Object3DSLoader.getModel(15,1,10, "avatar_2.3ds"));
//		Scene.addObject(Object3DSLoader.getModel(30,1,30, "humanoid.3ds"));
//		for(Object3D x : heatmap.getQTLObjects(dataset)){
//			Scene.addObject(x);
//		}
//		for(Object3D x : heatmap.getAnnotationObjects(dataset)){
//			Scene.addObject(x);
//		}
		r.update(camera);
	}
	
	public static Vector<Object3D> getObjects() {
		return myobjects;
	}
	
	public static Color getRandomColor(){
		return new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)Math.random()*255);
	}
	
	public static void updateScene() {
		if(Engine.verbose) Utils.console("Re-rendering on back buffer");
		long l1 = System.nanoTime();
		reDrawScene();
		Engine.getBackBufferGraphics().setColor(Color.black);
		Engine.getBackBufferGraphics().fillRect(0, 0, Engine.width, Engine.height);
		r.render();
		for(Object3D myobject : myobjects){
			myobject.update(camera);
			myobject.render(Engine.getBackBufferGraphics(),camera);
		}
		long l2 = System.nanoTime();
		((Graphics2D) Engine.getBackBufferGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		headsupdisplay.render((Graphics2D)Engine.getBackBufferGraphics());
		long l3 = System.nanoTime();
		setRenderTime(l2 - l1);
		setHudTime(l3 - l2);
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

	public static void setRenderTime(long renderTime) {
		Scene.renderTime = ((int)renderTime/1000000);
	}

	public static float getRenderTime() {
		return renderTime;
	}

	public static void setHudTime(long hudTime) {
		Scene.hudTime = (int)hudTime/1000000;
	}

	public static float getHudTime() {
		return hudTime;
	}

	public static double[] getBackgroundColor() {
		return new double[]{0,0,0};
	}

	public static Vector<Light> getLights() {
		return lights;
	}

	public static double[] getAmbientLight() {
		return new double[]{0.1,0.1,0.1};
	}
}
