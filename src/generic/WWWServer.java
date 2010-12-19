package generic;

import java.io.File;
import java.net.URL;

import rendering.Engine;

public class WWWServer extends Webserver implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public WWWServer(){
		Utils.console("Starting server");
		Webserver.PathTreeDictionary aliases = new Webserver.PathTreeDictionary();
      
		//ALIASES TO FILESYSTEM
		try {
			aliases.put("/", new File(new URL(Engine.getParentApplet().getCodeBase().toString()	+ "data").toURI()));
			setMappingTable(aliases);
			addServlet("/", "generic.servlets.FileServlet");	//Serving Static Content
		}catch (Exception e) {
			Utils.log("URLException", e);
		}
	}

	
	public void run(){
		try {
			serve();
			return;
		} catch (Exception e) {
			log("ERROR [http server] ",e);
			e.printStackTrace();
			return;
		}
	 }
	
}
