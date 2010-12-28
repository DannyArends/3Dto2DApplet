import generic.Utils;

import webserver.WWWServer;



public class StandAlone {
	static WWWServer webserver = new WWWServer();
	
	public static void main(String[] args) throws Exception{
		Utils.log("-- Starting webserver --",System.err);
		new Thread(webserver).start();
		Utils.log("-- Serving: http://localhost:8080/ --",System.err);
	}
}
