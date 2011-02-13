package objects.hud.windows;

import java.awt.Color;

import events.ServerConnection;

import generic.Utils;
import objects.hud.HudButton;
import objects.hud.HudText;
import objects.hud.HudWindow;
import rendering.Scene;

public class TerraformWindow extends HudWindow {
	String[] tiles;
	
	public TerraformWindow(ServerConnection s) {
		super(Scene.getWidth()-205, 100,200,350, "Terraform");
		setVisible(false);
		setMinimized(false);
		addChild(new HudButton(10,20,120,18,"Raise tile",true,new Color(0,125,0)){
			public void runPayload() {
				Utils.console(getName());
			}
		});
		addChild(new HudButton(10,40,120,18,"Lower tile",true,new Color(0,0,125)){
			public void runPayload() {
				Utils.console(getName());
			}
		});
		parseTiles(s.commandToServer("function=list_tiles"));
	}
	
	
	void parseTiles(String tilesList){
		tiles = tilesList.split("\n");
		int offset= Utils.offsetByComment(tiles);
		addChild(new HudText(10,60,"Tiles:"));
		for(int x=offset+1;x<tiles.length;x++){
			addChild(new HudButton(10,60+20*(x-offset),120,18,tiles[x],true,Color.lightGray){
				public void runPayload() {
					Utils.console(getName());
				}
			});
		}
	}
}
