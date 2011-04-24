package nl.dannyarends.rendering.objects.hud.windows;

import nl.dannyarends.rendering.objects.hud.HudWindow;

public class SettingsWindow extends HudWindow {
	public SettingsWindow(int x, int y) {
		super(x, y,200,100, "Settings");
		setVisible(false);
	}
}
