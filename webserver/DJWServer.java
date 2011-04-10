import java.io.File;

import webserver.WWWServer;
import generic.Utils;
import generic.options.OptionsPackage;
import generic.options.OptionsParser;
import generic.options.ServerOptions;

/// basic web server GUI version of the web server
//<p>
//We used a custom version of the Molgenis options reader to load the basic options provided for the web server
//For all of them we have default setting
//</p>
//

public class DJWServer {
	static ServerOptions webserverOptions;
	static OptionsParser optionsParser;
	static String localPath;
	
	public static void main(String[] args) throws Exception{
		Utils.log("-- Starting webserver " + setLocalPath() + "--",System.err);
		webserverOptions = new ServerOptions();
		webserverOptions.load("DJWServer.options");
		optionsParser = new OptionsParser((OptionsPackage) webserverOptions);
		optionsParser.parse(webserverOptions.properties);
		WWWServer webserver = new WWWServer();
		new Thread(webserver).start();
	}
	
	static void PrintOutOptions(){
		Utils.console("Websites located in " + ServerOptions.web_dir);
		Utils.console("PHP " + (ServerOptions.php_enabled?"en":"dis") + "abled " + ServerOptions.php_bin_location);
		Utils.console("PERL " + (ServerOptions.php_enabled?"en":"dis") + "abled " + ServerOptions.perl_bin_location);
		Utils.console("PYTHON " + (ServerOptions.php_enabled?"en":"dis") + "abled " + ServerOptions.python_bin_location);
	}
	
	static String setLocalPath(){
		File f = new File("");
		localPath = f.getAbsolutePath();
		return localPath;
	}
}

