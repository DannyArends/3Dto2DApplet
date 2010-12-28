package webserver;

import webserver.servlets.FileServlet;
import webserver.servlets.PerlServlet;


public class WWWServer extends Webserver implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public WWWServer(){
		Webserver.PathTreeDictionary aliases = new Webserver.PathTreeDictionary();
        aliases.put("/", new java.io.File("."));
        setMappingTable(aliases);
        addServlet("/cgi-bin", new PerlServlet());
        addServlet("/", new FileServlet());
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
