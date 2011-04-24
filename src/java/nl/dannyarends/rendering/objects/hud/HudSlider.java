package nl.dannyarends.rendering.objects.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import nl.dannyarends.rendering.Hud;
import nl.dannyarends.rendering.Scene;
import nl.dannyarends.applet.events.MyHandler;

public class HudSlider extends HudButton  {
	int cur = 0;
	
	public HudSlider(int x, int y, int sx, int sy) {
		super(x, y);
		setSize(sx,sy);
	}
		

	@Override
	public void runPayload() {
		MyHandler.registerForSlide(this);
	}
	
	@Override
	public boolean handleSlide(int mx, int my) {
		if(Math.abs(my-y) > 20) return false;
		if((mx-x) < 0 || (mx-x) > 150) return true;
		this.cur = (int) (mx-x);
		Scene.updateScene(true,false);
		return true;
	}
	
	@Override
	public void render(Graphics2D g) {
		Hud.setFont(g, 1);
		Hud.drawString(g, "Slide me", (int)x-50, (int)y+5);
		Hud.drawBox(g, (int)x, (int)y, (int)160, (int)2, new Color(0.5f,0.5f,0.5f));
		Hud.drawBox(g, (int)x+cur, (int)y-5, (int)20, (int)10, Color.white);
		Hud.drawString(g, ""+cur, (int)x+160, (int)y+5);
	}
}
