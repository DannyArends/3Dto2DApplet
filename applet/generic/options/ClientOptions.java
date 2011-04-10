package generic.options;

import generic.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class ClientOptions implements OptionsPackage{
	public Properties properties;
	
	@Option(name = "GUI_File", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Start the applet GUI")
	public boolean GUI_File = false;
	
	@Option(name = "trace_enabled", param = Option.Param.BOOLEAN, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace the running application")
	public static boolean trace_enabled = false;
	
	@Option(name = "trace_priority", param = Option.Param.INTEGER, type = Option.Type.REQUIRED_ARGUMENT, usage = "Trace level (0=silent,1=info,2=debug,3=full)")
	public static int trace_priority = 0;
	
	
	public ClientOptions(){
		
	}
	
	public boolean load(String propertiesFile){
		properties = new Properties();
		try	{
			URL url = new URL(propertiesFile); 
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
			properties.load(in);
			return true;
		}catch (Exception e){
			Utils.log("Properties file not found", System.err);
			return false;
		}
	}
}
