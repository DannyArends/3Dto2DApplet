package game;

import objects.renderables.Object3D;
import objects.renderables.Surface;
import objects.renderables.Text3D;
import objects.renderables.Triangle3D;
import events.ServerConnection;

public class GameTile extends GameObject{
	int[] information;
	
	GameTile(ServerConnection s,int x, int y, int tiledimension) {
		super(s);
		information = new int[tiledimension+2];
		information[0] = x;
		information[1] = y;
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
		Surface s = new Surface(getX(), getHeight()/50.0, getY(), 0, 0, 0.4, 0.4, TileTypes.getTileColor(getTileID()));
		s.setName("MapTile (" + getX() + ","+ getY() + "@"+getHeight()+"): " + getTileID() + "," + getObjectID());
		return s;
	}
	
	public Object3D getBuilding(){
		if(getObjectID() > 0){
			Object3D building;
			building = new Triangle3D(getX(), (getHeight()+1)/50.0, getY(), 0, 0, 0.5, 1, BuildingTypes.getBuildingColor(getObjectID()));
			return building;
		}else{
			return null;
		}
	}
	
	public Text3D getLabel(){
		if(getObjectID() > 0){
			Text3D label;
			label = new Text3D(getX(), ((getHeight())/50.0) + 1, getY());
			label.setText(BuildingTypes.getBuildingName(getObjectID()));
			return label;
		}else{
			return null;
		}
	}
}
