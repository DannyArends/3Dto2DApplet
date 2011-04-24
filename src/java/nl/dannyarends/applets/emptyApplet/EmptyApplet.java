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
package nl.dannyarends.applets.emptyApplet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import nl.dannyarends.generic.RenderWindow;

public class EmptyApplet extends Applet implements KeyListener, MouseListener, RenderWindow{
	private static final long serialVersionUID = 1L;
	
	public void init() {

	}


	public void update(Graphics g) {

	}

	public void paint(Graphics g) {
		update(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		e.consume();
	}
	
	@Override
	public void mouseEntered(MouseEvent e){}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e){}
	
	@Override
	public void keyPressed(KeyEvent e) {
		e.consume();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		e.consume();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	    e.consume();
	}
}
