/*
#
# WireFrame.java
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

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import rendering.Engine;
import rendering.Scene;
import events.MyHandler;
import events.ServerConnection;
import generic.RenderWindow;

public class WireFrame extends Applet implements KeyListener, MouseListener, RenderWindow{
	private static final long serialVersionUID = 1L;
	
	MyHandler eventListener= new MyHandler(this);
	ServerConnection server = new ServerConnection();
	
	public void init() {
		server.commandToServer("function=online");
		new Engine(this,server);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(eventListener);
	}


	public void update(Graphics g) {
		int p=0;
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(),getHeight());
		while(Scene.loading){
			if(Scene.loadingPercentage!=p){
				p = Scene.loadingPercentage;
				g.setColor(Color.black);
				g.fillRect(0, 0, getWidth(),getHeight());
			}
			g.setColor(Color.white);
			g.drawString(Scene.loadingMsg, getWidth()/2-100, getHeight()/2);
			g.setColor(server.getOnline() ? Color.green : Color.red);
			g.drawString("Server " + (server.getOnline() ? "connected" : "not connected"), getWidth()/2-100, getHeight()/2+20);
		}
		Scene.updateScene(true,true);
		Scene.updateGraphics(g);
	}

	public void paint(Graphics g) {
		update(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		eventListener.mouseClicked(e);
		e.consume();
	}
	@Override
	public void mouseEntered(MouseEvent e){eventListener.mouseEntered(e);}
	@Override
	public void mouseExited(MouseEvent e) {eventListener.mouseExited(e);}
	@Override
	public void mousePressed(MouseEvent e) {eventListener.mousePressed(e);}
	@Override
	public void mouseReleased(MouseEvent e){eventListener.mouseReleased(e);}
	@Override
	public void keyPressed(KeyEvent e) {
		eventListener.keyPressed(e);
		e.consume();
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {
		eventListener.keyTyped(e);
	    e.consume();
	}

}
