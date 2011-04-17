package nl.dannyarends.applet.game;

import nl.dannyarends.applet.objects.renderables.Object3D;
import nl.dannyarends.applet.objects.renderables.Surface;
import nl.dannyarends.applet.objects.renderables.Text3D;
import nl.dannyarends.rendering.Engine;

public class GameTile extends GameObject{
	int[] information;
	GameMap map;
	
	GameTile(GameMap m,int x, int y, int tiledimension) {
		super(m.connection);
		information = new int[tiledimension+2];
		information[0] = x;
		information[1] = y;
		map=m;
	}
	
	public int getX(){
		return information[0];
	}
	
	public int getY(){
		return information[1];
	}

	public int getHeight(){
		return information[2];
	}
	
	public void setHeight(int height){
		information[2] = height;
	}
	
	public int getTileID(){
		return information[3];
	}
	
	public void setTileID(int tileID){
		information[3] = tileID;
	}	
	
	public int getObjectID(){
		return information[4];
	}
	
	public void setObjectID(int objectID){
		information[4] = objectID;
	}
	
	public Surface toSurface(){
		Surface s = new Surface(getX(), getHeight()/500.0, getY(), 0, 0, 0.4, 0.4, map.getTileColor(getTileID()));
		s.setName("MapTile (" + getX() + ","+ getY() + "@"+getHeight()+"): " + getTileID() + "," + getObjectID());
		return s;
	}
	
	public Object3D getBuilding(){
		if(map.buildingExists(getObjectID())){
			return Engine.getObjectLoader().getModel(getX(), -getY()-0.25, ((getHeight())/500.0) , "object_"+getObjectID()+".3ds");
		}else{
			return null;
		}
	}
	
	public Text3D getLabel(){
		if(map.buildingExists(getObjectID())){
			Text3D label;
			label = new Text3D(getX()+0.25, ((getHeight())/500.0) + 0.7, getY()-0.25);
			label.setText(map.getBuildingName(getObjectID()));
			return label;
		}else{
			return null;
		}
	}
}
