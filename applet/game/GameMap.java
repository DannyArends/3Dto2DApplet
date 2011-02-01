package game;

import java.awt.Color;
import java.util.ArrayList;

import objects.renderables.Object3D;
import objects.renderables.Surface;
import objects.renderables.Triangle3D;
import events.ServerConnection;
import generic.Utils;

public class GameMap extends GameObject{
	String mapname;
	ServerConnection server;
	int[][][] maptiles;
	int dimx,dimy,tiledimension;
	
	public GameMap(ServerConnection s,String name) {
		super(s);
		mapname=name;
		server=s;
		parseMap(server.commandToServer("function=list_map&name="+mapname));
	}

	/// parseMap: Parse response from server,
	// @param map String response from the server when called for an list_map&name=MapName
	void parseMap(String map){
		String[] dimension1 = map.split("\n");
		int offset=0;
		while(dimension1[offset].equals("") || dimension1[offset].startsWith("#")){offset++;}
		Utils.console("Offset:" + offset);
		String[] dimension2 = dimension1[offset].split(" ");
		dimx=dimension1.length-offset;
		dimy=dimension2.length;
		tiledimension = dimension2[0].split(";").length;
		maptiles = new int[dimx][dimy][tiledimension];
		Utils.console("Loading map "+ mapname +": " + dimension1.length + " "+ dimx +","+ dimy +"*"+ tiledimension);
		for(int x=0;x<dimx;x++){
			dimension2 = dimension1[x+offset].split(" ");
			for(int y=0;y<dimy;y++){
				String[] tile = dimension2[y].split(";");
				for(int t=0;t<tiledimension;t++){
					maptiles[x][y][t] = Integer.parseInt(tile[t]);
				}
			}
		}
	}
	
	public ArrayList<Object3D> getObject3D(){
		ArrayList<Object3D> objects = new ArrayList<Object3D>();
		for(int x=dimx-1;x>=0;x--){
			for(int y=dimy-1;y>=0;y--){
				double height = maptiles[x][y][0]/2550.0;
				int objectid = maptiles[x][y][1];
				double vertility = maptiles[x][y][2]/255.0;
				Surface s = new Surface(x/2.0, height, y/2.0,0,0,0.25,0.25,new Color(0, 255-(int)(vertility*100), 0));
				s.setName("MapTile (" + x + ","+ y + "@"+height+"): " + objectid);
				objects.add(s);
				if(objectid > 0){
					objects.add(new Triangle3D(x/2.0, height, y/2.0,0,0,0.25,1.0,new Color(255, 0, 0)));
				}
			}
		}
		return objects;
	}
}
