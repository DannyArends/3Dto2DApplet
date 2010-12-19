/*
#
# ServerConnection.java
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
#
#     This program is free software; you can redistribute it and/or
#     modify it under the terms of the GNU General Public License,
#     version 3, as published by the Free Software Foundation.
# 
#     This program is distributed in the hope that it will be useful,
#     but without any warranty; without even the implied warranty of
#     merchantability or fitness for a particular purpose.  See the GNU
#     General Public License, version 3, for more details.
# 
#     A copy of the GNU General Public License, version 3, is available
#     at http://www.r-project.org/Licenses/GPL-3
#
*/

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
