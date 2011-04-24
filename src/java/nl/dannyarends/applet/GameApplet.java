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
package nl.dannyarends.applet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.dannyarends.applet.events.MyHandler;
import nl.dannyarends.applet.events.ServerConnection;
import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.options.ClientOptions;
import nl.dannyarends.options.OptionsPackage;
import nl.dannyarends.options.OptionsParser;
import nl.dannyarends.rendering.Engine;

public class GameApplet extends Applet implements KeyListener, MouseListener, RenderWindow{
	private static final long serialVersionUID = 1L;
	
	ServerConnection server = new ServerConnection("Applet");
	MyHandler eventListener= new MyHandler(this);
	static ClientOptions clientOptions;
	static OptionsParser optionsParser;
	Engine engine;
	
	public void init() {
		server.commandToServer("function=online");
		clientOptions = new ClientOptions("http://localhost/dist/data/client.properties");
		optionsParser = new OptionsParser((OptionsPackage) clientOptions);
		engine = new Engine(this,server,eventListener);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(eventListener);
	}


	public void update(Graphics g) {
		engine.update(g);
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
