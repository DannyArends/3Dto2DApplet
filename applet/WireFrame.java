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
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import rendering.Engine;
import rendering.Scene;
import events.MyHandler;
import events.ServerConnection;

public class WireFrame extends Applet implements KeyListener, MouseListener{
	private static final long serialVersionUID = 1L;
	
	MyHandler eventListener= new MyHandler();
	ServerConnection s = new ServerConnection();
	
	public void init() {
		s.commandToServer("online=true");
		new Engine(this,s);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(eventListener);
		Scene.updateScene();
	}


	public void update(Graphics g) {
		Scene.updateGraphics(g);
		Scene.updateScene();
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
