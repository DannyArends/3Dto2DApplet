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

import java.util.Vector;

import objects.Object3D;
import objects.Text3D;
import objects.Triangle3D;
import game.Engine;
import game.Scene;
import generic.Utils;

public class QTLheatmap {
	private static double cutoff;
	
	
	public QTLheatmap(){
		setCutoff(2.5);
	}
	
	public Vector<Object3D> getQTLObjects(QTLdataset d){
		Vector<Object3D> r = new Vector<Object3D>();
		double cm = 0;
		for(int x=(d.qtlmatrix.length-1);x>=0;x--){	
			for(int m=(d.qtlmatrix[x].scores.length-1);m>=0;m--){
				cm = d.markers[m].location/3 + d.markers[m].chromosome + d.chrlengths[d.markers[m].chromosome]/1.5;
				if(Math.abs(d.qtlmatrix[x].scores[m]) > getCutoff()){
		        	Triangle3D cube = new Triangle3D(cm,0.0,x,0,0,1,d.qtlmatrix[x].scores[m]/10,Utils.doubleToColor(d.qtlmatrix[x].scores[m],d.maxqtl));
					if(d.modelmatrix[x].scores[m]>0) cube.setWireframe(true);
		        	cube.render(Engine.getBackBufferGraphics(),Scene.getCamera());
					r.add((Object3D)cube);
				}
			}
		}
		return r;
	}
	
	public Vector<Object3D> getAnnotationObjects(QTLdataset d){
		Vector<Object3D> r = new Vector<Object3D>();
		double cm = 0;
		for(int x=(d.qtlmatrix.length-1);x>=0;x--){	
			Text3D text = new Text3D(d.qtlmatrix[x].name,-10,0.0,x);
			r.add((Object3D)text);
		}
		for(int m=(d.qtlmatrix[0].scores.length-1);m>=0;m--){
			cm = d.markers[m].location/3 + d.markers[m].chromosome + d.chrlengths[d.markers[m].chromosome]/1.5;
			Text3D text = new Text3D(d.markers[m].name,cm,0.0,-5);
			r.add((Object3D)text);
		}
		return r;
	}
	
	public static void increaseCutoff(){
		setCutoff(getCutoff() + 0.1);
	}
	
	public static void decreaseCutoff(){
		setCutoff(getCutoff() - 0.1);
	}

	public static void setCutoff(double cutoff) {
		QTLheatmap.cutoff = cutoff;
	}

	public static double getCutoff() {
		return cutoff;
	}
}
