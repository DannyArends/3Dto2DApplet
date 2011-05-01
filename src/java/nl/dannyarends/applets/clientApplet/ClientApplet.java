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

package nl.dannyarends.applets.clientApplet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.dannyarends.eventHandling.EventHandler;
import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.rendering.Engine;


public class ClientApplet extends Applet implements KeyListener, MouseListener, RenderWindow{
	private static final long serialVersionUID = -2934477801450325416L;
	Engine engine;
	EventHandler handler;
	
	public void init() {
		System.out.println("Applet started");
    addKeyListener(this);
    addMouseListener(this);
    engine = new Engine(this);
    handler = engine.getEventHandler();
    new Thread(engine).start();
	}

	

	public void update(Graphics g) {
	  engine.render((Graphics2D)g);
	}

	public void paint(Graphics g) {
		update(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	  handler.mouseClicked(e);
		e.consume();
	}
	
	@Override
	public void mouseEntered(MouseEvent e){handler.mouseEntered(e);}
	
	@Override
	public void mouseExited(MouseEvent e) {handler.mouseExited(e);}
	
	@Override
	public void mousePressed(MouseEvent e) {handler.mousePressed(e);}
	
	@Override
	public void mouseReleased(MouseEvent e){handler.mouseReleased(e);}
	
	@Override
	public void keyPressed(KeyEvent e) {
	  handler.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	  handler.keyReleased(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	   handler.keyTyped(e);
	}
}
