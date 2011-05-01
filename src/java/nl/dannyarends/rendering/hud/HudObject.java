package nl.dannyarends.rendering.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import nl.dannyarends.rendering.Hud;
import nl.dannyarends.rendering.interfaces.Renderable;

public abstract class HudObject implements Renderable {
  ArrayList<HudObject> children = new ArrayList<HudObject>();
  HudObject parent = null;
  Hud display = null;
  public int x = 0;
  public int y = 0;
  public Color bgcolor = Color.white;
  public Color txtcolor = Color.green;

  HudObject(int x,int y, Hud p){
    this.x=x;
    this.y=y;
    display=p;
  }
  
  private void setParent(HudObject p){
   this.parent=p;
  }
  
  public int getScreenX(){
    if(parent!=null) return x+parent.getScreenX();
    return x;
  }
  
  public int getScreenY() {
    if(parent!=null) return y+parent.getScreenY();
    return y;
  }
  
  public void addChild(HudObject c){
    c.setParent(this);
    children.add(c);
  }

  public void drawBox(Graphics2D g){
    g.setColor(bgcolor);
    g.fillRect(x, y, sx(), sy());
  }
  
  abstract public int sx();
  abstract public int sy();
  abstract public boolean onKey(char c);
  abstract public boolean onSlide(int mx, int my);
  abstract public boolean onClick();
  
  @Override
  abstract public void render(Graphics2D g);

}
