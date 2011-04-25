package nl.dannyarends.rendering.objects.hud.windows;

import nl.dannyarends.rendering.IconLoader;
import nl.dannyarends.rendering.objects.hud.HudText;
import nl.dannyarends.rendering.objects.hud.HudWindow;

public class AboutWindow extends HudWindow {
	HudText t;
	
	public AboutWindow(int x, int y) {
		super(x, y,400,125, "About");
		
		t = new HudText(10,10,"QTL viewing applet");
		t.addLine("Part of the iqtl package");
		t.addLine("(c) 2010 Danny Arends - GBIC");
		t.addLine("https://github.com/DannyArends/3Dto2DApplet");
		addChild(t);
		addChild(IconLoader.getIcon(300,10,"signup.png"));
	}

}
