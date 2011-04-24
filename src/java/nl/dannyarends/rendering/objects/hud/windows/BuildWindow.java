package nl.dannyarends.rendering.objects.hud.windows;

import nl.dannyarends.applet.events.ServerConnection;
import nl.dannyarends.gameserver.game.BuildingTypes;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.objects.hud.HudButton;
import nl.dannyarends.rendering.objects.hud.HudText;
import nl.dannyarends.rendering.objects.hud.HudWindow;

public class BuildWindow extends HudWindow {
	BuildingTypes b;
	public BuildWindow(ServerConnection s) {
		super(10, 100,200,350, "Build");
		setVisible(false);
		setMinimized(false);
		b= new BuildingTypes(s);
	}
	
	@Override
	public void onOpen(){
		clearChildren();
		addChild(new HudText(10,10,"Buildings:"));
		int n=1;
		//TODO: Group the buildings and show with tabs or something
		for(int x : b.returnAllIndices()){
			addChild(new HudButton(10,20+20*n,120,18,b.getBuildingName(x),true,b.getBuildingColor(x)){
				public void runPayload() {
					Utils.console(getName());
				}
			});
			n++;
		}
	}
}
