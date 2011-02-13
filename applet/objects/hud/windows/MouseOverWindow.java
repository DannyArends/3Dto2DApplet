package objects.hud.windows;

import objects.hud.HudText;
import objects.hud.HudWindow;

public class MouseOverWindow extends HudWindow{
	HudText displayed;
	String text = "";
	
	public MouseOverWindow(int x, int y) {
		super(x, y,120,20, "MouseOver");
		setVisible(false);
		setActive(true);
		setNeedUpdate(true);
		
	}
	
	public void setText(String t){
		if(t!=null)text=t;
	}
	
	
	public void update(){
		clearChildren();
		displayed = new HudText(0,-25,text);
		addChild(displayed);
	}
}
