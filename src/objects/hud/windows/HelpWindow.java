package objects.hud.windows;

import objects.hud.HudImage;
import objects.hud.HudText;
import objects.hud.HudWindow;

public class HelpWindow extends HudWindow {
	HudText t;
	
	public HelpWindow(int x, int y) {
		super(x, y, "Help");
		setSize(400,125);
		setVisible(false);
		t = new HudText(10,10,"Open Triangle: QTL at marker");
		t.addLine("Filled triangles: Selected Cofactor at marker");
		t.addLine("Triangle size: QTL effect/likelihood");
		addChild(t);
		addChild(new HudImage(300,10,"help.png"));
	}

}
