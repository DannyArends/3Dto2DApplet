package webserver;

import webserver.servlets.FileServlet;
import webserver.servlets.CGIServlet;


public class WWWServer extends Webserver implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public WWWServer(){
		Webserver.PathTreeDictionary aliases = new Webserver.PathTreeDictionary();
        aliases.put("/", new java.io.File("."));
        setMappingTable(aliases);
        
        //My own homepage
        addServlet("/cgi-bin", new CGIServlet("homepage",true));
        addServlet("/", new CGIServlet("homepage",false));
        
        //Another website
        addServlet("/", new FileServlet("qtlocator"),"www.QTLocator.nl");
        
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
