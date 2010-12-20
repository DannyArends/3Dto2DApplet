package objects.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import rendering.Hud;
import rendering.Scene;

import events.MyHandler;

public class HudSlider extends HudButton  {
	int cur = 0;
	
	public HudSlider(int x, int y, int sx, int sy,HudWindow p) {
		super(x, y, sx, sy,p);
	}
	
	public HudSlider(int x, int y) {
		this(x, y,null);
	}
	
	public HudSlider(int x, int y,HudWindow p) {
		super(x-2, y-5, 150, 10,p);
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
		Scene.updateScene();
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
