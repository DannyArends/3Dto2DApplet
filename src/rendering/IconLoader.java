package rendering;

import java.util.Vector;

import objects.hud.HudImage;

import events.ServerConnection;
import generic.Utils;

public class IconLoader {
	String iconlist;
	static Vector<HudImage> icons = new Vector<HudImage>();
	
	IconLoader(ServerConnection s){
		String iconlist = s.commandToServer("list_files=png&dir=icons");
		Utils.console("From server: " + iconlist.split("\n").length + " icons");
		for(String line : iconlist.split("\n")){
			if(!line.startsWith("#") && !line.equals("")){
				if(Engine.verbose) Utils.console("We got icon: '"+line+"'from server");
				HudImage h = new HudImage(0,0,line);
				h.setVisible(false);
				icons.add(h);
			}
		}
	}
	
	static HudImage getIcon(int x, int y,String name){
		HudImage image = null;
		for(HudImage h : icons){
			if(h.getName().equalsIgnoreCase(name)){
				image = new HudImage(h);
				image.setLocation(x, y);
				image.setVisible(true);
			}
		}
		return image;
	}
}
