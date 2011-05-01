/*
#
# Scene.java
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
import java.awt.Graphics2D;
import java.util.ArrayList;

import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.hud.HudWindow;
import nl.dannyarends.rendering.interfaces.Renderable;
import nl.dannyarends.rendering.objects.lighting.Light;
import nl.dannyarends.rendering.scene.Object3D;


/// A 'renderable' moment/state of the engine
//<p>
//TODO
//</p>
//

public class Scene implements Renderable, Runnable{
  private ArrayList<Object3D> objects = new ArrayList<Object3D>();
  private ArrayList<Light> lights = new ArrayList<Light>();
  private RenderWindow window;
  private Engine engine;
  
	public Scene(RenderWindow w, Engine e){
	  window=w;
	  engine=e;
	}
	
	@Override
	public void run() {

	}
	
  @Override
  public void render(Graphics2D g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, (int)window.getSize().getWidth(),(int)window.getSize().getHeight());
    Utils.idle(20);
  }

  public int getWidth() {
    // TODO Auto-generated method stub
    return (int) window.getSize().getWidth();
  }
  
  public int getHeight() {
    // TODO Auto-generated method stub
    return (int) window.getSize().getHeight();
  }

  public ArrayList<Object3D> getObjects() {
    return objects;
  }
  
  public ArrayList<Light> getLights() {
    return lights;
  }

  public double[] getAmbientLight() {
    return null;
  }
}
