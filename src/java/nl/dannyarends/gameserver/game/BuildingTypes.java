package nl.dannyarends.gameserver.game;

import java.awt.Color;

import nl.dannyarends.applets.gameApplet.events.ServerConnection;
import nl.dannyarends.generic.Utils;

public class BuildingTypes extends Types{
	static int nbuildings;
	static String[] buildingNames;
	static Color[] buildingColors;
	
	public BuildingTypes(ServerConnection s) {
		super(s);
		parseTileTypes(connection.commandToServer("function=list_buildings"));
	}

	private void parseTileTypes(String buildingTypeInfo) {
		String[] lines = buildingTypeInfo.split("\n");
		int offset = Utils.offsetByComment(lines);
		if(offset < 0) return;
		nbuildings=lines.length-offset;
		IDs = new int[nbuildings];
		buildingColors = new Color[nbuildings];
		buildingNames = new String[nbuildings];
		for(int x=0;x<nbuildings;x++){
			String[] buildingdescr = lines[x+offset].split("\t");
			IDs[x] = Integer.parseInt(buildingdescr[0]);
			buildingNames[x] = buildingdescr[1];
			if(buildingdescr.length < 6){
				buildingColors[x] = new Color(255,0,0);
			}else{
				buildingColors[x] = new Color(Integer.parseInt(buildingdescr[3]),Integer.parseInt(buildingdescr[4]),Integer.parseInt(buildingdescr[5]));
			}
		}
		Utils.console("Building Types: "+nbuildings);
	}
	
	public Color getBuildingColor(int buildingID) {
		if(IDExists(buildingID)){
			return buildingColors[whichIndexIsID(buildingID)];
		}else{
			return new Color(255,0,0);
		}
	}

	public String getBuildingName(int buildingID) {
		if(IDExists(buildingID)){
			return buildingNames[whichIndexIsID(buildingID)];
		}else{
			return "Unknown";
		}
	}
	
	public boolean buildingExists(int id){
		if(id==0) return false;
		Utils.console("Going to check: " + id);
		for(int x : IDs){
			if(x==id) return true;
		}
		return false;
	}
}
