package rendering;

import java.util.ArrayList;

import objects.hud.HudImage;
import events.ServerConnection;
import generic.Utils;

/// Lazy loader to load icons via HTTP 
//<p>
//TODO
//</p>
//

public class IconLoader {
	String iconlist;
	public static int notfound = 0;
	static ArrayList<HudImage> icons = new ArrayList<HudImage>();
	
	/**
	 * IconLoader constructor: Gets a list of 2D icons and initializes empty icon models
	 * 
	 */	
	IconLoader(ServerConnection s){
		String iconlist = s.commandToServer("function=list_files&dir=icons&extension=png");
		Utils.console("From server: " + (iconlist.split("\n").length - 3) + " icons");
		for(String line : iconlist.split("\n")){
			if(!line.startsWith("#") && !line.equals("")){
				if(Engine.verbose) Utils.console("We got icon: '"+line+"' from server");
				HudImage h = new HudImage(0,0,line);
				h.setVisible(false);
				icons.add(h);
			}
		}
	}
	
	public static HudImage getIcon(int x, int y,String name){
		HudImage image = null;
		for(HudImage h : icons){
			if(h.getName().equalsIgnoreCase(name)){
				if(!h.isLoaded())h.TryLoadingFromName();
				image = new HudImage(h);
				image.setLocation(x, y);
				image.setVisible(false);
			}
		}
		if(image==null){
			notfound++;
			return new HudImage(x, y, "error");
		}
		return image;
	}
	
	public static int getAvailable(){
		return icons.size();
	}
	
	public static int getUsed(){
		int cnt=0;
		for(HudImage h : icons){
			if(h.isLoaded()) cnt++;
		}
		return cnt;
	}
	
}
