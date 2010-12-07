package objects;

import game.Engine;
import generic.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Text3D extends Object3D {
	private String text;
	private int fontId=0;
  
    Font[] fonts = new Font[]{
		new Font ("Helvetica", Font.PLAIN,  10)
		,new Font("TimesRoman", Font.PLAIN,  10)
		,new Font("Courier", Font.PLAIN,  10)
		,new Font("Helvetica", Font.BOLD,  10)
		,new Font("Helvetica", Font.ITALIC,  10)
		,new Font("Helvetica", Font.BOLD + Font.ITALIC,  10)
    };

	
	Text3D(double x,double y,double z){
		super(x,y,z);
		this.fontId = 0;
	}
	
	public Text3D(String text,double x,double y,double z){
		this(x,y,z);
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
		int scaleFactor = (int) ((Engine.getWidth() / 8));
		double[] d = computeOrtogonalProjection((this.x - c.x),(this.y - c.y),(this.z - c.z),rotation);
		if(!((d[2] + near + nearToObj) < 0)){
			//Calculate a perspective projection
			d=computePerspectiveProjection(d[0],d[1],d[2],near,nearToObj);
			if(!((Engine.getWidth()/2 - scaleFactor * d[0]) < 0) && !((Engine.getWidth()/2 - scaleFactor * d[0])<0)){
				g2d.setFont(fonts[fontId]);
				g2d.setColor(Color.white);
				g2d.drawString(getText(), (int)(Engine.getWidth()/2 - scaleFactor * d[0]), (int)(Engine.getHeight()/2 - scaleFactor * d[1]));
			}else{
				Utils.console("not in view");
			}
		}
	}

	public void setFontId(int fontId) {
		if(fontId > fonts.length){
			this.fontId = 0;
		}
		this.fontId = fontId;
	}

	public int getFontId() {
		return fontId;
	}
}
