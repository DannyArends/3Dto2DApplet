package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Text3D extends Point3D {
	private String text;
	Text3D(){
		
	}
	
	Text3D(String text){
		setText(text);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	
	public void render(Graphics g, Camera c){
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawString(getText(), arg1, arg2)
	}
}
