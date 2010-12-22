package objects.hud.windows;

import objects.hud.HudImage;
import objects.hud.HudText;
import objects.hud.HudWindow;

public class AboutWindow extends HudWindow {
	HudText t;
	
	public AboutWindow(int x, int y) {
		super(x, y, "About");
		setSize(400,125);
		setVisible(false);
		t = new HudText(10,10,"QTL viewing applet");
		t.addLine("Part of the iqtl package");
		t.addLine("(c) 2010 Danny Arends - GBIC");
		t.addLine("https://github.com/DannyArends/3Dto2DApplet");
		addChild(t);
		addChild(new HudImage(300,10,"signup.png"));
	}

}
