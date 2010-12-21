package objects.hud;

import generic.Utils;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import rendering.Engine;


public class HudImage extends HudButton{
	ImageIcon i;
	URL url;
	
	public HudImage(HudImage h) {
		super((int)h.x, (int)h.y, h.getName());
		i=h.i;
		url=h.url;
	}
	
	public HudImage(int x, int y,String name) {
		super((int)x, (int)y, name);
		TryLoadingFromName(name);
	}
	
	public HudImage(int x, int y,String name,HudObject p) {
		super((int)x, (int)y, name,p);
		Utils.console(name);
		TryLoadingFromName(name);
	}
	
	void TryLoadingFromName(String name){
		try {
			Utils.console(Engine.getParentApplet().getCodeBase().toString()	+ "data/icons/" + name);
			url = new URL(Engine.getParentApplet().getCodeBase().toString()	+ "data/icons/" + name); 
			i= new ImageIcon(url);
		}catch (Exception e) {
			Utils.log("No such image at:" + Engine.getParentApplet().getCodeBase().toString() + name , System.out);
		}	
	}

	@Override
	public void render(Graphics2D g) {
		  if(isVisible()){
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
