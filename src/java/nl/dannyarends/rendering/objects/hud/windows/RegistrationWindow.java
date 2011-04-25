package nl.dannyarends.rendering.objects.hud.windows;

import java.awt.Color;

import nl.dannyarends.rendering.IconLoader;
import nl.dannyarends.rendering.Scene;
import nl.dannyarends.rendering.objects.hud.HudInputBox;
import nl.dannyarends.rendering.objects.hud.HudText;
import nl.dannyarends.rendering.objects.hud.HudWindow;



public class RegistrationWindow extends HudWindow {
	
	public RegistrationWindow() {
		super(Scene.getWidth()/2-200,Scene.getHeight()/2-100,325,120, "Register");
		setColor(new Color(0,200,0));
		HudText t = new HudText(10,-25,"Register own map");
		t.addLine("User/map name: ");
		t.addLine("Password: ");
		addChild(t);
		addChild(new HudInputBox(100,0,10,"n"));
		addChild(new HudInputBox(100,25,10,"p"));
		addChild(IconLoader.getIcon(25,50,"arrow_right.png"));
		setShowTopMenu(false);
		setActive(true);
		setVisible(false);
		setNeedUpdate(true);
	}
}
