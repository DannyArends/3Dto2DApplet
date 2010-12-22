package objects.hud.windows;

import objects.hud.HudText;
import objects.hud.HudWindow;

public class ControlsWindow extends HudWindow {
	HudText t;
	
	public ControlsWindow(int x, int y) {
		super(x, y, "Controls");
		setSize(400,250);
		setVisible(false);
		t = new HudText(10,10,"Click and move mouse to look around");
		t.addLine("[Left]       step left");
		t.addLine("[Right]      step right");
		t.addLine("[Down]       step back");
		t.addLine("[Up]         step forward");
		t.addLine("[Page Up]    float up");
		t.addLine("[Page Up]    float up");
		t.addLine("[M]          Toggle between model only view");
		t.addLine("[+]/[-]      Increade/Decrease LOD score Cut-off");
		addChild(t);
	}

}
