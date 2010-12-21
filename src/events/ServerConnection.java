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

import rendering.Engine;

public class ServerConnection {
	String aLine;
	URL url; 
	HttpURLConnection con; 
	OutputStream oStream;
	byte[] parameterAsBytes;
	BufferedReader in;
	long up,down;
	
	public ServerConnection(){
		up=0;
		down=0;
	}
	
	public String commandToServer(String parametersAsString){
		String response = "\n";
		try {
			if(parametersAsString==null) return "";
			parameterAsBytes = parametersAsString.getBytes(); 
			//send parameters to server 
			//FOR DEPLOY: 
			//Utils.console(Engine.getAppletURL() + "cgi-bin/server.cgi");
			//url = new URL(Engine.getAppletURL() + "cgi-bin/server.cgi");
			url = new URL("http://localhost/Testing/cgi-bin/server.cgi"); 
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true); 
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
			if(Engine.verbose) Utils.console(response);
			in.close(); 
			oStream.close(); 
		} catch (Exception e) {
			Utils.log(e.getMessage(),System.err);
		}
		up += parameterAsBytes.length;
		down += response.length();
		Utils.console("Server traffic: " + up + "/" + down);
		return response;
	}
}
