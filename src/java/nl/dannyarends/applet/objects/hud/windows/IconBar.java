package nl.dannyarends.applet.objects.hud.windows;

import nl.dannyarends.applet.objects.hud.HudWindow;
import nl.dannyarends.rendering.Engine;
import nl.dannyarends.rendering.IconLoader;

public class IconBar extends HudWindow {
	
	
	public IconBar() {
		super(0,Engine.getHeight()-100,650,100, "IconBar");
		addChild(IconLoader.getIcon(25,0,"user_id.png","Register"));
		addChild(IconLoader.getIcon(75,0,"walk.png"));
		addChild(IconLoader.getIcon(125,0,"world.png"));
		addChild(IconLoader.getIcon(175,0,"fight.png"));
		addChild(IconLoader.getIcon(225,0,"magic.png"));
		addChild(IconLoader.getIcon(275,0,"leaf.png","Terraform"));
		addChild(IconLoader.getIcon(325,0,"hammer.png","Build"));
		addChild(IconLoader.getIcon(375,0,"object.png"));
		addChild(IconLoader.getIcon(450,0,"settings.png"));
		addChild(IconLoader.getIcon(500,0,"stats.png"));
		addChild(IconLoader.getIcon(550,0,"help.png"));
		//addChild(IconLoader.getIcon(600,0,"notthere.png"));
		setShowTopMenu(false);
		setActive(true);
		setVisible(true);
	}
}
