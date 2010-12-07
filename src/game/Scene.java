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

package game;

import genetics.QTLdataset;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import objects.Camera;
import objects.Object3D;


public class Scene extends Engine{
	static private Camera camera = new Camera(0.0, 20.0, 0.0, -45, 15);
	static Vector<Object3D> myobjects = new Vector<Object3D>();
	
	public Scene(Applet parent){
		super(parent);
	}
	
	public static void updateScene(QTLdataset dataset) {
		//Utils.console("Updating Scene");
		Scene.getBackBufferGraphics().setColor(Color.black);
		Scene.getBackBufferGraphics().fillRect(0, 0, Engine.width, Engine.height);
		for(Object3D myobject : myobjects){
			myobject.update(camera);
			myobject.render(getBackBufferGraphics(),camera);
		}
		Scene.getBackBufferGraphics().setColor(Color.white);
		Scene.getBackBufferGraphics().drawString("Traits: " + dataset.ntraits, 10, 36);
		Scene.getBackBufferGraphics().drawString("Chromosomes: " + dataset.nchromosomes, 10, 48);
	    String distances = "Lengths: ";
	    for (int c = 0; c < dataset.nchromosomes; c++) {
	      distances += dataset.chrlengths[c] + " ";
	    }
	    Scene.getBackBufferGraphics().drawString(distances, 10, 60);
	    Scene.getBackBufferGraphics().drawString("Markers: " + dataset.nmarkers, 10, 72);
		updateGraphics(getParentApplet().getGraphics());
	}
	
	public static void addObject(Object3D o){
		myobjects.add(o);
	}
	
	public static void updateGraphics(Graphics g) {
		//Utils.console("Updating Graphics");
		g.drawImage(getBackBuffer(), 5, 5,Scene.width-10,Scene.height-10, getParentApplet());
		getParentApplet().showStatus("Hrot: " + camera.getHorizontalRotation() + " deg, Vrot: " + camera.getVerticalRotation() + " deg");
	}
	
	public static Camera getCamera() {
		return camera;
	}

	public static void setCamera(Camera camera) {
		Scene.camera = camera;
	}
}
