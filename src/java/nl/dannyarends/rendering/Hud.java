package nl.dannyarends.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.hud.HudText;
import nl.dannyarends.rendering.hud.HudWindow;
import nl.dannyarends.rendering.interfaces.Renderable;

public class Hud implements Runnable,Renderable {
  private ArrayList<HudWindow> windows = new ArrayList<HudWindow>();
  private RenderWindow window;
  private Engine engine;
  private Scene scene;
  
  public Hud(RenderWindow w, Engine e, Scene s) {
    window=w;
    engine=e;
    scene=s;
    HudWindow wi = new HudWindow(30,30,this);
    HudText t = new HudText(0,0,this);
    t.addText("Halo");
    wi.addChild(t);
    windows.add(wi);
  }
  
  @Override
  public void run() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(Graphics2D g) {
    g.setColor(Color.white);
    for(HudWindow w : windows){
      w.render(g);
    }
    Utils.idle(20);
  }
}
