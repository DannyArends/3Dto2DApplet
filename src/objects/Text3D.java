/*
#
# Text3D.java
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
#
#     This program is free software; you can redistribute it and/or
#     modify it under the terms of the GNU General Public License,
#     version 3, as published by the Free Software Foundation.
# 
#     This program is distributed in the hope that it will be useful,
#     but without any warranty; without even the implied warranty of
#     merchantability or fitness for a particular purpose.  See the GNU
#     General Public License, version 3, for more details.
# 
#     A copy of the GNU General Public License, version 3, is available
#     at http://www.r-project.org/Licenses/GPL-3
#
*/

package objects;

import game.Engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Text3D extends Object3D {
	private String text;
	private int fontId=0;
  
	Font[] fonts = new Font[]{
			new Font ("Dialog", Font.PLAIN,  8)
			,new Font("Dialog", Font.PLAIN,  6)
			,new Font("Dialog", Font.PLAIN,  4)
			,new Font("Dialog", Font.PLAIN,  2)
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
