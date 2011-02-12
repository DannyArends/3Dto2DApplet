package game;

import java.awt.Color;
import java.util.ArrayList;

import objects.renderables.Object3D;
import objects.renderables.Surface;
import objects.renderables.Triangle3D;
import rendering.Scene;
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
		int offset = Utils.offsetByComment(dimension1);
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
				double height = maptiles[x][y][0]/500.0;
				int objectid = maptiles[x][y][1];
				//double vertility = maptiles[x][y][2]/255.0;
				int c = (int)((height*100)>255?255:height*100);
				Surface s = new Surface(x, height, y,0,0,0.4,0.4,new Color(0, c, 255-(int)c));
				s.setName("MapTile (" + x + ","+ y + "@"+height+"): " + objectid);
				objects.add(s);
				if(objectid > 0){
					objects.add(new Triangle3D(x, height, y,0,0,0.5,1, new Color(125, 35, 125)));
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
		for(int t=0;t<tiledimension;t++){
			maptiles[x][y][t] = Integer.parseInt(tile[t]);
		}
	}
	
	public void update_tile(int x,int y,int tile,int value){
		parseTileResponse(x,y,server.commandToServer("function=update_tile&p1="+mapname+"&p2="+x+"&p3="+y+"&p4="+tile+"&p5="+value));
		Scene.mapReload();
	}
	
	public int[] get_tile(int x,int y){
		return maptiles[x][y];
	}
	
	public void update_map(){
		server.commandToServer("function=update_map&p1="+mapname);
		Scene.mapReload();
	}
}
