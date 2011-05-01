package nl.dannyarends.rendering.hud;

import java.awt.Graphics2D;
import java.util.ArrayList;

import nl.dannyarends.rendering.Hud;

public class HudWindow extends HudObject {
  private ArrayList<HudObject> topMenu = new ArrayList<HudObject>();
  private boolean showTopMenu = true;
  private boolean minimized = false;
  private boolean visible = true;
  private boolean active = false;
  private boolean needUpdate = false;
  
  public HudWindow(int x, int y, Hud h) {
    super(x, y, h);
  }

  @Override
  public int sx() {
    int sx = 0;
    for(HudObject c : children){
      if(c.x+c.sx() > sx) sx = c.x+c.sx();
    }
    return sx;
  }

  @Override
  public int sy() {
    if(minimized) return 20;
    int sy = 0;
    for(HudObject c : children){
      if(c.y+c.sy() > sy) sy = c.y+c.sy();
    }
    return 20+sy;
  }

  @Override
  public boolean onKey(char c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean onSlide(int mx, int my) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean onClick() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void render(Graphics2D g) {
    if(visible){
      drawBox(g);
      if(!minimized){
        for(HudObject o : children){
          o.render(g);
        }
      }
    }
  }

  @Override
  public boolean onUpdate() {
    // TODO Auto-generated method stub
    return false;
  }

}
