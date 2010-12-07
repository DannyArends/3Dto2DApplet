/*
#
# Material3DS.java
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

import java.awt.Color;

public class Material3DS {
	String materialname;
	public boolean is_diffuse = false;
	public boolean is_specular = false;
	public boolean is_ambient = false;
	
	private Color ambientColor;
	private Color specularColor;
	private Color diffuseColor;
	
	public Material3DS(String name){
		this.materialname=name;
	}


	public void setAmbientColor(Color ambientColor) {
		this.ambientColor = ambientColor;
	}


	public Color getAmbientColor() {
		return ambientColor;
	}


	public void setSpecularColor(Color specularColor) {
		this.specularColor = specularColor;
	}


	public Color getSpecularColor() {
		return specularColor;
	}


	public void setDiffuseColor(Color diffuseColor) {
		this.diffuseColor = diffuseColor;
	}


	public Color getDiffuseColor() {
		return diffuseColor;
	}
	

}
