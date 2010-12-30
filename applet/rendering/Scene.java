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

import generic.Utils;
import genetics.QTLdataset;
import genetics.QTLheatmap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import objects.Camera;
import objects.renderables.Object3D;
import objects.renderables.light.Light;
import objects.renderables.light.PointLight;


public class Scene{
	static private Dimension size = new Dimension(10,10);
	static private Camera camera = new Camera(-4.0, 2.0, -4.0, 0, 0);
	static Vector<Object3D> myobjects = new Vector<Object3D>();
	static Vector<Light> lights = new Vector<Light>();
	public static int softmyobjectslimit = 12500;
	static Hud headsupdisplay;
	private static QTLdataset dataset;
	static QTLheatmap heatmap;
	private static float renderTime;
	private static float hudTime;
	static private double[] backgroundColor = new double[]{0.0, 0.0, 0.0};
	private static boolean render_2d = true;
	private static boolean render_3d = true;
	static RayTracer r = null;
	static int grainedness=150;
	
	public Scene(Engine p, Dimension s){
		size=s;
		headsupdisplay=new Hud(size.width, size.height);
		lights.add(new PointLight(4.0, 0.0, -5.0, 0.0, 1.0, 1.0));
		lights.add(new PointLight(-5.0, 0.0, 5.0, 1.0, 0.0, 0.0));
		//lights.add(new PointLight(0,-5.0,0,0.1,0.1,1.0));
		try{
			dataset = new QTLdataset("data/data.dat");
			heatmap = new QTLheatmap();
			headsupdisplay.addDataset(dataset);
			reDraw3DScene();
		}catch(Exception e){
			Utils.log("Error unable to load dataset", e);
		}
		//Scene.addObject(new Surface(50.0, -50.0, 50.0,0,0,50.0,50.0,Color.green));
		Object3D i = Object3DSLoader.getModel(1,1,1, "lung_0.3ds");
		i.setTransparant(0.4);
		Scene.addObject(i);
		Scene.addObject(Object3DSLoader.getModel(0,0,0, "avatar_1.3ds"));
		Scene.addObject(Object3DSLoader.getModel(5,0,0, "lung_0.3ds"));
		Scene.addObject(Object3DSLoader.getModel(0,0,15, "humanoid.3ds"));
		Scene.addObject(Object3DSLoader.getModel(5,1,1, "avatar_2.3ds"));
		Scene.addObject(Object3DSLoader.getModel(1,1,5, "humanoid.3ds"));
		Scene.addObject(Object3DSLoader.getModel(1,3,5, "humanoid.3ds"));
//		for(Object3D x : heatmap.getQTLObjects(dataset)){
//			Scene.addObject(x);
//		}
//		for(Object3D x : heatmap.getAnnotationObjects(dataset)){
//			Scene.addObject(x);
//		}
		r= new RayTracer();
	}
	
	public static void reDraw3DScene() {
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
		if(r!= null){
			r.update(camera);
			//for(Object3D myobject : myobjects){
			//	myobject.update(camera);
				//if(!myobject.isLoaded()) myobject.TryLoadingFromName();
			//}
		}
	}
	
	public static Vector<Object3D> getObjects() {
		return myobjects;
	}
	
	public static void updateScene(boolean redraw2d, boolean redraw3d) {
		if(Engine.verbose) Utils.console("Re-rendering on back buffer");
		if(Engine.getBackBufferGraphics()==null){
			Utils.log("No BackBuffer Yet ;)",System.err); return;
		}
		if(redraw3d) reDraw3DScene();
		long l1 = System.nanoTime();
		if(!redraw2d && !redraw3d && grainedness > 0){
			grainedness-=3;
		}else{
			grainedness=150;
			Engine.getBackBufferGraphics().setColor(Color.black);
			Engine.getBackBufferGraphics().fillRect(0, 0, size.width, size.height);
		}
		if(render_3d && r != null){
			r.render();
			//for(Object3D myobject : myobjects){
			//	myobject.render(Engine.getBackBufferGraphics(),camera);
			//}
		}
		long l2 = System.nanoTime();
		if(render_2d && headsupdisplay != null){
				((Graphics2D) Engine.getBackBufferGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
				headsupdisplay.render((Graphics2D)Engine.getBackBufferGraphics());
		}
		long l3 = System.nanoTime();
		setRenderTime(l2 - l1);
		setHudTime(l3 - l2);
		if(Engine.verbose) Utils.console("DONE Re-rendering on back buffer");
		updateGraphics(Engine.getRenderWindow().getGraphics());
	}
	
	public static void addObject(Object3D o){
		myobjects.add(o);
	}
	
	public static void clearObjects(){
		myobjects.clear();
	}
	
	public static void updateGraphics(Graphics g) {
		if(Engine.verbose) Utils.console("Back buffer to front buffer");
		if(Engine.getBackBuffer()!=null && Engine.getRenderWindow() != null){
			g.drawImage(Engine.getBackBuffer(), 0, 0,size.width,size.height, Engine.getRenderWindow());
			Engine.getRenderWindow().showStatus("Hrot: " + camera.getHorizontalRotation() + " deg, Vrot: " + camera.getVerticalRotation() + " deg");
		}else{
			Utils.log("No backbuffer created yet", System.err);
		}
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
		return backgroundColor;
	}

	public static Vector<Light> getLights() {
		return lights;
	}

	public static double[] getAmbientLight() {
		return new double[]{0.1,0.1,0.1};
	}
}
