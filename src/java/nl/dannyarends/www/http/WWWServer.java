package nl.dannyarends.www.http;

import java.util.Map;

import nl.dannyarends.www.http.servlets.AdminServlet;
import nl.dannyarends.www.http.servlets.CGIServlet;


public class WWWServer extends Webserver implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public WWWServer(Map<String,Object> arguments){
		super(arguments,System.out);
		Webserver.PathTreeDictionary aliases = new Webserver.PathTreeDictionary();
	    aliases.put("/", new java.io.File("."));
	    setMappingTable(aliases);
	    
	    //Admin
	    addServlet("/admin/tests", new CGIServlet("admin/tests",false));
	    addServlet("/admin", new AdminServlet());
	}
	
	public WWWServer(){
		Webserver.PathTreeDictionary aliases = new Webserver.PathTreeDictionary();
	    aliases.put("/", new java.io.File("."));
	    setMappingTable(aliases);
	    
	    //Admin
	    addServlet("/admin/tests", new CGIServlet("admin/tests",false));
	    addServlet("/admin", new AdminServlet());
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
