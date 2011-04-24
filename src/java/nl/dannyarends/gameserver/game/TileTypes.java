package nl.dannyarends.gameserver.game;

import java.awt.Color;

import nl.dannyarends.applets.gameApplet.events.ServerConnection;
import nl.dannyarends.generic.Utils;

public class TileTypes extends Types{
	static int ntiles;
	static Color[] tileColors;
	static String[] tileNames;
	
	public TileTypes(ServerConnection s) {
		super(s);
		parseTileTypes(connection.commandToServer("function=list_tiles"));
	}

	private void parseTileTypes(String tileTypeInfo) {
		String[] lines = tileTypeInfo.split("\n");
		int offset = Utils.offsetByComment(lines);
		if(offset < 0) return;
		ntiles=lines.length-offset;
		IDs = new int[ntiles];
		tileColors = new Color[ntiles];
		tileNames = new String[ntiles];
		Utils.log("N tiles:" + ntiles, System.err);
		for(int x=0;x<ntiles;x++){
			String[] tiledescr = lines[x+offset].split("\t");
			IDs[x] = Integer.parseInt(tiledescr[0]);
			tileNames[x] = tiledescr[1];
			if(tiledescr.length < 6){
				tileColors[x] = new Color(255,0,0);
			}else{
				tileColors[x] = new Color(Integer.parseInt(tiledescr[3]),Integer.parseInt(tiledescr[4]),Integer.parseInt(tiledescr[5]));
			}
		}
		Utils.console("Tile Types: " + ntiles);
	}
	
	public Color getTileColor(int tileID) {
		if(IDExists(tileID)){
			return tileColors[whichIndexIsID(tileID)];
		}else{
			return new Color(255,0,0);
		}
	}

	public String getTileName(int tileID) {
		if(IDExists(tileID)){
			return tileNames[whichIndexIsID(tileID)];
		}else{
			return "Unknown";
		}
	}

}
