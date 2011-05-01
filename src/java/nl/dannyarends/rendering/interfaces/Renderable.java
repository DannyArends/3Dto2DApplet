package nl.dannyarends.rendering.interfaces;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Renderable {
  public Color bgcolor = Color.white;
  public Color txtcolor = Color.green;
  
  public void render(Graphics2D g);
}
