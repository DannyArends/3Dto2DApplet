/*
#
# Loader3DS.java
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

package rendering;

import java.net.URL;
import java.util.ArrayList;

import objects.renderables.Model3DS;
import events.ServerConnection;
import generic.Utils;

/// Lazy loader to load 3DS objects via HTTP 
//<p>
//TODO
//</p>
//

public class Object3DSLoader {
	static ArrayList<Model3DS> models = new ArrayList<Model3DS>();
	public static int notfound = 0;
	URL url;

	/**
	 * Object3DSLoader constructor: Gets a list of 3DS files and initializes empty models3DSObjects
	 * 
	 */	
	public Object3DSLoader(ServerConnection s) {
		String modellist = s.commandToServer("list_files=3ds&dir=models");
		Utils.console("From server: " + (modellist.split("\n").length - 3) + " models");
		for(String line : modellist.split("\n")){
			if(!line.startsWith("#") && !line.equals("")){
				Utils.console("We got model: '"+line+"' from server");
				Model3DS h = new Model3DS(0,0,0,line);
				models.add(h);
			}
		}
	}
	
	public static Model3DS getModel(double x, double y, double z,String name){
		Model3DS image = null;
		for(Model3DS h : models){
			if(h.getName().equalsIgnoreCase(name)){
				if(!h.isLoaded()) h.TryLoadingFromName();
				image = new Model3DS(h);
				image.setLocation(x, y, z);
			}
		}
		if(image==null){
			notfound++;
			return new Model3DS(x, y, z, "error");
		}
		return image;
	}

	public static int getAvailable(){
		return models.size();
	}
	
	public static int getUsed(){
		int cnt = 0;
		for(Model3DS h : models){
			if(h.isLoaded()) cnt++;
		}
		return cnt;
	}

}
