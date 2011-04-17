package nl.dannyarends.applet.objects.hud.windows;

import java.awt.Color;

import nl.dannyarends.applet.objects.hud.HudInputBox;
import nl.dannyarends.applet.objects.hud.HudText;
import nl.dannyarends.applet.objects.hud.HudWindow;
import nl.dannyarends.rendering.IconLoader;
import nl.dannyarends.rendering.Scene;

public class LoginWindow extends HudWindow {
	
	public LoginWindow() {
		super(Scene.getWidth()/2-200,Scene.getHeight()/2-100,325,120, "Login");
		setColor(new Color(100,100,0));
		HudText t = new HudText(10,-25,"Login to the system");
		t.addLine("Username: ");
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
