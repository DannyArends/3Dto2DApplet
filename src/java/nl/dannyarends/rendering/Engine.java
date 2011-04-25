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

package nl.dannyarends.rendering;

import nl.dannyarends.eventHandling.MyHandler;
import nl.dannyarends.eventHandling.MyTimer;
import nl.dannyarends.eventHandling.ServerConnection;
import nl.dannyarends.generic.RenderWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 * \brief Engine main class to provide a rendering engine<br>
 *
 * This class contains the main class to provide a rendering engine
 * bugs: none found<br>
 */
public class Engine{
	public static boolean verbose=false;
	
	private static Image backBuffer = null;
	private static Graphics backBufferGraphics = null;
	private static Dimension size;
	private static RenderWindow parent;
	private ServerConnection server = null;
	private MyHandler eventHandler;
	static MyTimer timer;
	static IconLoader iconloader;
	static TextureLoader textureloader;
	static Object3DSLoader objectloader;
	
	/**
	 * Start the Rendering engine on the specified rendering window with the given server connection
	 * 
	 * @param p - RenderWindow parent (use extend Applet or JFrame and implements RenderWindow)
	 * @param s - ServerConnection connection to a file server (used for Applets and internal transfer DWF)
	 * @param eventListener - Which event listener do we need to interrupt ?
	 * @return
	 */	
	public Engine(RenderWindow p, ServerConnection s,MyHandler eventListener){
		server = s;
		eventHandler=eventListener;
		timer= new MyTimer(s,eventHandler);
		parent=p;
		size = p.getSize();
		backBuffer = p.createImage(size.width, size.height);
		setBackBufferGraphics(backBuffer.getGraphics());
		Thread t = new Thread(new Scene(size,s,eventListener));
		t.start();
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
	
	public void setRenderWindowUpdate(boolean u) {
		timer.setRenderWindowUpdate(u);
	}

	public boolean isRenderWindowUpdate() {
		return timer.isRenderWindowUpdate();
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

	public static void setTimer(MyTimer myTimer) {
		timer = myTimer;
	}

	public static Object3DSLoader getObjectLoader() {
		return objectloader;
	}

}
