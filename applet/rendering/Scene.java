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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import objects.Camera;
import objects.Texture;
import objects.hud.windows.ObjectWindow;
import objects.renderables.Object3D;
import objects.renderables.Sphere;
import objects.renderables.Surface;
import objects.renderables.light.Light;
import objects.renderables.light.PointLight;
import events.ServerConnection;
import game.GameMap;
import generic.Utils;
import genetics.QTLdataset;
import genetics.QTLheatmap;

/// A 'renderable' moment/state of the engine
//<p>
//TODO
//</p>
//

public class Scene implements Runnable{
	static private Dimension size = new Dimension(10,10);
	private ServerConnection server;
	private static int softmyobjectslimit = 12500;
	private static Hud headsupdisplay;
	private static QTLdataset dataset;
	private static QTLheatmap heatmap;
	private static float renderTime;
	private static float hudTime;
	private static  double[] backgroundColor = new double[]{0.0, 0.0, 0.0};
	public static boolean loading = true;
	public static int loadingPercentage = 0;
	public static String loadingMsg = "";
	public static boolean render_2d = true;
	public static boolean render_3d = true;
	
	private static RayTracer raytracer = new RayTracer();
	private static ArrayList<Object3D> myobjects = new ArrayList<Object3D>();
	private static ArrayList<Light> lights = new ArrayList<Light>();
	private static Camera camera = new Camera(-10.0, 10.0, -10.0, -35, 15);
	
	public Scene(Dimension dim, ServerConnection s){
		size=dim;
		server = s;
	}
	
	/**
	 * Loading Thread starts the Timer thread when done loading
	 * 
	 * @return
	 */	
	@Override
	public void run() {
		UpdateLoading(0,"Querying server");
		Engine.setIconLoader(new IconLoader(server));
		Engine.setTextureLoader( new TextureLoader(server));
		Engine.setObjectLoader(new Object3DSLoader(server));
		UpdateLoading(10,"Loading HUD");
		headsupdisplay = new Hud(size.width, size.height);
		UpdateLoading(30,"Adding Lights");
		lights.add(new PointLight(25.0,  10.0, 0.0, 1, 0, 0));
		lights.add(new PointLight(0.0, 10.0, 25.0, 1, 1.0, 1.0));
		lights.add(new PointLight(100.0, 10.0, 100.0, 1, 1.0, 1.0));
		UpdateLoading(50,"Loading Dataset");
		//loadBasicDataSet();
		UpdateLoading(70,"Loading Scene");
		loadBasicSceneFromServer();
		UpdateLoading(80,"PreComputing Object statistics");
		PreComputeLoadedObjects();
		UpdateLoading(90,"Starting rendering");
		Engine.setTimer(new MyTimer(server));
		loading=false;
	}
	
	/**
	 * Update the loading window
	 * 
	 * @param percentage percentage done
	 * @param m loading message
	 * @return
	 */	
	void UpdateLoading(int percentage,String m){
		loadingPercentage = percentage;
		loadingMsg = m;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void loadBasicDataSet(){
		try{
			dataset = new QTLdataset("data/data.dat");
			heatmap = new QTLheatmap();
			headsupdisplay.addDataset(dataset);
			int cnt=0;
			for(Object3D x : heatmap.getQTLObjects(dataset)){
				Scene.addObject(x);
				cnt++;
			}
			for(Object3D x : heatmap.getAnnotationObjects(dataset)){
				Scene.addObject(x);
			}
		}catch(Exception e){
			Utils.log("Error unable to load dataset", e);
		}
	}
		
	static void loadBasicScene(){	
		Texture one =  TextureLoader.getTexture("Ground_0.bmp");
		Texture two =  TextureLoader.getTexture("Sky_0.bmp");
		Object3D sur = new Surface(50.0, 0.0, 50.0,0,0,50.0,50.0,Color.green);
		sur.setTransparant(0.0);
		sur.setTexture(one);
		Scene.addObject(sur);
		Hud.addWindow(new ObjectWindow(100,100,350,200,sur));
		Sphere sph = new Sphere(25.0, 4.0, 25.0,2.0);
		sph.setTransparant(0.0);
		sph.setTexture(two);
		Scene.addObject(sph);
	}
	
	void loadBasicSceneFromServer(){	
		GameMap start = new GameMap(server,"Danny");
		for(Object3D x : start.getObject3D()){
			Scene.addObject(x);
		}
	}
		
	static void PreComputeLoadedObjects(){
		raytracer.update(camera);
		for(Object3D myobject : myobjects){
			myobject.update(camera);
			if(!myobject.isLoaded()) myobject.TryLoadingFromName();
		}
	}
	
	public static void reDraw3DScene() {
		//clearObjects();
		//loadBasicScene();
		PreComputeLoadedObjects();
	}
	
	public static ArrayList<Object3D> getObjects() {
		return myobjects;
	}
	
	/**
	 * Update the scene
	 * 
	 * @param redraw2d Sometimes the user changes 2D
	 * @param redraw3d Sometimes the user changes 3D
	 * @return
	 */	
	public static void updateScene(boolean redraw2d, boolean redraw3d) {
		if(Engine.verbose) Utils.console("Re-rendering on back buffer");
		if(Engine.getBackBufferGraphics()==null){
			Utils.log("No BackBuffer Yet ;)",System.err); return;
		}
		if(redraw3d) reDraw3DScene();
		long l1 = System.nanoTime();
		if(!redraw2d && !redraw3d){
		}else{
			Engine.getBackBufferGraphics().setColor(Color.black);
			Engine.getBackBufferGraphics().fillRect(0, 0, size.width, size.height);
			for(Object3D myobject : myobjects){
				myobject.bufferMyObject();
				myobject.render(Engine.getBackBufferGraphics(),camera);
			}
		}
		if(render_3d && raytracer != null){
			//raytracer.render();
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
	
	/**
	 * Copies the graphical back buffer to the graphics buffer
	 * 
	 */	
	public static void updateGraphics(Graphics g) {
		if(Engine.verbose) Utils.console("Back buffer to front buffer");
		if(Engine.getBackBuffer()!=null && Engine.getRenderWindow() != null){
			g.drawImage(Engine.getBackBuffer(), 0, 0,size.width, size.height, Engine.getRenderWindow());
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

	public static ArrayList<Light> getLights() {
		return lights;
	}

	public static double[] getAmbientLight() {
		return new double[]{0.3,0.3,0.3};
	}

	public static void setSoftmyobjectslimit(int softmyobjectslimit) {
		Scene.softmyobjectslimit = softmyobjectslimit;
	}

	public static int getSoftmyobjectslimit() {
		return softmyobjectslimit;
	}

}
