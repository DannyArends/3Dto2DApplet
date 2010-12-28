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

package rendering;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

import events.ServerConnection;

public class Engine {
	private static Image backBuffer;
	private static Graphics backBufferGraphics;
	public static boolean verbose=false;
	public static int width;
	public static int height;
	public static double near = 3.0f;
	public static double nearToObj = 1.0f;
	private static Applet parentApplet;
	MyTimer timer;
	IconLoader iconloader;
	Object3DSLoader objectloader;
	
	public Engine(Applet parent, ServerConnection s){
		parentApplet=parent;
		width = parent.getSize().width;
		height = parent.getSize().height;
		backBuffer = parent.createImage(width, height);
		setBackBufferGraphics(backBuffer.getGraphics());
		//timer = new MyTimer(s);
		iconloader = new IconLoader(s);
		objectloader = new Object3DSLoader(s);
		new Scene(s);
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
	
	public static String getAppletURL(){
		return (Engine.getParentApplet().getDocumentBase().toExternalForm().substring(0,Engine.getParentApplet().getDocumentBase().toExternalForm().lastIndexOf('/'))+"/");
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
}
