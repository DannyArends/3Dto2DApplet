package objects.hud.windows;

import objects.hud.HudInputBox;
import objects.hud.HudText;
import objects.hud.HudWindow;
import objects.renderables.Object3D;
import rendering.IconLoader;

public class ObjectWindow extends HudWindow {
	HudText t;
	
	public ObjectWindow(int x, int y) {
		super(x, y,400,125, "Object");
		setVisible(false);
		t = new HudText(10,10,"Object");
		addChild(t);
		addChild(IconLoader.getIcon(300,10,"signup.png"));
	}
	
	public ObjectWindow(int x, int y,int sx, int sy, Object3D object) {
		super(x, y,sx,sy, "Object");
		setTarget(object);
		setVisible(true);
		t = new HudText(10,10,"Object:" + object.getName());
		addChild(t);
		addChild(new HudText(10,45,"Location:"));
		addChild(new HudInputBox(75, 45, 3,object.getLocation()[0],"x"));
		addChild(new HudInputBox(125, 45, 3,object.getLocation()[1],"y"));
		addChild(new HudInputBox(175, 45, 3,object.getLocation()[2],"z"));
		addChild(new HudText(10,70,"Rotation:"));
		addChild(new HudInputBox(75, 70, 3,object.getHorizontalRotation(),"h"));
		addChild(new HudInputBox(125, 70, 3,object.getHorizontalRotation(),"v"));
		addChild(new HudText(10,95,"Material:"));
		addChild(new HudInputBox(75, 95, 5,0.0,"m"));
	}

}
