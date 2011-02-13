package objects.hud.windows;

import java.awt.Color;

import objects.hud.HudInputBox;
import objects.hud.HudText;
import objects.hud.HudWindow;
import rendering.IconLoader;
import rendering.Scene;

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
