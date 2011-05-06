package nl.dannyarends.www;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nl.dannyarends.generic.JavaCompiler;
import nl.dannyarends.generic.JavaCompiler.CompileUnit;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.ircclient.IRCHandler;
import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.GeneratorOptions;
import nl.dannyarends.options.OptionsPackage;
import nl.dannyarends.options.OptionsParser;
import nl.dannyarends.options.WebOptions;
import nl.dannyarends.www.http.WWWServer;
import nl.dannyarends.www.http.servlets.BotServlet;
import nl.dannyarends.www.http.servlets.CGIServlet;

/**
 * \brief Main webserver8080 method<br>
 *
 * We used a custom version of the Molgenis options reader to load the basic options 
 * provided for the web server, for all of them we have default setting.
 * bugs: none found<br>
 */
public class WebServer8080 {
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

//		Utils.log("-- Starting Generation " + setLocalPath() + "--",System.err);
//		Generator g = new Generator();
//		g.generate();
		Utils.log("-- Starting compiler " + setLocalPath() + "--",System.err);
		JavaCompiler j = new JavaCompiler();

		CompileUnit source = j.newCompileUnit("src\\java\\nl\\dannyarends\\ircclient\\","build");
		source.addDependencies(new String[]{"src\\java","libs\\pircbot.jar"});
		source.setMainClass("nl.dannyarends.ircclient.IRCHandler");
		source.setCustomJarName("websites/homepage/dist/Bot");
		
		Utils.log("-- Starting WebServer " + setLocalPath() + "--",System.err);
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("port", 8080);
		WWWServer webserver = new WWWServer(properties);
		webserver.addServlet("/bot", new BotServlet());
		webserver.addServlet("/cgi-bin", new CGIServlet("homepage",true));
		webserver.addServlet("/", new CGIServlet("homepage",false));
		webserver.setAttribute("bot", botentry);
		new Thread(webserver).start();
		new Thread(botentry).start();
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

