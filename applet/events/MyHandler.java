/*
#
# MyHandler.java
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

package events;

import generic.MathUtils;
import generic.RenderWindow;
import generic.Utils;
import genetics.QTLheatmap;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import objects.Point2D;
import objects.Vector3D;
import objects.hud.HudObject;
import objects.hud.windows.ObjectWindow;
import objects.renderables.Object3D;
import rendering.Engine;
import rendering.Hud;
import rendering.Intersection;
import rendering.RayTracer;
import rendering.Scene;

public class MyHandler implements MouseMotionListener,KeyListener, MouseListener{
	static int mx; // the most recently recorded mouse coordinates
	static int my;
	static HudObject keyinputlistener = null;
	static HudObject sliderinputlistener = null;
	private boolean dragging;
	private RenderWindow parent = null;
	
	public MyHandler(RenderWindow window){
		setParent(window);
	}
	
	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mouseClicked(MouseEvent e) {
		int c = e.getButton();
	    switch(c){
	    	case MouseEvent.BUTTON1:break;
	    	case MouseEvent.BUTTON2:break;
	    	case MouseEvent.BUTTON3:break;
	      }
	    sliderinputlistener=null;
	}

	public void mousePressed(MouseEvent e) {
		int c = e.getButton();
		mx = e.getX();
		my = e.getY();
		if(c == MouseEvent.BUTTON3){
			dragging=true;
		}else{
			dragging=!(sliderinputlistener==null);
		}
		Scene.updateScene(true,true);
	}

	Object3D getObjectAt(int x,int y){
		Vector3D ray = RayTracer.constructRayThroughPixel(x, y, 0, 0);
		Intersection intersection = RayTracer.findIntersection(ray, null);
		if(intersection.getPrimitive()!=null){
			return intersection.getPrimitive();
		}
		return null;
	}
	
	public void mouseReleased(MouseEvent e) {
		int c = e.getButton();
		if(c == MouseEvent.BUTTON1 && !dragging){
			if(!ButtonControler.checkLocation(mx,my)){
				Utils.console("gonna raytrace");
				Object3D o = getObjectAt(mx,my);
				if(o!=null){
					o.setEdgeColors(new Color[]{o.getEdgeColors()[0].darker()});
					ObjectWindow w = new ObjectWindow(100,100,250,200,o);
					Hud.addWindow(w);
				}
				Scene.updateScene(true,true);
			}else{
				Scene.updateScene(true,false);
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		int new_mx = e.getX();
		int new_my = e.getY();
		if(dragging){
			if(sliderinputlistener!=null){
				if(!sliderinputlistener.handleSlide(mx,my))sliderinputlistener=null ;
			}else{
				Scene.getCamera().setHorizontalRotation((int)(Scene.getCamera().getHorizontalRotation() - (new_mx - mx)));
				Scene.getCamera().setVerticalRotation((int)(Scene.getCamera().getVerticalRotation() +  (new_my - my)));
			}
			mx = new_mx;
			my = new_my;
			Scene.updateScene(false, true);
		}else{
			Object3D o = getObjectAt(new_mx,new_my);
			if(o!=null){
				o.setEdgeColors(new Color[]{o.getEdgeColors()[0].darker()});
			}
			Scene.updateScene(false, true);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
	    switch(c){
	    	case KeyEvent.VK_UP:Utils.console("UP");Scene.getCamera().move(RayTracer.direction);break;
	    	case KeyEvent.VK_DOWN:Utils.console("DOWN");Scene.getCamera().move(MathUtils.oppositeVector(RayTracer.direction));break;
	    	case KeyEvent.VK_LEFT:Utils.console("LEFT");Scene.getCamera().move(MathUtils.oppositeVector(RayTracer.rightDirection));break;
	    	case KeyEvent.VK_RIGHT:Utils.console("RIGHT");Scene.getCamera().move(RayTracer.rightDirection);break;
	    	case KeyEvent.VK_PAGE_UP:Utils.console("UP");Scene.getCamera().move(0,1,0);break;
	    	case KeyEvent.VK_PAGE_DOWN:Utils.console("DOWN");Scene.getCamera().move(0,-1,0);break;
	    	case KeyEvent.VK_ESCAPE:System.exit(0);break;
	    	case KeyEvent.VK_M:Utils.console("Modelonly");QTLheatmap.setModelonly(!QTLheatmap.isModelonly());Scene.updateScene(false,true);break;
	    	case KeyEvent.VK_EQUALS:Utils.console("+");QTLheatmap.increaseCutoff();Scene.updateScene(false,true);break;
	    	case KeyEvent.VK_MINUS:Utils.console("-");QTLheatmap.decreaseCutoff();Scene.updateScene(false,true);break;
	      }
	}

	public void keyTyped(KeyEvent e) {
	      if(keyinputlistener!=null)keyinputlistener.handleKeystroke(e);
	}

	public static void registerForKeystrokes(HudObject b) {
		if(Engine.verbose) Utils.console("Object registering for keystrokes");
		keyinputlistener = b;
	}

	public static void registerForSlide(HudObject b) {
		if(Engine.verbose) Utils.console("Object registering for mouse slides");
		sliderinputlistener=b;
	}

	public static Point2D getMouse() {
		Point2D p = new Point2D(mx,my);
		return p;
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void setParent(RenderWindow parent) {
		this.parent = parent;
	}

	public RenderWindow getParent() {
		return parent;
	}

}
