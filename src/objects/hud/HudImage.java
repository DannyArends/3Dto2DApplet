package objects.hud;

import generic.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

import objects.Point2D;

import rendering.Engine;
import rendering.Hud;


public class HudImage extends HudButton{
	ImageIcon i;
	URL url;
	
	public HudImage(HudImage h) {
		super((int)h.x, (int)h.y);
		setName(h.getName().substring(0, h.getName().indexOf('.')));
		setSize(new Point2D(50,100));
		i=h.i;
		url=h.url;
	}
	
	public HudImage(int x, int y,String name) {
		super((int)x, (int)y);
		setName(name);
		setSize(new Point2D(50,100));
		TryLoadingFromName(name);
	}
	
	public HudImage(int x, int y, String name, HudObject p) {
		super((int)x, (int)y, p);
		setName(name);
		setSize(new Point2D(50,100));
		TryLoadingFromName(name);
	}
	
	void TryLoadingFromName(String name){
		try {
			if(Engine.verbose) Utils.console(Engine.getParentApplet().getCodeBase().toString()	+ "data/icons/" + name);
			url = new URL(Engine.getParentApplet().getCodeBase().toString()	+ "data/icons/" + name); 
			i= new ImageIcon(url);
			setSize(new Point2D(50,100));
		}catch (Exception e) {
			Utils.log("No such image at:" + Engine.getParentApplet().getCodeBase().toString() + name , System.out);
		}	
	}

	@Override
	public void render(Graphics2D g) {
		  if(isVisible()){
			 // Utils.console(getName() + " = " + isVisible() + " " + x + " " + y);
			  //Hud.drawBox(g, (int)x, (int)y, (int)50,50, Color.green);
			  g.drawImage(i.getImage(), (int)x, (int)y, Engine.getParentApplet());
		  }
		
	}

	@Override
	public void handleKeystroke(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleSlide(int mx, int my) {
		// TODO Auto-generated method stub
		return false;
	}

}
