package nl.dannyarends.rendering;

import java.net.URL;
import java.util.ArrayList;
import nl.dannyarends.applet.objects.Texture;
import nl.dannyarends.applet.events.ServerConnection;
import nl.dannyarends.generic.Utils;

/// Lazy loader to load textures via HTTP 
//<p>
//TODO
//</p>
//

public class TextureLoader {
	static ArrayList<Texture> models = new ArrayList<Texture>();
	public static int notfound = 0;
	URL url;

	/**
	 * TextureLoader constructor: Gets a list of textures and initializes empty texture models
	 * 
	 */	
	public TextureLoader(ServerConnection s) {
		String modellist = s.commandToServer("function=list_files&dir=textures&extension=bmp");
		Utils.log("From server: " + (modellist.split("\n").length - 3) + " textures",System.err);
		for(String line : modellist.split("\n")){
			if(!line.startsWith("#") && !line.equals("")){
				Utils.console("We got texture: '"+line+"' from server");
				Texture h = new Texture(line);
				models.add(h);
			}
		}
	}
	
	public static Texture getTexture(String name){
		Texture image = null;
		for(Texture h : models){
			if(h.getName().equalsIgnoreCase(name)){
				if(!h.isLoaded()) h.TryLoadingFromName();
				image = new Texture(h);
			}
		}
		if(image==null){
			notfound++;
			return new Texture("error");
		}
		return image;
	}

	public static int getAvailable(){
		return models.size();
	}
	
	public static int getUsed(){
		int cnt = 0;
		for(Texture h : models){
			if(h.isLoaded()) cnt++;
		}
		return cnt;
	}
}
