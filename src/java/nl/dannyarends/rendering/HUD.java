package nl.dannyarends.rendering;

import java.awt.Color;
import java.awt.Graphics;

import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.interfaces.Renderable;

public class HUD implements Runnable,Renderable {
  private RenderWindow window;
  private Engine engine;
  private Scene scene;
  
  public HUD(RenderWindow w, Engine e, Scene s) {
    window=w;
    engine=e;
    scene=s;
  }
  
  @Override
  public void run() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(Graphics g) {
    g.setColor(Color.white);
    g.drawString(engine.getClient().serverTime.getTime(),10,10);
    g.drawString(engine.getClient().serverTime.getDate(),10,30);
    g.drawString(engine.stats_scene_time + "/" + engine.stats_hud_time,10,50);
    Utils.idle(20);
  }
}
