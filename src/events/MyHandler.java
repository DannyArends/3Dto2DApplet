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

import game.Hud;
import game.Scene;
import generic.Utils;
import genetics.QTLheatmap;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MyHandler implements MouseMotionListener{
	int mx, my; // the most recently recorded mouse coordinates
	
	public MyHandler(){

	}
	
	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mouseClicked(MouseEvent e) {
		int c = e.getButton();
	    switch(c){
	    	case MouseEvent.BUTTON1:Utils.console("M1");break;
	    	case MouseEvent.BUTTON2:Utils.console("M2");break;
	    	case MouseEvent.BUTTON3:Utils.console("M3");ButtonControler.rightClickMenu(e.getPoint().x,e.getPoint().y);break;
	      }
	}

	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		ButtonControler.checkLocation(mx,my);
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		// get the latest mouse position
		int new_mx = e.getX();
		int new_my = e.getY();

		// since the last event
		Scene.getCamera().setHorizontalRotation(Scene.getCamera().getHorizontalRotation() - (new_mx - mx));
		Scene.getCamera().setVerticalRotation(Scene.getCamera().getVerticalRotation() + (new_my - my));
		// update our data
		mx = new_mx;
		my = new_my;

		Scene.getParentApplet().repaint();
		e.consume();
	}
	
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
	    switch(c){
	    	case KeyEvent.VK_UP:Utils.console("UP");Scene.getCamera().move(1,0,0);break;
	    	case KeyEvent.VK_DOWN:Utils.console("DOWN");Scene.getCamera().move(-1,0,0);break;
	    	case KeyEvent.VK_LEFT:Utils.console("LEFT");Scene.getCamera().move(0,0,1);break;
	    	case KeyEvent.VK_RIGHT:Utils.console("RIGHT");Scene.getCamera().move(0,0,-1);break;
	    	case KeyEvent.VK_PAGE_UP:Utils.console("UP");Scene.getCamera().move(0,1,0);break;
	    	case KeyEvent.VK_PAGE_DOWN:Utils.console("DOWN");Scene.getCamera().move(0,-1,0);break;
	    	case KeyEvent.VK_ESCAPE:System.exit(0);break;
	    	case KeyEvent.VK_H:Utils.console("Help");Hud.setPrintHelp(!Hud.isPrintHelp());break;
	    	case KeyEvent.VK_A:Utils.console("About");Hud.setPrintAbout(!Hud.isPrintAbout());break;
	    	case KeyEvent.VK_C:Utils.console("Controls");Hud.setPrintControls(!Hud.isPrintControls());break;
	    	case KeyEvent.VK_M:Utils.console("Modelonly");QTLheatmap.setModelonly(!QTLheatmap.isModelonly());Scene.reDrawScene();break;
	    	case KeyEvent.VK_EQUALS:Utils.console("+");QTLheatmap.increaseCutoff();Scene.reDrawScene();break;
	    	case KeyEvent.VK_MINUS:Utils.console("-");QTLheatmap.decreaseCutoff();Scene.reDrawScene();break;
	      }
		Scene.getParentApplet().repaint();
	}

	public void keyTyped(KeyEvent e) {
	      char c = e.getKeyChar();
	      if(c != KeyEvent.CHAR_UNDEFINED ) {
	         //System.out.print(c+"\n");
		  }
	}

}
