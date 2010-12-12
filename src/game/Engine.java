/*
#
# Engine.java
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

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Engine {
	private static Image backBuffer;
	private static Graphics backBufferGraphics;
	public static int width;
	public static int height;
	public static double near = 3.0f;
	public static double nearToObj = 2.5f;
	private static Applet parentApplet;
	
	Engine(Applet parent){
		width = parent.getSize().width;
		height = parent.getSize().height;
		backBuffer = parent.createImage(width, height);
		setBackBufferGraphics(backBuffer.getGraphics());
		parentApplet=parent;
	}

	public static Image getBackBuffer() {
		return backBuffer;
	}

	public void setBackBuffer(Image backBuffer) {
		Engine.backBuffer = backBuffer;
	}

	public void setBackBufferGraphics(Graphics backbufferGraphics) {
		Engine.backBufferGraphics = backbufferGraphics;
	}

	public static Graphics getBackBufferGraphics() {
		return backBufferGraphics;
	}

	public void setParentApplet(Applet parentApplet) {
		Engine.parentApplet = parentApplet;
	}

	public static Applet getParentApplet() {
		return parentApplet;
	}
	
	public static int getWidth() {
		return parentApplet.getSize().width;
	}
	
	public static int getHeight() {
		return parentApplet.getSize().height;
	}
}
