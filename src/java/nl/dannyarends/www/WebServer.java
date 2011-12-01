package nl.dannyarends.www;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nl.dannyarends.generic.Utils;
import nl.dannyarends.ircclient.IRCHandler;
import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.GeneratorOptions;
import nl.dannyarends.options.OptionsPackage;
import nl.dannyarends.options.OptionsParser;
import nl.dannyarends.options.WebOptions;
import nl.dannyarends.www.http.WWWServer;
import nl.dannyarends.www.http.Webserver;
import nl.dannyarends.www.http.servlets.CGIServlet;
import nl.dannyarends.www.http.servlets.FileServlet;
import nl.dannyarends.www.http.servlets.MovedServlet;
import nl.dannyarends.www.http.servlets.RplotServlet;

/**
 * \brief Main webserver method<br>
 *
 * We used a custom version of the Molgenis options reader to load the basic options 
 * provided for the web server, for all of them we have default setting.
 * bugs: none found<br>
 */
public class WebServer {
	static WebOptions webserverOptions;
	static DatabaseOptions databaseOptions;
	static GeneratorOptions generatorOptions;
	static OptionsParser optionsParser;
	static IRCHandler botentry = new IRCHandler();
	static String localPath;
		
	public static void main(String[] args) throws Exception{
		Utils.log("-- Parsing properties --",System.err);
		webserverOptions = new WebOptions("settings/www.properties");
		databaseOptions = new DatabaseOptions("settings/db.properties");
		generatorOptions = new GeneratorOptions("settings/generator.properties");
		optionsParser = new OptionsParser();
		optionsParser.parse((OptionsPackage) webserverOptions);
		optionsParser.parse((OptionsPackage) databaseOptions);
		optionsParser.parse((OptionsPackage) generatorOptions);
		Utils.log("-- Starting WebServer " + setLocalPath() + "--",System.err);
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put(Webserver.ARG_PORT, 80);
		WWWServer webserver = new WWWServer(properties);
		webserver.addServlet("/pdf", new FileServlet("homepage/pdf"),"www.dannyarends.nl");
		webserver.addServlet("/cgi-bin", new CGIServlet("homepage",true),"www.dannyarends.nl");
		webserver.addServlet("/Rplots", new RplotServlet(),"www.dannyarends.nl");
		webserver.addServlet("/", new CGIServlet("homepage",false),"www.dannyarends.nl");
		webserver.addServlet("/", new MovedServlet(),"dannyarends.nl");
		webserver.addServlet("/", new FileServlet("xqtl"),"www.xqtl.nl");		
		webserver.addServlet("/", new FileServlet("xqtl"),"www.xqtl.org");		
		webserver.addServlet("/", new MovedServlet(),"xqtl.nl");
		webserver.addServlet("/", new MovedServlet(),"xqtl.org");
		new Thread(webserver).start();
	}
	
	static void PrintOutOptions(){
		Utils.console("Websites located in " + WebOptions.web_dir);
		Utils.console("PHP " + (WebOptions.php_enabled?"en":"dis") + "abled " + WebOptions.php_bin_location);
		Utils.console("PERL " + (WebOptions.php_enabled?"en":"dis") + "abled " + WebOptions.perl_bin_location);
		Utils.console("PYTHON " + (WebOptions.php_enabled?"en":"dis") + "abled " + WebOptions.python_bin_location);
	}
	
	static String setLocalPath(){
		File f = new File("");
		localPath = f.getAbsolutePath();
		return localPath;
	}
}

