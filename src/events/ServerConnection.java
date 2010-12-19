package events;

import generic.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnection {
	String aLine;
	URL url; 
	HttpURLConnection con; 
	OutputStream oStream;
	byte[] parameterAsBytes;
	BufferedReader in;
	
	public ServerConnection(){
	
	}
	
	public String commandToServer(String parametersAsString){
		String response = "\n";
		try {
			if(parametersAsString==null) parametersAsString = "msg=hello&to=world"; 
			parameterAsBytes = parametersAsString.getBytes(); 
			//send parameters to server 
			//FOR DEPLOY: url = new URL(Engine.getParentApplet().getDocumentBase() + "server.php");
			url = new URL("http://localhost/Testing/server.php"); 
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true); 
			con.setDoInput(true); // only if reading response 
			con.setDoInput(true); 
			con.setRequestMethod("POST");
			con.setRequestProperty("Content=length", String.valueOf(parameterAsBytes.length)); 
			oStream = con.getOutputStream(); 
			oStream.write(parameterAsBytes); 
			oStream.flush(); 
			//Read response from server 
			in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
			aLine = in.readLine(); 
			while (aLine != null){ 
				response += aLine + "\n"; 
				aLine = in.readLine();
			} 
			Utils.console(response);
			in.close(); 
			oStream.close(); 
		} catch (Exception ioe) {
			Utils.log("Error",ioe);
		}
		return response;
	}
}
