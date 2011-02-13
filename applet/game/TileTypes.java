package game;

import java.awt.Color;

import events.ServerConnection;
import generic.Utils;

public class TileTypes extends Types{
	static int ntiles;
	static Color[] tilecolors;
	
	TileTypes(ServerConnection s) {
		super(s);
		parseTileTypes(connection.commandToServer("function=list_tiles"));
	}

	private void parseTileTypes(String tileTypeInfo) {
		String[] lines = tileTypeInfo.split("\n");
		int offset = Utils.offsetByComment(lines);
		ntiles=lines.length-offset;
		IDs = new int[ntiles];
		tilecolors = new Color[ntiles];
		for(int x=0;x<ntiles;x++){
			String[] tiledescr = lines[x+offset].split("\t");
			IDs[x] = Integer.parseInt(tiledescr[0]);
			if(tiledescr.length < 6){
				tilecolors[x] = new Color(255,0,0);
			}else{
				tilecolors[x] = new Color(Integer.parseInt(tiledescr[3]),Integer.parseInt(tiledescr[4]),Integer.parseInt(tiledescr[5]));
			}
		}
		Utils.console("Tile Types: " + ntiles);
	}
	
	public static Color getTileColor(int tileID) {
		if(tileID < ntiles){
			return tilecolors[tileID];
		}else{
			return new Color(255,0,0);
		}
	}

}
