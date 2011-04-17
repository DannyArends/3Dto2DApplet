package nl.dannyarends.options;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import nl.dannyarends.generic.Utils;

public abstract class OptionsPackage {
	Properties properties = new Properties();
	
	OptionsPackage(){
		
	}
	
	OptionsPackage(String propertiesFile){
		load(propertiesFile);
	}
	
	public boolean load(String propertiesFile){
		try	{
			properties.load(new FileInputStream(propertiesFile));
			return true;
		}catch (Exception e){
			Utils.log("Properties file not found", System.err);
			return false;
		}
	}

	public boolean loadURL(String propertiesUrl){
		try	{
			URL url = new URL(propertiesUrl); 
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
