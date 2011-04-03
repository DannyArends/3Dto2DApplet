import java.io.File;

import options.ServerOptions;
import options.OptionsParser;
import webserver.WWWServer;
import generic.Utils;

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
		optionsParser = new OptionsParser((webserverOptions = new ServerOptions("DJWServer.options")));
		optionsParser.parse(webserverOptions.properties);
		WWWServer webserver = new WWWServer();
		new Thread(webserver).start();
	}
	
	static void PrintOutOptions(){
		Utils.console("Websites located in " + webserverOptions.web_dir);
		Utils.console("PHP " + (webserverOptions.php_enabled?"en":"dis") + "abled " + webserverOptions.php_bin_location);
		Utils.console("PERL " + (webserverOptions.php_enabled?"en":"dis") + "abled " + webserverOptions.perl_bin_location);
		Utils.console("PYTHON " + (webserverOptions.php_enabled?"en":"dis") + "abled " + webserverOptions.python_bin_location);
	}
	
	static String setLocalPath(){
		File f = new File("");
		localPath = f.getAbsolutePath();
		return localPath;
	}
}

