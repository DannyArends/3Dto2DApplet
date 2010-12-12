/*
#
# InputBox.java
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


package objects.hud;

import events.MyHandler;
import game.Hud;
import game.Scene;
import generic.Utils;
import genetics.QTLheatmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class InputBox extends Button2D {
	String input="";
	int size;
	
	public InputBox(int x, int y,int s) {
		super(x, y,s*15,20);
		this.size=s;
	}
	
	@Override
	public void runPayload() {
		MyHandler.registerForKeystrokes(this);
	}
	
	@Override
	public void handleKeystroke(KeyEvent e) {
		char c = e.getKeyChar();
		if(c != KeyEvent.CHAR_UNDEFINED ) {
		    switch(c){
	    	case KeyEvent.VK_ENTER:this.input="";break;
	    	case KeyEvent.VK_BACK_SPACE:this.input= this.input.substring(0,(this.input.length()-1 > 0)?this.input.length()-1 : 0);break;
	    	default: this.input += c;
			}
		}
		Scene.updateScene();
	}

	@Override
	public void render(Graphics2D g) {
		Hud.drawBox(g, (int)x, (int)y, (int)size*15, (int)20, new Color(0.5f,0.5f,0.5f));
		Hud.drawString(g, input==""?"type here":input, (int)x+10, (int)y+15);
	}

}
