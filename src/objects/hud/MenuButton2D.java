package objects.hud;

import events.ButtonControler;
import generic.Utils;

import java.util.Vector;

public class MenuButton2D extends Button2D{

	Vector<Button2D> children = new Vector<Button2D>();
	
	public MenuButton2D(int x, int y, String name) {
		super(x, y, name);
		ChildMenu(x,y);
	}
	
	public void ChildMenu(int x, int y){
		children.add(new Button2D(x,y+20,"Button1"));
		children.add(new Button2D(x,y+35,"Button2"));
		children.add(new Button2D(x,y+50,"Button3"));
		children.add(new Button2D(x,y+65,"Button4"));
	}
	
	@Override
	public void runPayload() {
		Utils.console("MenuButton at:"+x+","+y+"clicked");
		ButtonControler.addButtons(children);
	}
}
