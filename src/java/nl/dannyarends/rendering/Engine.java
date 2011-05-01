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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import nl.dannyarends.eventHandling.EventHandler;
import nl.dannyarends.eventHandling.KeyBoardHandler;
import nl.dannyarends.eventHandling.MouseHandler;
import nl.dannyarends.eventHandling.NetworkHandler;
import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.interfaces.Renderable;

/**
 * \brief Engine main class to provide a rendering engine<br>
 *
 * This class contains the main class to provide a rendering engine
 * bugs: none found<br>
 */
public class Engine implements Renderable,Runnable{
	private RenderWindow window;
	private HUD hud;
	private Scene scene;
	private EventHandler eventhandler;
	private boolean rendering = true;

	private Image backBuffer = null;
	private Graphics backBufferGraphics = null;
	
	public int stats_scene_time;
	public int stats_hud_time;
	/**
	 * Start the Rendering engine on the specified rendering window with the given server connection
	 * 
	 * @param window - RenderWindow parent (use extend Applet or JFrame and implements RenderWindow)
	 * @return
	 */	
	public Engine(RenderWindow w){
	  setWindow(w);
	  
	  setScene(new Scene(window,this));
	  setHud(new HUD(window,this,scene));
	  
	  backBuffer = w.createImage((int)w.getSize().getWidth(), (int)w.getSize().getHeight());
	  setBackBufferGraphics(backBuffer.getGraphics());
	  
    new Thread(scene).start();
    new Thread(hud).start();
	}
	
  @Override
  public void run() {
    while(rendering){
      long l1 = System.nanoTime();
      scene.render(getBackBufferGraphics());
      long l2 = System.nanoTime();
      hud.render(getBackBufferGraphics());
      long l3 = System.nanoTime();
      stats_scene_time = (int) ((l2 - l1)/1000000);
      stats_hud_time = (int) ((l3 - l2)/1000000);
      updateGraphics(window.getGraphics());
    }
  }
	
  @Override
	public void render(Graphics g) {
    updateGraphics(g);
  }
  
  public void updateGraphics(Graphics g) {
    if(backBuffer !=null && window != null){
      g.drawImage(backBuffer, 0, 0,(int)window.getSize().getWidth(), (int)window.getSize().getHeight(), window);
      window.showStatus("StatusText");
    }else{
      Utils.log("No backbuffer created yet", System.err);
    }
  }

  public void setHud(HUD h) {
    hud = h;
  }

  public RenderWindow getWindow() {
    return window;
  }

  public void setWindow(RenderWindow w) {
    window = w;
  }
  
  public EventHandler getEventHandler() {
    return eventhandler;
  }

  public NetworkHandler getClient() {
    return eventhandler.getClient();
  }

  public void setClient(NetworkHandler c) {
    eventhandler.setClient(c);
  }

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene s) {
    scene = s;
  }

  public HUD getHud() {
    return hud;
  }

  public void setBackBufferGraphics(Graphics backBufferGraphics) {
    this.backBufferGraphics = backBufferGraphics;
  }

  public Graphics getBackBufferGraphics() {
    return backBufferGraphics;
  }
}
