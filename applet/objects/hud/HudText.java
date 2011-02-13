package objects.hud;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import rendering.Hud;

public class HudText extends HudObject{
	private ArrayList<String> text = new ArrayList<String>();
	
	public HudText(int x, int y, String t){
		super(x,y);
		setName("text");
		addLine(t);
	}

	@Override
	public void render(Graphics2D g) {
		Hud.setFont(g, 1);
		int line_cnt = 1;
		for(String line : getTextVector()){
			Hud.drawString(g, line, (int)x, (int)y+20*line_cnt);
			line_cnt++;
		}
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

	public void addLine(String line) {
		text.add(line);
	}

	public ArrayList<String> getTextVector() {
		return text;
	}

	public void clearLines() {
		text.clear();
	}
}
