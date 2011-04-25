package nl.dannyarends.generator.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public abstract class ModelObject {
  
  public class PassValues{
	  public int length;
	  public String[] values;
  }
  
  ModelObject(){
	  
  }
  
  ModelObject(int x, int y){
     this.x=x;
     this.y=y;
  }
  
  ModelObject(int x, int y,String name){
    this.x=x;
    this.y=y;
    this.name=name;
  }
  
  public ModelObject(String name) {
    this.name=name;
  }

private int x=0;
  private int y=0;
  private int z=0;
  String sep = ";";
  String name = "";
  String description = "";
  public abstract void writeToStream(BufferedWriter out) throws IOException;
  public abstract int readFromStream(BufferedReader in);
	
  public PassValues toValues(BufferedReader in){
  	try{
  	  int c = in.read();
  	  String attribute = "";
  	  int cnt = 0;
  	  do{
  		attribute += (char)c;
  		cnt++;
  	  }while(((c =  in.read()) != -1 && (char)c != '\0'));
  	  PassValues ret = new PassValues();
  	  ret.values = attribute.split(sep);
  	  ret.length = cnt;
  	  return ret;
  	}catch(Exception e){
  	  System.err.println("unable to parse in to String[] using sep");
  	  return null;
  	}
  }
  
  public String getName() {
    return name;
  }
  
  public void setX(int x) {
  	this.x = x;
  }
  
  public int getX() {
  	return x;
  }
  
  public void setY(int y) {
  	this.y = y;
  }
  
  public int getY() {
  	return y;
  }
  
  public void setZ(int z) {
  	this.z = z;
  }
  
  public int getZ() {
  	return z;
  }
}
