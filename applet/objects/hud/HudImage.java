package objects.hud;

import generic.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

import rendering.Engine;
import rendering.Hud;


public class HudImage extends HudButton{
	ImageIcon i;
	URL url;
	boolean loaded = false;
	
	public boolean isLoaded() {
		return loaded;
	}

	public HudImage(HudImage h) {
		super((int)h.x, (int)h.y);
		setName(h.getName().substring(0, h.getName().indexOf('.')));
		setSize(50,50);
		i=h.i;
		loaded=h.loaded;
		url=h.url;
	}
	
	public HudImage(int x, int y,String name) {
		super((int)x, (int)y);
		setName(name);
		setSize(50,50);
	}
	
	public HudImage(int x, int y, String name, HudObject p) {
		super((int)x, (int)y, p);
		setName(name);
		setSize(50,50);
	}
	
	public void TryLoadingFromName(){
		try {
			Utils.console(Engine.getParentApplet().getCodeBase().toString()	+ "data/icons/" + getName());
			url = new URL(Engine.getParentApplet().getCodeBase().toString()	+ "data/icons/" + getName()); 
			i= new ImageIcon(url);
			setSize(50,50);
			loaded=true;
		}catch (Exception e) {
			Utils.log("No such image at:" + Engine.getParentApplet().getCodeBase().toString() + getName() , System.out);
			loaded=false;
		}	
	}

	@Override
	public void render(Graphics2D g) {
		  if(isLoaded() && isVisible()){
			 g.drawImage(i.getImage(), (int)x, (int)y,i.getIconWidth(),i.getIconHeight(), Engine.getParentApplet());
		  }else{
			 Hud.drawBox(g, (int)x, (int)y, 50, 50, Color.red);
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
