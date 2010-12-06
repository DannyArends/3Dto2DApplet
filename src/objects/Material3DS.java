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
