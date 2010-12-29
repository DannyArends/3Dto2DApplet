/*
#
# MenuButton2D.java
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

import generic.Utils;
import rendering.Scene;

public class HudMenuButton extends HudButton{
	public HudMenuButton(int x, int y, String name) {
		super(x, y);
		setName(name);
	}
	
	public HudMenuButton(int x, int y, int sx, int sy, String name) {
		super(x, y);
		setSize(sx,sy);
		setName(name);
	}
	
	@Override
	public void runPayload() {
		Utils.console("HudMenuButton "+getName()+" at: " + x + ", " + y + " clicked");
		for(int wb=0;wb<children.size();wb++){
			children.get(wb).setVisible(!children.get(wb).isVisible());
		}
		Utils.console("Opened "+ children.size() +" subwindows");
		Scene.updateScene(true,false);
	}
}
