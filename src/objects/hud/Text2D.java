package objects.hud;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import rendering.Hud;

public class Text2D extends Object2D{
	private String text;
	
	public Text2D(int x,int y, String t){
		super(x,y);
		setText(t);
	}

	@Override
	public void render(Graphics2D g) {
		Hud.drawBox(g, (int)x, (int)y, getText().length()*7,20, Color.darkGray);
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
