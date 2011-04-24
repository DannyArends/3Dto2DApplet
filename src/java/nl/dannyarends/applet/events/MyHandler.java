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

package nl.dannyarends.applet.events;

import nl.dannyarends.generic.MathUtils;
import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.genetics.QTLheatmap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import nl.dannyarends.rendering.Engine;
import nl.dannyarends.rendering.Hud;
import nl.dannyarends.rendering.RayTracer;
import nl.dannyarends.rendering.Scene;
import nl.dannyarends.rendering.objects.Point2D;
import nl.dannyarends.rendering.objects.hud.HudObject;
import nl.dannyarends.rendering.objects.renderables.Object3D;

/// MyHandler
//<p>
//Handles all events (A.t.m Mouse, Keyboard)
//</p>
//
public class MyHandler implements MouseMotionListener,KeyListener, MouseListener{
	static int mx; // the most recently recorded mouse coordinates
	static int my;
	static long lastmousemove;
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
			e.consume();
		}else{
			dragging=!(sliderinputlistener==null);
		}
		Scene.updateScene(true,true);
	}

	public void mouseReleased(MouseEvent e) {
		int c = e.getButton();
		if(c == MouseEvent.BUTTON1 && !dragging){
			if(!ButtonControler.checkLocation(mx,my)){
				Utils.console("gonna raytrace");
				Object3D o = Scene.getObjectAt(mx,my);
				if(o!=null){
//					o.setEdgeColors(new Color[]{o.getEdgeColors()[0].darker()});
//					ObjectWindow w = new ObjectWindow(100,100,250,200,o);
//					int[] t = Scene.getCurrentMap().get_tile((int)(o.location[0]), (int)(o.location[2]));
//					Scene.getCurrentMap().update_tile((int)(o.location[0]), (int)(o.location[2]), 1, (t[1]==1)?0:1);
					//if(t[1]==1)Hud.addWindow(w);
				}
				Scene.updateScene(true,true);
			}else{
				Scene.updateScene(true,false);
				e.consume();
			}
		}
	}
	
	public long getTimeSinceLastMouseMove(){
		return (System.currentTimeMillis()-lastmousemove);
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		if(Hud.isMouseOver()){
			Hud.getMouseOverWindow().setVisible(false);
			Hud.setMouseOver(false);
		}
		lastmousemove = System.currentTimeMillis();
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
			e.consume();
		}else{
			Object3D o = Scene.getObjectAt(new_mx,new_my);
			if(o!=null){
//				int[] t = Scene.getCurrentMap().get_tile((int)(o.location[0]), (int)(o.location[2]));
//				Scene.getCurrentMap().update_tile((int)(o.location[0]), (int)(o.location[2]), 0, (t[0]+10>5000)?0:t[0]+100);
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

	public void showMouseOver() {
		Hud.showMouseOver(mx,my);
	}

}
