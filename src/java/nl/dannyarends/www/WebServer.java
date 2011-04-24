package nl.dannyarends.www;

import java.io.File;

import nl.dannyarends.generator.Generator;
import nl.dannyarends.generic.JavaCompiler;
import nl.dannyarends.generic.JavaCompiler.CompileUnit;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.GeneratorOptions;
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
	static GeneratorOptions generatorOptions;
	static OptionsParser optionsParser;
	static String localPath;
	
	public static void main(String[] args) throws Exception{
		Utils.log("-- Parsing properties --",System.err);
		webserverOptions = new ServerOptions("settings/www.properties");
		databaseOptions = new DatabaseOptions("settings/db.properties");
		generatorOptions = new GeneratorOptions("settings/generator.properties");
		optionsParser = new OptionsParser();
		optionsParser.parse((OptionsPackage) webserverOptions);
		optionsParser.parse((OptionsPackage) databaseOptions);
		optionsParser.parse((OptionsPackage) generatorOptions);
		
		Utils.log("-- Starting Generation " + setLocalPath() + "--",System.err);
		Generator g = new Generator();
		g.generate();
		Utils.log("-- Starting compiler " + setLocalPath() + "--",System.err);
		JavaCompiler j = new JavaCompiler();

		CompileUnit source = j.newCompileUnit("src\\java\\nl\\dannyarends\\applets\\gameApplet","build");
		source.addDependencies(new String[]{"src\\java"});
		source.setMainClass("nl.dannyarends.applets.gameApplet");
		source.setCustomJarName("GameApplet");
		
		j.CompileTarget(source);
		
//		generated.addDependency("libs");
//		generated.addDependency("src\\java");
//		j.CompileTarget(generated);
		
		Utils.log("-- Starting WebServer " + setLocalPath() + "--",System.err);
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

