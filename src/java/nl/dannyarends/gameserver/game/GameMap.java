package nl.dannyarends.gameserver.game;

import java.awt.Color;
import java.util.ArrayList;

import nl.dannyarends.rendering.Scene;
import nl.dannyarends.rendering.objects.renderables.Object3D;
import nl.dannyarends.rendering.objects.renderables.Text3D;
import nl.dannyarends.applet.events.ServerConnection;
import nl.dannyarends.generic.Utils;

public class GameMap extends GameObject{
	String mapname;
	TileTypes tiletypes;
	BuildingTypes buildingtypes;
	GameTile[] maptiles;
	int dimx,dimy,tiledimension;
	
	
	public GameMap(ServerConnection s,String name) {
		super(s);
		mapname=name;
		tiletypes = new TileTypes(connection);
		buildingtypes = new BuildingTypes(connection);
		parseMap(connection.commandToServer("function=list_map&name="+mapname));
	}

	/// parseMap: Parse response from server,
	// @param map String response from the server when called for an list_map&name=MapName
	void parseMap(String map){
		String[] dimension1 = map.split("\n");
		int offset = Utils.offsetByComment(dimension1);
		if(offset < 0) return;
		String[] dimension2 = dimension1[offset].split(" ");
		dimx=dimension1.length-offset;
		dimy=dimension2.length;
		tiledimension = dimension2[0].split(";").length;
		maptiles = new GameTile[dimx*dimy];
		Utils.console("Loading map "+ mapname +": " + dimension1.length + " "+ dimx +","+ dimy +"*"+ tiledimension);
		for(int x=0;x<dimx;x++){
			dimension2 = dimension1[x+offset].split(" ");
			for(int y=0;y<dimy;y++){
				String[] tile = dimension2[y].split(";");
				maptiles[(x*dimy)+y] = new GameTile(this,x,y,tiledimension);
				maptiles[(x*dimy)+y].setHeight(Integer.parseInt(tile[0]));
				maptiles[(x*dimy)+y].setTileID(Integer.parseInt(tile[1]));
				maptiles[(x*dimy)+y].setObjectID(Integer.parseInt(tile[2]));
			}
		}
	}
	
	public ArrayList<Object3D> getObject3D(){
		ArrayList<Object3D> objects = new ArrayList<Object3D>();
		Object3D building;
		Text3D label;
		for(int x=dimx-1;x>=0;x--){
			for(int y=dimy-1;y>=0;y--){
				objects.add(maptiles[(x*dimy)+y].toSurface());
				if((building = maptiles[(x*dimy)+y].getBuilding()) != null){
					objects.add(building);
					
				}
				if((label = maptiles[(x*dimy)+y].getLabel()) != null){
					objects.add(label);
				}
			}
		}
		return objects;
	}
	
	void parseTileResponse(int x, int y, String response){
		String[] dimension1 = response.split("\n");
		int offset=0;
		if(dimension1.length==0)return;
		while(dimension1[offset].equals("") || dimension1[offset].startsWith("#")){
			offset++;
		}
		Utils.console("Offset:" + offset);
		String[] tile = dimension1[offset].split(";");
		maptiles[(x*dimy)+y].setHeight(Integer.parseInt(tile[0]));
		maptiles[(x*dimy)+y].setTileID(Integer.parseInt(tile[1]));
		maptiles[(x*dimy)+y].setObjectID(Integer.parseInt(tile[2]));
	}
	
	public void update_tile(int x,int y,int tile,int value){
		parseTileResponse(x,y,connection.commandToServer("function=update_tile&p1="+mapname+"&p2="+x+"&p3="+y+"&p4="+tile+"&p5="+value));
		Scene.mapReload();
	}
	
	public void update_map(){
		connection.commandToServer("function=update_map&p1="+mapname);
		Scene.mapReload();
	}

	public boolean buildingExists(int id) {
		return buildingtypes.buildingExists(id);
	}

	public Color getTileColor(int id) {
		return tiletypes.getTileColor(id);
	}

	public String getBuildingName(int id) {
		return buildingtypes.getBuildingName(id);
	}
}
