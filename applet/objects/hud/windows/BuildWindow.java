package objects.hud.windows;

import java.awt.Color;

import objects.hud.HudButton;
import objects.hud.HudText;
import objects.hud.HudWindow;
import events.ServerConnection;
import generic.Utils;

public class BuildWindow extends HudWindow {
	String[] buildings;
	
	public BuildWindow(ServerConnection s) {
		super(10, 100,200,350, "Build");
		setVisible(true);
		setMinimized(true);
		parseBuildings(s.commandToServer("function=list_buildings"));
	}
	
	
	void parseBuildings(String buildingList){
		buildings = buildingList.split("\n");
		int offset= Utils.offsetByComment(buildings);
		addChild(new HudText(10,10,"Buildings:"));
		for(int x=offset;x<buildings.length;x++){
			addChild(new HudButton(10,20+20*(x-offset),120,18,buildings[x],true,Color.lightGray){
				public void runPayload() {
					Utils.console(getName());
				}
			});
		}
	}
}
