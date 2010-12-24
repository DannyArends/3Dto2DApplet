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
	double reflectance=0;
	double shininess=0;
	
	private Color ambientColor = new Color(125,0,0);
	private Color specularColor = new Color(0,125,0);
	private Color diffuseColor = new Color(0,0,125);
	
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


	public Object getName() {
		// TODO Auto-generated method stub
		return materialname;
	}


	public double[] getSpecular() {
		return new double[]{specularColor.getRed()/255,specularColor.getGreen()/255,specularColor.getBlue()/255};
	}


	public double[] getAmbient() {
		// TODO Auto-generated method stub
		return new double[]{ambientColor.getRed()/255,ambientColor.getGreen()/255,ambientColor.getBlue()/255};
	}


	public double[] getEmission() {
		// TODO Auto-generated method stub
		return new double[]{diffuseColor.getRed()/255,diffuseColor.getGreen()/255,diffuseColor.getBlue()/255};
	}


	public double getReflectance() {
		return reflectance;
	}


	public double getShininess() {
		return shininess;
	}
	

}
