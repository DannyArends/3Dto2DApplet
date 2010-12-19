/*
#
# QTLdataset.java
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import rendering.Engine;
import rendering.Hud;
import rendering.Scene;
import generic.Utils;

public class QTLdataset {
	public int nmarkers, nindividuals, nchromosomes, ntraits, maxqtl, minqtl;
	public double[] chrlengths;
	public NamedDoubleArray[] qtlmatrix;
	public NamedIntArray[] modelmatrix;
	public GeneticLocation[] markers;
	public GeneticLocation[] traits;

	public QTLdataset(String path) {
		Utils.console("MODEL: Loading location: " + Engine.getParentApplet().getCodeBase().toString() + path);
	    try {
			URL url = new URL(Engine.getParentApplet().getCodeBase().toString()	+ path);
			InputStream stream = url.openStream();
			readmodeldata(stream);
			System.out.println("MODEL: Parsing done");
	    }catch(Exception e){
	    	Utils.log("Location",e);
	    }
	  }

	  public void readmodeldata(InputStream myfile) {
	      BufferedReader input = new BufferedReader(new InputStreamReader(myfile), 1024);
	      try {
	        String line = null; // entire line
	        String param = null; // param ( Before = )
	        String value = null; // value ( After = )
	        String[] values; // split of values with ,
	        while ((line = input.readLine()) != null) {
	          try {
	            param = line.split("=")[0];
	            value = line.split("=")[1];
	          } catch (Exception e) {
	            System.out.println("Unparsable line: " + line);
	          }
	          if (param.equals("markers")) {
	            nmarkers = Integer.parseInt(value);
	            markers = new GeneticLocation[nmarkers];
	          }
	          if (param.equals("individuals")) {
	            nindividuals = Integer.parseInt(value);
	            markers = new GeneticLocation[nmarkers];
	          }
	          if (param.equals("traits")) {
	            ntraits = Integer.parseInt(value);
	            qtlmatrix = new NamedDoubleArray[ntraits];
	          }
	          if (param.equals("locdata")) {
	            if (Integer.parseInt(value) == 1) {
	              System.out.println("Traits with genetic locations" + ntraits);
	              traits = new GeneticLocation[ntraits];
	            }
	          }
	          if (param.equals("modeldata")) {
	            if (Integer.parseInt(value) == 1) {
	              System.out.println("MQM Model present");
	              modelmatrix = new NamedIntArray[ntraits];
	            }
	          }
	          if (param.equals("chromosomes")) {
	            nchromosomes = Integer.parseInt(value);
	            chrlengths = new double[nchromosomes + 1];
	          }
	          if (param.equals("maxQTL")) {
	            maxqtl = Integer.parseInt(value);
	          }
	          if (param.equals("minQTL")) {
	            minqtl = Integer.parseInt(value);
	          }
	          if (param.equals("length")) {
	            values = value.split(",");
	            for (int x = 0; x < values.length; x++) {
	              chrlengths[x] = Double.parseDouble(values[x]);
	            }
	          }
	          try {
	            String[] split = param.split("\\.");
	            if (split[0].equals("qtldata")) {
	            	NamedDoubleArray n = new NamedDoubleArray();
	              values = value.split(",");
	              n.name = values[0];
	              n.scores = new double[nmarkers];
	              for (int x = 1; x < values.length; x++) {
	                n.scores[x - 1] = Double.parseDouble(values[x]);
	              }
	              qtlmatrix[Integer.parseInt(split[1])] = n;
	            }
	            if (split[0].equals("model")) {
	              NamedIntArray n = new NamedIntArray();
	              values = value.split(",");
	              n.name = qtlmatrix[(Integer.parseInt(split[1]))].name;
	              n.scores = new int[nmarkers];
	              for (int x = 0; x < values.length; x++) {
	                n.scores[x] = Integer.parseInt(values[x]);
	              }
	              modelmatrix[Integer.parseInt(split[1])] = n;
	            }
	            if (split[0].equals("loc")) {
	              GeneticLocation n = new GeneticLocation();
	              values = value.split(",");
	              n.name = values[0];
	              n.chromosome = (Integer.parseInt(values[1]) - 1);
	              n.location = Double.parseDouble(values[2]);
	              traits[Integer.parseInt(split[1])] = n;
	            }
	            if (split[0].equals("mapdata")) {
	            	GeneticLocation n = new GeneticLocation();
	              values = value.split(",");
	              n.name = values[0];
	              n.chromosome = (Integer.parseInt(values[1]) - 1);
	              n.location = Double.parseDouble(values[2]);
	              markers[Integer.parseInt(split[1])] = n;
	            }
	          } catch (Exception e) {
	        	  Utils.console("Line error" + e.getMessage());
	          }
	        }
	      } catch(Exception e){
	    	  Utils.log("General error",e);
	      }
	  }
	  
		public int DatasetInfo(Graphics2D g,int x,int y){
			Hud.drawBox(g,x,y,250,96,Color.gray);
			Hud.setFont(g,0);
			Hud.drawString(g,"--Dataset Overview--", x, y+12);
			Hud.setFont(g,1);
			Hud.drawString(g,"Traits: " + ntraits, x, y+24);
			Hud.drawString(g,"Chromosomes: " + nchromosomes, x, y+36);
		    String distances = "Lengths: ";
		    for (int c = 0; c < nchromosomes; c++) {
		      distances += chrlengths[c] + " ";
		    }
		    Hud.drawString(g,distances, x, y+48);
		    Hud.drawString(g,"Markers: " + nmarkers, x, y+60);
		    Hud.drawString(g,"Cut-off: " + ((float)Math.round(QTLheatmap.getCutoff()*10))/10.0, x, y+72);
		    return y+96;
		}
}

