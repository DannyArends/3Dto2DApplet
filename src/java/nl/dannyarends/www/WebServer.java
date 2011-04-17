package nl.dannyarends.www;

import java.io.File;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.OptionsPackage;
import nl.dannyarends.options.OptionsParser;
import nl.dannyarends.options.ServerOptions;
import nl.dannyarends.www.http.WWWServer;

/// basic web server GUI version of the web server
//<p>
//We used a custom version of the Molgenis options reader to load the basic options provided for the web server
//For all of them we have default setting
//</p>
//

public class WebServer {
	static ServerOptions webserverOptions;
	static DatabaseOptions databaseOptions;
	static OptionsParser optionsParser;
	static String localPath;
	
	public static void main(String[] args) throws Exception{
		Utils.log("-- Starting webserver " + setLocalPath() + "--",System.err);
		webserverOptions = new ServerOptions("settings/www.properties");
		databaseOptions = new DatabaseOptions("settings/db.properties");
		optionsParser = new OptionsParser();
		optionsParser.parse((OptionsPackage) webserverOptions);
		optionsParser.parse((OptionsPackage) databaseOptions);
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

