package webserver;

import webserver.servlets.FileServlet;
import webserver.servlets.CGIServlet;


public class WWWServer extends Webserver implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public WWWServer(){
		Webserver.PathTreeDictionary aliases = new Webserver.PathTreeDictionary();
        aliases.put("/", new java.io.File("."));
        setMappingTable(aliases);
        
        //Localhost (No Hostname is used)
        addServlet("/cgi-bin", new CGIServlet("applet",true),"localhost");
        addServlet("/", new FileServlet("applet"),"localhost");
        
        //My own homepage
        addServlet("/", new CGIServlet("homepage",false),"www.dannyarends.nl");
        
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
