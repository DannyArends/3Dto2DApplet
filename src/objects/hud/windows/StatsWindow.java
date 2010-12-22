package objects.hud.windows;

import objects.hud.HudText;
import objects.hud.HudWindow;
import rendering.Engine;
import rendering.Hud;
import rendering.IconLoader;
import rendering.Scene;

public class StatsWindow extends HudWindow {
	
	
	public StatsWindow(int x, int y) {
		super(x, y, 325, 200, "Stats");
		update();
		setVisible(false);
		setNeedUpdate(true);
	}
	
	@Override
	public void update(){
		clearChildren();
		HudText t = new HudText(10,10,"Verbose: " + Engine.verbose);
		t.addLine("Objects (current,softlimit) : (" + Scene.getObjects().size() + "/" + Scene.softmyobjectslimit + ")");
		t.addLine("HUD windows (visible/hidden): (" + Hud.getVisibleWindows().size() + "/" + Hud.getHudWindows().size() + ")");
		t.addLine("Images (used/available-notfound): " + IconLoader.getUsedIcons().size() + "/" + IconLoader.getAvailableIcons().size() + "-" + IconLoader.notfound);
		t.addLine("Camera Location (x,y,z:H/V): " + Scene.getCamera().x + "," + Scene.getCamera().y + "," + Scene.getCamera().z + ":" + Scene.getCamera().getHorizontalRotation() +"/"+ Scene.getCamera().getVerticalRotation());		
		addChild(t);
	}
}
