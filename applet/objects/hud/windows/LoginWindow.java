package objects.hud.windows;

import java.awt.Color;

import objects.hud.HudInputBox;
import objects.hud.HudText;
import objects.hud.HudWindow;
import rendering.Engine;
import rendering.IconLoader;

public class LoginWindow extends HudWindow {
	
	
	public LoginWindow() {
		super(Engine.width/2-200,Engine.height/2-100,325,200, "Login");
		setColor(Color.orange);
		HudText t = new HudText(10,10,"Login to the system");
		t.addLine("Username: ");
		t.addLine("Password: ");
		addChild(t);
		addChild(new HudInputBox(100,32,10));
		addChild(new HudInputBox(100,52,10));
		addChild(IconLoader.getIcon(25,75,"arrow_right.png"));
		addChild(IconLoader.getIcon(25,120,"user_add.png"));
		setShowTopMenu(false);
		setActive(true);
		setVisible(false);
		setNeedUpdate(true);
	}
}
