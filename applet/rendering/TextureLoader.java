package rendering;

import java.net.URL;
import java.util.Vector;

import objects.Texture;
import events.ServerConnection;
import generic.Utils;

public class TextureLoader {
	static Vector<Texture> models = new Vector<Texture>();
	public static int notfound = 0;
	URL url;

	public TextureLoader(ServerConnection s) {
		String modellist = s.commandToServer("list_files=bmp&dir=textures");
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
