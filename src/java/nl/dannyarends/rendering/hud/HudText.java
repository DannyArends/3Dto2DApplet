package nl.dannyarends.rendering.hud;

import java.awt.Graphics2D;
import java.util.ArrayList;

import nl.dannyarends.rendering.Hud;

public class HudText extends HudObject {
  private ArrayList<String> texts = new ArrayList<String>();

  public HudText(int x, int y, Hud p) {
    super(x, y, p);
  }
  
  public void addText(String s){
    texts.add(s);
  }
  
  @Override
  public int sx() {
    int sx=0;
    for(String line : texts){
      if(line.length() > sx) sx = line.length();
    }
    return (sx*7);
  }

  @Override
  public int sy() {
    return (int)20*texts.size();
  }

  @Override
  public void render(Graphics2D g) {
    g.setColor(txtcolor);
    for(int i=0;i<texts.size();i++){
      g.drawString(texts.get(i), getScreenX(), (int)getScreenY()+16*(i+2));
    }
  }

  @Override
  public boolean onKey(char c) {
    return false;
  }

  @Override
  public boolean onSlide(int mx, int my) {
    return false;
  }

  @Override
  public boolean onClick() {
    return false;
  }

  @Override
  public boolean onUpdate() {
    // TODO Auto-generated method stub
    return false;
  }

}
