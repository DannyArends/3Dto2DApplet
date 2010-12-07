/*
#
# QTLheatmap.java
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

package genetics;

import objects.Object3D;
import objects.Text3D;
import objects.Triangle3D;
import game.Engine;
import game.Scene;
import generic.Utils;

public class QTLheatmap {
	
	public QTLheatmap(QTLdataset d){
		double cm = 0;
		for(int x=(d.qtlmatrix.length-1);x>=0;x--){	
			for(int m=(d.qtlmatrix[x].scores.length-1);m>=0;m--){
				cm = d.markers[m].location/3 + d.markers[m].chromosome + d.chrlengths[d.markers[m].chromosome]/1.5;
				if(Math.abs(d.qtlmatrix[x].scores[m]) > 1){
		        	Triangle3D cube = new Triangle3D(cm,0.0,x,0,0,1,d.qtlmatrix[x].scores[m]/10,Utils.doubleToColor(d.qtlmatrix[x].scores[m],d.maxqtl));
					if(d.modelmatrix[x].scores[m]>0) cube.setWireframe(true);
		        	cube.render(Engine.getBackBufferGraphics(),Scene.getCamera());
					Scene.addObject((Object3D)cube);
				}
				if(x==0){
					Text3D text = new Text3D(d.markers[m].name,cm,0.0,-5);
					Scene.addObject((Object3D)text);
				}
			}
			Text3D text = new Text3D(d.qtlmatrix[x].name,-10,0.0,x);
			Scene.addObject((Object3D)text);
		}
	}
}
