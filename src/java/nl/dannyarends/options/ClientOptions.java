package nl.dannyarends.options;


public class ClientOptions extends OptionsPackage{
	public ClientOptions(String propertiesUrl){
		loadURL(propertiesUrl);
	}
	
	@Option(name = "GUI_File", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Start the applet GUI")
	public boolean GUI_File = false;
	
	@Option(name = "trace_enabled", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace the running application")
	public static boolean trace_enabled = false;
	
	@Option(name = "trace_priority", param = Option.Param.INTEGER, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace level (0=silent,1=info,2=debug,3=full)")
	public static int trace_priority = 0;
}
