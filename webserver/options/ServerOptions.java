package options;

import generic.Utils;
import java.io.FileInputStream;
import java.util.Properties;

public class ServerOptions {
	public Properties properties;
	
	@Option(name = "web_dir", param = Option.Param.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of the websites")
	public static String web_dir = "websites";
	
	@Option(name = "web_gui", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Start the applet GUI")
	public boolean web_gui = false;
	
	@Option(name = "trace_enabled", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace the running application")
	public static boolean trace_enabled = false;
	
	@Option(name = "trace_priority", param = Option.Param.INTEGER, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace level (0=silent,1=info,2=debug,3=full)")
	public static int trace_priority = 0;
	
	@Option(name = "trace_to", param = Option.Param.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace level (0=silent,1=info,2=debug,3=full)")
	public String trace_to = "System.out";
	
	@Option(name = "db_driver", param = Option.Param.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "Database back end driver for the web server")
	public static String db_driver = "org.hsqldb.jdbcDriver";
	
	@Option(name = "db_user", param = Option.Param.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "Username for the database")
	public static String db_user = "sa";
	
	@Option(name = "db_pass", param = Option.Param.STRING, type = Option.Type.OPTIONAL_ARGUMENT, usage = "Password for the database")
	public String db_pass = "";
	
	@Option(name = "db_uri", param = Option.Param.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "URI of the database back end for the web server")
	public static String db_uri = "jdbc:hsqldb:file:database/DJWServer;shutdown=true";
	
	@Option(name = "php_enabled", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Enable PHP?")
	public static boolean php_enabled = false;
	
	@Option(name = "perl_enabled", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Enable Perl?")
	public static boolean perl_enabled = false;
	
	@Option(name = "python_enabled", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Enable Python?")
	public static boolean python_enabled = false;
	
	@Option(name = "php_bin_location", param = Option.Param.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of PHP")
	public static String php_bin_location = "websites";

	@Option(name = "perl_bin_location", param = Option.Param.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of Perl")
	public static String perl_bin_location = "websites";
	
	@Option(name = "python_bin_location", param = Option.Param.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of Python")
	public static String python_bin_location = "websites";

	
	public ServerOptions(){
		
	}
	
	public ServerOptions(String propertiesFile){
		properties = new Properties();
		try	{
			properties.load(new FileInputStream(propertiesFile));
		}catch (Exception e){
			Utils.log("Properties file not found", System.err);
		}
	}
}
