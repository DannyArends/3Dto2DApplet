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

import events.ServerConnection;
import generic.RenderWindow;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Engine{
	public static boolean verbose=false;
	
	private static Image backBuffer = null;
	private static Graphics backBufferGraphics = null;
	private static Dimension size;
	private static RenderWindow parent;
	
	static MyTimer timer;
	Scene scene;
	static IconLoader iconloader;
	static TextureLoader textureloader;
	static Object3DSLoader objectloader;
	
	/**
	 * Start the Rendering engine on the specified rendering window with the given server connection
	 * 
	 * @param p RenderWindow parent (use extend Applet or JFrame and implements RenderWindow)
	 * @param s ServerConnection connection to a file server (used for Applets and internal transfer DWF)
	 * @return
	 */	
	public Engine(RenderWindow p, ServerConnection s){
		parent=p;
		size = p.getSize();
		backBuffer = p.createImage(size.width, size.height);
		setBackBufferGraphics(backBuffer.getGraphics());
		Thread t = new Thread(new Scene(size,s));
		t.start();
	}

	public static Image getBackBuffer() {
		return backBuffer;
	}

	public void setBackBuffer(Image bb) {
		backBuffer = bb;
	}

	public void setBackBufferGraphics(Graphics bbG) {
		backBufferGraphics = bbG;
	}

	public static Graphics getBackBufferGraphics() {
		return backBufferGraphics;
	}

	public void setRenderWindow(RenderWindow p) {
		parent = p;
	}

	public static RenderWindow getRenderWindow() {
		return parent;
	}
	
	public static String getAppletURL(){
		return (parent.getDocumentBase().toExternalForm().substring(0,parent.getDocumentBase().toExternalForm().lastIndexOf('/'))+"/");
	}
	
	public static int getWidth() {
		return size.width;
	}
	
	public static int getHeight() {
		return size.height;
	}

	public static void setIconLoader(IconLoader i) {
		iconloader=i;
		
	}

	public static void setTextureLoader(TextureLoader t) {
		textureloader=t;
		
	}

	public static void setObjectLoader(Object3DSLoader o) {
		objectloader = o;
		
	}

	public static void setTimer(MyTimer t) {
		timer=t;
	}
}
