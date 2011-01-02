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

import generic.ColorUtils;
import generic.Utils;

import java.util.Vector;

import objects.renderables.Object3D;
import objects.renderables.Text3D;
import objects.renderables.Triangle3D;
import rendering.Scene;

public class QTLheatmap {
	private static double cutoff;
	private static boolean modelonly;
	
	
	public QTLheatmap(){
		setCutoff(2.5);
		setModelonly(false);
	}
	
	public Vector<Object3D> getQTLObjects(QTLdataset d){
		Vector<Object3D> r = new Vector<Object3D>();
		double cm = 0;
//		double maxcm = d.markers[(d.qtlmatrix[0].scores.length-1)].location/3 + d.markers[(d.qtlmatrix[0].scores.length-1)].chromosome + d.chrlengths[d.markers[(d.qtlmatrix[0].scores.length-1)].chromosome]/1.5;
//		Surface triangle = new Surface((double)maxcm/2,(double)cutoff, (double)(d.qtlmatrix.length-1)/2,0,0,(double)maxcm/2,(d.qtlmatrix.length-1)/2,Color.green);//new Surface((double)maxcm/2,(double)cutoff,(double)(d.qtlmatrix.length-1)/2,0,0,0.0,Utils.doubleToColor(d.qtlmatrix[x].scores[m],d.maxqtl));
//		triangle.render(Engine.getBackBufferGraphics(),Scene.getCamera());
//		r.add((Object3D)triangle);
		for(int x=(d.qtlmatrix.length-1);x>=0;x--){	
			for(int m=(d.qtlmatrix[x].scores.length-1);m>=0;m--){
				cm = d.markers[m].location/3 + d.markers[m].chromosome + d.chrlengths[d.markers[m].chromosome]/1.5;
				if(Math.abs(d.qtlmatrix[x].scores[m]) > getCutoff()){
		        	if(isModelonly()){
		        		if(d.modelmatrix[x].scores[m]>0){
		        			//Surface triangle = new Surface(cm,0.0,x,0,0,d.qtlmatrix[x].scores[m]/100,d.qtlmatrix[x].scores[m]/100,Utils.doubleToColor(d.qtlmatrix[x].scores[m],d.maxqtl));
		        			Triangle3D qtl = new Triangle3D(cm,0.0,x,0,0,1,d.qtlmatrix[x].scores[m]/10,ColorUtils.doubleToColor(d.qtlmatrix[x].scores[m],d.maxqtl));
		        			Text3D t = new Text3D(""+d.qtlmatrix[x].scores[m],cm,(d.qtlmatrix[x].scores[m]/10)+1,x); 
		        			qtl.setWireframe(false);
		        			r.add((Object3D)qtl);
		        			r.add((Object3D)t);
		        		}
		        	}else{
		        		//Surface triangle = new Surface(cm,0.0,x,0,0,d.qtlmatrix[x].scores[m]/100,d.qtlmatrix[x].scores[m]/100,Utils.doubleToColor(d.qtlmatrix[x].scores[m],d.maxqtl));
		        		Triangle3D qtl = new Triangle3D((d.qtlmatrix[x].scores.length-1)-m,0.0,(d.qtlmatrix.length-1)-x,0,0,1,d.qtlmatrix[x].scores[m]/20,ColorUtils.doubleToColor(d.qtlmatrix[x].scores[m],d.maxqtl));
		        		if(d.modelmatrix[x].scores[m]>0){qtl.setWireframe(false); }else{ qtl.setWireframe(true); }
		        		r.add((Object3D)qtl);
		        	}
		        	if(r.size() > Scene.getSoftmyobjectslimit() && !isModelonly()){
		        		Utils.log("Too many objects, going into model only mode",System.err);
		        		r.clear();
		        		setModelonly(true);
		        		r=getQTLObjects(d);
		        		return r;
		        	}
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

	public static void setModelonly(boolean modelonly) {
		QTLheatmap.modelonly = modelonly;
	}

	public static boolean isModelonly() {
		return modelonly;
	}
}
