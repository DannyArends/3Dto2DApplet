package nl.dannyarends.rendering.objects.hud.windows;

import nl.dannyarends.rendering.objects.hud.HudText;
import nl.dannyarends.rendering.objects.hud.HudWindow;

public class ControlsWindow extends HudWindow {
	HudText t;
	
	public ControlsWindow(int x, int y) {
		super(x, y,400,250, "Controls");
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
