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
	double reflectance=2;
	double shininess=5;
	
	private double[] ambientColor = new double[]{1.0,1.0,1.0};
	private double[] specularColor = new double[]{1.0,1.0,1.0};
	private double[] diffuseColor = new double[]{1.0,1.0,1.0};
	private double[] emissionColor = new double[]{0.1,0.1,0.1};
	
	public Material3DS(String name){
		this.materialname=name;
	}


	public void setAmbientColor(Color ambientColor) {
		this.ambientColor[0] = (float)ambientColor.getRed()/255.0;
		this.ambientColor[1] = (float)ambientColor.getGreen()/255.0;
		this.ambientColor[2] = (float)ambientColor.getBlue()/255.0;
	}


	public double[] getAmbient() {
		return ambientColor;
	}

	public Color getAmbientColor() {
		return new Color((int)(ambientColor[0]*255),(int)(ambientColor[1]*255),(int)(ambientColor[2]*255));
	}

	public void setSpecularColor(Color specularColor) {
		this.specularColor[0] = (float)specularColor.getRed()/255.0;
		this.specularColor[1] = (float)specularColor.getGreen()/255.0;
		this.specularColor[2] = (float)specularColor.getBlue()/255.0;
	}


	public double[] getSpecular() {
		return specularColor;
	}
	
	public Color getSpecularColor() {
		return new Color((int)(specularColor[0]*255),(int)(specularColor[1]*255),(int)(specularColor[2]*255));
	}


	public void setDiffuseColor(Color diffuseColor) {
		this.diffuseColor[0] = (float)diffuseColor.getRed()/255.0;
		this.diffuseColor[1] = (float)diffuseColor.getGreen()/255.0;
		this.diffuseColor[2] = (float)diffuseColor.getBlue()/255.0;
	}


	public double[] getDiffuse() {
		return diffuseColor;
	}
	
	public Color getDiffuseColor() {
		return new Color((int)(diffuseColor[0]*255),(int)(diffuseColor[1]*255),(int)(diffuseColor[2]*255));
	}
	
	public double[] getEmission() {
		return emissionColor;
	}
	


	public Object getName() {
		// TODO Auto-generated method stub
		return materialname;
	}

	public double getReflectance() {
		return reflectance;
	}


	public double getShininess() {
		return shininess;
	}
	

}
