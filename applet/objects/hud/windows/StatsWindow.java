package objects.hud.windows;

import events.ServerConnection;
import objects.hud.HudText;
import objects.hud.HudWindow;
import rendering.Engine;
import rendering.Hud;
import rendering.IconLoader;
import rendering.Object3DSLoader;
import rendering.Scene;

public class StatsWindow extends HudWindow {
	String serverresponse;
	
	public StatsWindow(int x, int y, ServerConnection s) {
		super(x, y, 325, 200, "Stats");
		update();
		setVisible(false);
		setNeedUpdate(true);
		serverresponse = s.commandToServer("function=online");
	}
	
	@Override
	public void update(){
		clearChildren();
		HudText t = new HudText(10,10,"Verbose: " + Engine.verbose);
		t.addLine("Objects (current,softlimit) : " + Scene.getObjects().size() + "/" + Scene.getSoftmyobjectslimit() + "");
		t.addLine("Rendering Time (Hud, 3D Scene) : " + Scene.getHudTime() + " ms/" + Scene.getRenderTime() + " ms");
		t.addLine("HUD windows (visible/hidden): (" + Hud.getVisibleWindows().size() + "/" + Hud.getHudWindows().size() + ")");
		t.addLine("Images (used/available-notfound): " + IconLoader.getUsed() + "/" + IconLoader.getAvailable() + "-" + IconLoader.notfound);
		t.addLine("Objects (used/available-notfound): " + Object3DSLoader.getUsed() + "/" + Object3DSLoader.getAvailable() + "-" + Object3DSLoader.notfound);
		t.addLine("Camera Location (x,y,z:H/V): " + Scene.getCamera().location[0] + "," + Scene.getCamera().location[1] + "," + Scene.getCamera().location[2] + ":" + Scene.getCamera().getHorizontalRotation() +"/"+ Scene.getCamera().getVerticalRotation());		
		t.addLine("Server Traffic (up/down): " + (int)ServerConnection.up/1024 + " kb/" + (int)ServerConnection.down/1024 + " kb");
		t.addLine(serverresponse);
		addChild(t);
	}
}
