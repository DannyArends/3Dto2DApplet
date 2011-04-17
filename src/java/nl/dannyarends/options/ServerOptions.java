package nl.dannyarends.options;


public class ServerOptions extends OptionsPackage{

	public ServerOptions(String propertiesFile) {
		super(propertiesFile);
	}

	@Option(name = "web_dir", param = Option.Param.DIRPATH, type = Option.Type.REQUIRED_ARGUMENT, usage = "Location of the websites")
	public static String web_dir = "websites";
	
	@Option(name = "GUI_File", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Start the applet GUI")
	public boolean GUI_File = false;
	
	@Option(name = "trace_enabled", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace the running application")
	public static boolean trace_enabled = false;
	
	@Option(name = "trace_priority", param = Option.Param.INTEGER, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace level (0=silent,1=info,2=debug,3=full)")
	public static int trace_priority = 0;
	
	@Option(name = "trace_to", param = Option.Param.STRING, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace level (0=silent,1=info,2=debug,3=full)")
	public String trace_to = "System.out";
	
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
}
