package nl.dannyarends.rendering.objects.hud.windows;

import nl.dannyarends.rendering.IconLoader;
import nl.dannyarends.rendering.objects.hud.HudText;
import nl.dannyarends.rendering.objects.hud.HudWindow;

public class HelpWindow extends HudWindow {
	HudText t;
	
	public HelpWindow(int x, int y) {
		super(x, y,400, 125, "Help");
		t = new HudText(10,10,"Open Triangle: QTL at marker");
		t.addLine("Filled triangles: Selected Cofactor at marker");
		t.addLine("Triangle size: QTL effect/likelihood");
		addChild(t);
		addChild(IconLoader.getIcon(300,10,"help.png"));
	}

}
