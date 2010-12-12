package events;

import game.Scene;

import java.awt.Graphics2D;
import java.util.Vector;

import objects.hud.Button2D;
import objects.hud.InputBox;
import objects.hud.MenuButton2D;

public class ButtonControler {
	
	static Vector<Button2D> monitored = new Vector<Button2D>();
	public ButtonControler(){
		addMainMenu();
	}
	
	public static void checkLocation(int x,  int y){
		for(Button2D b : monitored){
			//Utils.console(""+x+","+y+"=="+b.x+","+b.y);
			if(b.x < x && b.y < y){
				if(b.getAbsoluteSizeX() > x && b.getAbsoluteSizeY() > y){
					//resetButtons();
					b.runPayload();
					//Scene.reDrawScene();
					//Scene.updateScene();
					break;
				}	
			}
		}
	}
	
	public static void resetButtons(){
		monitored.clear();
		addMainMenu();
	}
	
	public static void addMainMenu(){
		addButton(new MenuButton2D(0,0,"File"));
		addButton(new MenuButton2D(60,0,"Edit"));
		addButton(new MenuButton2D(120,0,"View"));
		addButton(new Button2D(180,0,"Help"));
		addButton(new InputBox(240,0,10));
	}
	
	public static void rightClickMenu(int x, int y){
		resetButtons();
		addButton(new Button2D(x,y+00,"Button1"));
		addButton(new Button2D(x,y+20,"Button2"));
		addButton(new Button2D(x,y+40,"Button3"));
		addButton(new Button2D(x,y+60,"Button4"));
		Scene.reDrawScene();
		Scene.updateScene();
	}
	
	synchronized public static void addButton(Button2D b){
		monitored.add(b);
	}

	public void render(Graphics2D g) {
		for(Button2D b : monitored){
			b.render(g);
		}
	}

	public static void addButtons(Vector<Button2D> children) {
		resetButtons();
		for(Button2D b : children){
			addButton(b);
		}
		Scene.reDrawScene();
		Scene.updateScene();
	}
}
