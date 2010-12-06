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
