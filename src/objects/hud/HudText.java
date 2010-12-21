package objects.hud;


import generic.Utils;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import rendering.Hud;

public class HudText extends HudObject{
	private String text;
	
	public HudText(int x, int y, String t){
		super(x,y);
		setName("text");
		setText(t);
	}

	@Override
	public void render(Graphics2D g) {
		Hud.setFont(g, 1);
		Hud.drawString(g, getText(), (int)x+10, (int)y+15);
	}

	@Override
	public void handleKeystroke(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleSlide(int mx, int my) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
