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

package nl.dannyarends.applet.objects.renderables;


import nl.dannyarends.generic.MathUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import nl.dannyarends.applet.objects.Camera;
import nl.dannyarends.applet.objects.Vector3D;
import nl.dannyarends.rendering.Engine;

/// Text3D
//<p>
//Class to used to provide a placeholder for text at a 3D location without having to do transforms
//</p>
//
public class Text3D extends Object3D {
	private String text;
	private int fontId=0;
  
	Font[] fonts = new Font[]{
			 new Font("Dialog",  Font.PLAIN,  10)
			,new Font("Dialog",  Font.PLAIN,  9)
			,new Font("Dialog",  Font.PLAIN,  8)
			,new Font("Dialog",  Font.PLAIN,  7)
			,new Font("Dialog",  Font.PLAIN,  6)
			,new Font("Dialog",  Font.PLAIN,  5)
			,new Font("Dialog",  Font.PLAIN,  4)
			,new Font("Dialog",  Font.PLAIN,  3)
			,new Font("Dialog",  Font.PLAIN,  2)
			,new Font("Dialog",  Font.PLAIN,  1)
			,new Font("Dialog",  Font.PLAIN,  0)
	    };

	
	public Text3D(double x,double y,double z){
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
		int width = Engine.getWidth();
		int height = Engine.getHeight();
		int scaleFactor_w = (int) ((width / 1.90));
		int scaleFactor_h = (int) ((height / 1.40));
		double[] d = computeOrtogonalProjection(MathUtils.calcPointsDiff(c.location,location),c.rotation);
		int fontid;
		if(!inFrontOfCamera(d[2])){
			//Calculate a perspective projection
			d=computePerspectiveProjection(d);
			fontid = (int)pointDifference(difference(c))/30;
				
			g2d.setFont(fonts[fontid<10?fontid:10]);
			g2d.setColor(Color.white);
			g2d.drawString(getText(), (int)(width/2 - scaleFactor_w * d[0]), (int)(height/2 - scaleFactor_h * d[1]));
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

	@Override
	public double[] getTextureCoords(double[] point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double intersect(Vector3D ray) {
		// TODO Auto-generated method stub
		return 0;
	}
}
