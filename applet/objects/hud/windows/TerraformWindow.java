package objects.hud.windows;

import events.ServerConnection;
import game.TileTypes;
import generic.Utils;

import java.awt.Color;

import objects.hud.HudButton;
import objects.hud.HudText;
import objects.hud.HudWindow;
import rendering.Scene;

public class TerraformWindow extends HudWindow {
	TileTypes t;
	
	public TerraformWindow(ServerConnection s) {
		super(Scene.getWidth()-205, 100,200,350, "Terraform");
		setVisible(false);
		setMinimized(false);
		t= new TileTypes(s);
	}
	
	@Override
	public void onOpen(){
		clearChildren();
		addChild(new HudText(10,10,"Landscaping:"));
		addChild(new HudButton(10,40,120,18,"Raise tile",true,new Color(0,125,0)){
			public void runPayload() {
				Utils.console(getName());
			}
		});
		addChild(new HudButton(10,60,120,18,"Lower tile",true,new Color(0,0,125)){
			public void runPayload() {
				Utils.console(getName());
			}
		});
		addChild(new HudText(10,80,"Tiles:"));
		int n=4;
		for(int x : t.returnAllIndices()){
			Utils.console(""+x);
			addChild(new HudButton(10,30+20*n,120,18,t.getTileName(x),true,t.getTileColor(x)){
				public void runPayload() {
					Utils.console(getName());
				}
			});
			n++;
		}
	}
}
